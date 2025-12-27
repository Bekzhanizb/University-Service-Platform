package bekezhan.io.universityserviceplatform.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceCategoryResponseDTO {
    private Long id;
    private String name;
    private String description;
}

