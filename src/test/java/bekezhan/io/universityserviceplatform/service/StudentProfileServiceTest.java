package bekezhan.io.universityserviceplatform.service;

import bekezhan.io.universityserviceplatform.dto.StudentProfileRequestDTO;
import bekezhan.io.universityserviceplatform.dto.StudentProfileResponseDTO;
import bekezhan.io.universityserviceplatform.entity.StudentProfile;
import bekezhan.io.universityserviceplatform.entity.User;
import bekezhan.io.universityserviceplatform.mapper.StudentProfileMapper;
import bekezhan.io.universityserviceplatform.repository.StudentProfileRepository;
import bekezhan.io.universityserviceplatform.repository.UserRepository;
import bekezhan.io.universityserviceplatform.service.implementatoins.StudentProfileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentProfileServiceTest {

    @Mock
    private StudentProfileRepository profileRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StudentProfileMapper mapper;

    @InjectMocks
    private StudentProfileServiceImpl profileService;

    private User user;
    private StudentProfile profile;
    private StudentProfileRequestDTO requestDTO;
    private StudentProfileResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .email("student@example.com")
                .role(User.Role.STUDENT)
                .build();

        profile = StudentProfile.builder()
                .id(1L)
                .user(user)
                .studentCode("ST001")
                .faculty("Computer Science")
                .course(2)
                .groupName("CS-21")
                .build();

        requestDTO = StudentProfileRequestDTO.builder()
                .userId(1L)
                .studentCode("ST001")
                .faculty("Computer Science")
                .course(2)
                .groupName("CS-21")
                .build();

        responseDTO = StudentProfileResponseDTO.builder()
                .id(1L)
                .userId(1L)
                .studentCode("ST001")
                .faculty("Computer Science")
                .course(2)
                .groupName("CS-21")
                .build();
    }

    @Test
    void createProfile_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(profileRepository.findByUserId(1L)).thenReturn(Optional.empty());
        when(mapper.toEntity(requestDTO)).thenReturn(profile);
        when(profileRepository.save(any(StudentProfile.class))).thenReturn(profile);
        when(mapper.toResponseDTO(profile)).thenReturn(responseDTO);

        StudentProfileResponseDTO result = profileService.createProfile(requestDTO);

        assertNotNull(result);
        assertEquals(responseDTO.getStudentCode(), result.getStudentCode());
        verify(profileRepository).save(any(StudentProfile.class));
    }

    @Test
    void createProfile_UserNotStudent_ThrowsException() {
        user.setRole(User.Role.TEACHER);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class, () -> profileService.createProfile(requestDTO));
        verify(profileRepository, never()).save(any(StudentProfile.class));
    }

    @Test
    void createProfile_ProfileAlreadyExists_ThrowsException() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(profileRepository.findByUserId(1L)).thenReturn(Optional.of(profile));

        assertThrows(RuntimeException.class, () -> profileService.createProfile(requestDTO));
        verify(profileRepository, never()).save(any(StudentProfile.class));
    }

    @Test
    void getProfileByUserId_Success() {
        when(profileRepository.findByUserId(1L)).thenReturn(Optional.of(profile));
        when(mapper.toResponseDTO(profile)).thenReturn(responseDTO);

        StudentProfileResponseDTO result = profileService.getProfileByUserId(1L);

        assertNotNull(result);
        assertEquals(responseDTO.getUserId(), result.getUserId());
    }

    @Test
    void getProfileByUserId_NotFound_ThrowsException() {
        when(profileRepository.findByUserId(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> profileService.getProfileByUserId(1L));
    }
}
