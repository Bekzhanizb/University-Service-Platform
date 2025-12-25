package bekezhan.io.universityserviceplatform.service;

import bekezhan.io.universityserviceplatform.dto.UserRequestDTO;
import bekezhan.io.universityserviceplatform.dto.UserResponseDTO;
import bekezhan.io.universityserviceplatform.entity.*;
import bekezhan.io.universityserviceplatform.mapper.UserMapper;
import bekezhan.io.universityserviceplatform.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final StudentProfileRepository studentRepository;
    private final TeacherProfileRepository teacherRepository;
    private final UserMapper mapper;

    @Transactional
    public UserResponseDTO registerUser(UserRequestDTO dto) {
        User user = User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword()) // В продакшене: encoder.encode(dto.getPassword())
                .fullName(dto.getFullName())
                .role(dto.getRole())
                .createdAt(LocalDateTime.now())
                .build();

        User saved = userRepository.save(user);

        if (dto.getRole() == User.Role.STUDENT) {
            StudentProfile profile = StudentProfile.builder()
                    .user(saved)
                    .build();
            studentRepository.save(profile);
        } else if (dto.getRole() == User.Role.TEACHER) {
            TeacherProfile profile = TeacherProfile.builder()
                    .user(saved)
                    .build();
            teacherRepository.save(profile);
        }

        return mapper.toResponseDTO(saved);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapper.toResponseDTO(user);
    }
}