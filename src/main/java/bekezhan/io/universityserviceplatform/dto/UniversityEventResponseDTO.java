package bekezhan.io.universityserviceplatform.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UniversityEventResponseDTO {
    private Long id;
    private String title;
    private String description;
}
