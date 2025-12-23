package bekezhan.io.universityserviceplatform.dto;

import bekezhan.io.universityserviceplatform.entity.User;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {
    private String email;
    private String password;
    private String fullName;
    private User.Role role;
}

