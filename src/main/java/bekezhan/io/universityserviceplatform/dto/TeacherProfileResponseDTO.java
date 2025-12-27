package bekezhan.io.universityserviceplatform.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherProfileResponseDTO {
    private Long id;
    private Long userId;
    private String department;
    private String academicTitle;
}
