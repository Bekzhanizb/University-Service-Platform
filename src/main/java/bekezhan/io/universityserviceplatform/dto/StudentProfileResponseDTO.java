package bekezhan.io.universityserviceplatform.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentProfileResponseDTO {
    private Long id;
    private Long userId;
    private String studentCode;
    private String faculty;
    private Integer course;
    private String groupName;
}
