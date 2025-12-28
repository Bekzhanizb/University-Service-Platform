package bekezhan.io.universityserviceplatform.service.implementatoins;

import bekezhan.io.universityserviceplatform.dto.ChangePasswordRequestDTO;
import bekezhan.io.universityserviceplatform.dto.UpdateUserRequestDTO;
import bekezhan.io.universityserviceplatform.dto.UserRequestDTO;
import bekezhan.io.universityserviceplatform.dto.UserResponseDTO;
import bekezhan.io.universityserviceplatform.entity.User;
import bekezhan.io.universityserviceplatform.mapper.UserMapper;
import bekezhan.io.universityserviceplatform.repository.UserRepository;
import bekezhan.io.universityserviceplatform.security.JwtProvider;
import bekezhan.io.universityserviceplatform.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public UserResponseDTO register(UserRequestDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        User saved = userRepository.save(user);
        return userMapper.toResponseDTO(saved);
    }

    @Override
    public String login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtProvider.generateToken(email);
    }

    @Override
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth != null ? auth.getName() : null;

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequestDTO dto) {
        User currentUser = getCurrentUser();

        if (!passwordEncoder.matches(dto.getOldPassword(), currentUser.getPassword())) {
            throw new RuntimeException("Invalid old password");
        }

        currentUser.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(currentUser);
    }

    @Override
    @Transactional
    public UserResponseDTO updateProfile(UpdateUserRequestDTO dto) {
        User currentUser = getCurrentUser();

        if (!currentUser.getEmail().equals(dto.getEmail())) {
            if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
                throw new RuntimeException("Email already exists");
            }
            currentUser.setEmail(dto.getEmail());
        }

        currentUser.setFullName(dto.getFullName());

        User updated = userRepository.save(currentUser);
        return userMapper.toResponseDTO(updated);
    }
}
