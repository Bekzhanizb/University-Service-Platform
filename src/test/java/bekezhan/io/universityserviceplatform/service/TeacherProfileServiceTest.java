package bekezhan.io.universityserviceplatform.service;

import bekezhan.io.universityserviceplatform.dto.TeacherProfileRequestDTO;
import bekezhan.io.universityserviceplatform.dto.TeacherProfileResponseDTO;
import bekezhan.io.universityserviceplatform.entity.TeacherProfile;
import bekezhan.io.universityserviceplatform.entity.User;
import bekezhan.io.universityserviceplatform.mapper.TeacherProfileMapper;
import bekezhan.io.universityserviceplatform.repository.TeacherProfileRepository;
import bekezhan.io.universityserviceplatform.repository.UserRepository;
import bekezhan.io.universityserviceplatform.service.implementatoins.TeacherProfileServiceImpl;
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
class TeacherProfileServiceTest {

    @Mock
    private TeacherProfileRepository profileRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TeacherProfileMapper mapper;

    @InjectMocks
    private TeacherProfileServiceImpl profileService;

    private User user;
    private TeacherProfile profile;
    private TeacherProfileRequestDTO requestDTO;
    private TeacherProfileResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .email("teacher@example.com")
                .role(User.Role.TEACHER)
                .build();

        profile = TeacherProfile.builder()
                .id(1L)
                .user(user)
                .department("Computer Science")
                .academicTitle("Professor")
                .build();

        requestDTO = TeacherProfileRequestDTO.builder()
                .userId(1L)
                .department("Computer Science")
                .academicTitle("Professor")
                .build();

        responseDTO = TeacherProfileResponseDTO.builder()
                .id(1L)
                .userId(1L)
                .department("Computer Science")
                .academicTitle("Professor")
                .build();
    }

    @Test
    void createProfile_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(profileRepository.findByUserId(1L)).thenReturn(Optional.empty());
        when(mapper.toEntity(requestDTO)).thenReturn(profile);
        when(profileRepository.save(any(TeacherProfile.class))).thenReturn(profile);
        when(mapper.toResponseDTO(profile)).thenReturn(responseDTO);

        TeacherProfileResponseDTO result = profileService.createProfile(requestDTO);

        assertNotNull(result);
        assertEquals(responseDTO.getDepartment(), result.getDepartment());
        verify(profileRepository).save(any(TeacherProfile.class));
    }

    @Test
    void createProfile_UserNotTeacher_ThrowsException() {
        user.setRole(User.Role.STUDENT);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class, () -> profileService.createProfile(requestDTO));
        verify(profileRepository, never()).save(any(TeacherProfile.class));
    }

    @Test
    void getProfileByUserId_Success() {
        when(profileRepository.findByUserId(1L)).thenReturn(Optional.of(profile));
        when(mapper.toResponseDTO(profile)).thenReturn(responseDTO);

        TeacherProfileResponseDTO result = profileService.getProfileByUserId(1L);

        assertNotNull(result);
        assertEquals(responseDTO.getUserId(), result.getUserId());
    }
}
