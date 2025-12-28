package bekezhan.io.universityserviceplatform.service;

import bekezhan.io.universityserviceplatform.dto.ChangePasswordRequestDTO;
import bekezhan.io.universityserviceplatform.dto.UpdateUserRequestDTO;
import bekezhan.io.universityserviceplatform.dto.UserRequestDTO;
import bekezhan.io.universityserviceplatform.dto.UserResponseDTO;
import bekezhan.io.universityserviceplatform.entity.User;

public interface AuthService {
    UserResponseDTO register(UserRequestDTO dto);
    String login(String email, String password);
    User getCurrentUser();
    void changePassword(ChangePasswordRequestDTO dto);
    UserResponseDTO updateProfile(UpdateUserRequestDTO dto);
}