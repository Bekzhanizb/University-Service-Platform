package bekezhan.io.universityserviceplatform.service;

import bekezhan.io.universityserviceplatform.dto.UserResponseDTO;
import bekezhan.io.universityserviceplatform.entity.User;

import java.util.List;

public interface UserService {
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserById(Long id);
    void deleteUser(Long id);

    User findByEmail(String email);
    void blockUser(Long id);
    void unblockUser(Long id);
}
