package bekezhan.io.universityserviceplatform.service;

import bekezhan.io.universityserviceplatform.security.JwtProvider;
import bekezhan.io.universityserviceplatform.dto.UserRequestDTO;
import bekezhan.io.universityserviceplatform.dto.UserResponseDTO;
import bekezhan.io.universityserviceplatform.entity.User;
import bekezhan.io.universityserviceplatform.mapper.UserMapper;
import bekezhan.io.universityserviceplatform.repository.UserRepository;
import bekezhan.io.universityserviceplatform.service.implementatoins.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthServiceImpl authService;

    private UserRequestDTO requestDTO;
    private User user;
    private UserResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = UserRequestDTO.builder()
                .email("test@example.com")
                .password("password123")
                .fullName("Test User")
                .role(User.Role.STUDENT)
                .build();

        user = User.builder()
                .id(1L)
                .email("test@example.com")
                .password("encodedPassword")
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
    void register_Success() {
        when(userRepository.findByEmail(requestDTO.getEmail())).thenReturn(Optional.empty());
        when(userMapper.toEntity(requestDTO)).thenReturn(user);
        when(passwordEncoder.encode(requestDTO.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toResponseDTO(user)).thenReturn(responseDTO);

        UserResponseDTO result = authService.register(requestDTO);

        assertNotNull(result);
        assertEquals(responseDTO.getEmail(), result.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_EmailAlreadyExists_ThrowsException() {
        when(userRepository.findByEmail(requestDTO.getEmail()))
                .thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class, () -> authService.register(requestDTO));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void login_Success() {
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtProvider.generateToken(requestDTO.getEmail())).thenReturn("jwt-token");

        String token = authService.login(requestDTO.getEmail(), requestDTO.getPassword());

        assertNotNull(token);
        assertEquals("jwt-token", token);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}