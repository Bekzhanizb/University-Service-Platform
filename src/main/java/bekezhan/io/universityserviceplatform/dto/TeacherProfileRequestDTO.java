package bekezhan.io.universityserviceplatform.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherProfileRequestDTO {
    private Long userId;
    private String department;
    private String academicTitle;
}

