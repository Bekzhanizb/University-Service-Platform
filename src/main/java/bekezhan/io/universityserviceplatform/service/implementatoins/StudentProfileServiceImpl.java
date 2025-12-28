package bekezhan.io.universityserviceplatform.service.implementatoins;

import bekezhan.io.universityserviceplatform.dto.StudentProfileRequestDTO;
import bekezhan.io.universityserviceplatform.dto.StudentProfileResponseDTO;
import bekezhan.io.universityserviceplatform.entity.StudentProfile;
import bekezhan.io.universityserviceplatform.entity.User;
import bekezhan.io.universityserviceplatform.mapper.StudentProfileMapper;
import bekezhan.io.universityserviceplatform.repository.StudentProfileRepository;
import bekezhan.io.universityserviceplatform.repository.UserRepository;
import bekezhan.io.universityserviceplatform.service.StudentProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentProfileServiceImpl implements StudentProfileService {

    private final StudentProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final StudentProfileMapper mapper;

    @Override
    @Transactional
    public StudentProfileResponseDTO createProfile(StudentProfileRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != User.Role.STUDENT) {
            throw new RuntimeException("User is not a student");
        }

        if (profileRepository.findByUserId(dto.getUserId()).isPresent()) {
            throw new RuntimeException("Profile already exists");
        }

        StudentProfile profile = mapper.toEntity(dto);
        profile.setUser(user);

        StudentProfile saved = profileRepository.save(profile);
        return mapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentProfileResponseDTO getProfileByUserId(Long userId) {
        StudentProfile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        return mapper.toResponseDTO(profile);
    }

    @Override
    @Transactional
    public StudentProfileResponseDTO updateProfile(Long id, StudentProfileRequestDTO dto) {
        StudentProfile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        profile.setStudentCode(dto.getStudentCode());
        profile.setFaculty(dto.getFaculty());
        profile.setCourse(dto.getCourse());
        profile.setGroupName(dto.getGroupName());

        StudentProfile updated = profileRepository.save(profile);
        return mapper.toResponseDTO(updated);
    }
}
