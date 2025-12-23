package bekezhan.io.universityserviceplatform.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceRequestDTO {
    private String title;
    private String description;
    private Long categoryId;
}

