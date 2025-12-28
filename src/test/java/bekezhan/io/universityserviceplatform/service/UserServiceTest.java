package bekezhan.io.universityserviceplatform.service;

import bekezhan.io.universityserviceplatform.dto.UserRequestDTO;
import bekezhan.io.universityserviceplatform.dto.UserResponseDTO;
import bekezhan.io.universityserviceplatform.entity.User;
import bekezhan.io.universityserviceplatform.mapper.UserMapper;
import bekezhan.io.universityserviceplatform.repository.UserRepository;
import bekezhan.io.universityserviceplatform.service.implementatoins.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserRequestDTO requestDTO;
    private UserResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .email("test@example.com")
                .password("encodedPassword")
                .fullName("Test User")
                .role(User.Role.STUDENT)
                .isBlocked(false)
                .build();

        requestDTO = UserRequestDTO.builder()
                .email("test@example.com")
                .password("password123")
                .fullName("Test User")
                .role(User.Role.STUDENT)
                .build();

        responseDTO = UserResponseDTO.builder()
                .id(1L)
                .email("test@example.com")
                .fullName("Test User")
                .role(User.Role.STUDENT)
                .build();
    }

    @Test
    void getAllUsers_Success() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        when(userMapper.toResponseDTO(user)).thenReturn(responseDTO);

        List<UserResponseDTO> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(responseDTO.getEmail(), result.get(0).getEmail());
    }

    @Test
    void getUserById_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toResponseDTO(user)).thenReturn(responseDTO);

        UserResponseDTO result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(responseDTO.getEmail(), result.getEmail());
    }

    @Test
    void blockUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.blockUser(1L);

        assertTrue(user.getIsBlocked());
        verify(userRepository).save(user);
    }

    @Test
    void unblockUser_Success() {
        user.setIsBlocked(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.unblockUser(1L);

        assertFalse(user.getIsBlocked());
        verify(userRepository).save(user);
    }
}