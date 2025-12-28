package bekezhan.io.universityserviceplatform.service.implementatoins;

import bekezhan.io.universityserviceplatform.dto.TeacherProfileRequestDTO;
import bekezhan.io.universityserviceplatform.dto.TeacherProfileResponseDTO;
import bekezhan.io.universityserviceplatform.entity.TeacherProfile;
import bekezhan.io.universityserviceplatform.entity.User;
import bekezhan.io.universityserviceplatform.mapper.TeacherProfileMapper;
import bekezhan.io.universityserviceplatform.repository.TeacherProfileRepository;
import bekezhan.io.universityserviceplatform.repository.UserRepository;
import bekezhan.io.universityserviceplatform.service.TeacherProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeacherProfileServiceImpl implements TeacherProfileService {

    private final TeacherProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final TeacherProfileMapper mapper;

    @Override
    @Transactional
    public TeacherProfileResponseDTO createProfile(TeacherProfileRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != User.Role.TEACHER) {
            throw new RuntimeException("User is not a teacher");
        }

        if (profileRepository.findByUserId(dto.getUserId()).isPresent()) {
            throw new RuntimeException("Profile already exists");
        }

        TeacherProfile profile = mapper.toEntity(dto);
        profile.setUser(user);

        TeacherProfile saved = profileRepository.save(profile);
        return mapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherProfileResponseDTO getProfileByUserId(Long userId) {
        TeacherProfile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        return mapper.toResponseDTO(profile);
    }

    @Override
    @Transactional
    public TeacherProfileResponseDTO updateProfile(Long id, TeacherProfileRequestDTO dto) {
        TeacherProfile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        profile.setDepartment(dto.getDepartment());
        profile.setAcademicTitle(dto.getAcademicTitle());

        TeacherProfile updated = profileRepository.save(profile);
        return mapper.toResponseDTO(updated);
    }
}
