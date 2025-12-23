package bekezhan.io.universityserviceplatform.dto;

import java.time.LocalDateTime;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UniversityEventDTO {
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}

