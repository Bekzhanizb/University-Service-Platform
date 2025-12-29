package bekezhan.io.universityserviceplatform.controller;

import bekezhan.io.universityserviceplatform.dto.*;
import bekezhan.io.universityserviceplatform.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRequestDTO dto) {
        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String token = authService.login(
                credentials.get("email"),
                credentials.get("password")
        );
        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser() {
        return ResponseEntity.ok(
                new UserResponseDTO(
                        authService.getCurrentUser().getId(),
                        authService.getCurrentUser().getEmail(),
                        authService.getCurrentUser().getFullName(),
                        authService.getCurrentUser().getRole()
                )
        );
    }

    @PutMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequestDTO dto) {
        authService.changePassword(dto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/profile")
    public ResponseEntity<UserResponseDTO> updateProfile(@RequestBody UpdateUserRequestDTO dto) {
        return ResponseEntity.ok(authService.updateProfile(dto));
    }
}