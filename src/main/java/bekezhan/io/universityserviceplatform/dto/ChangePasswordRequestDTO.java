package bekezhan.io.universityserviceplatform.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordRequestDTO {
    private String oldPassword;
    private String newPassword;
}