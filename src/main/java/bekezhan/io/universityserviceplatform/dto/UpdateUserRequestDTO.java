package bekezhan.io.universityserviceplatform.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserRequestDTO {
    private String fullName;
    private String email;
}