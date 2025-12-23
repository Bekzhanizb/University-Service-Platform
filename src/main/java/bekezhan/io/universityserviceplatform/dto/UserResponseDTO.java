package bekezhan.io.universityserviceplatform.dto;


import bekezhan.io.universityserviceplatform.entity.User;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private Long id;
    private String email;
    private String fullName;
    private User.Role role;
}
