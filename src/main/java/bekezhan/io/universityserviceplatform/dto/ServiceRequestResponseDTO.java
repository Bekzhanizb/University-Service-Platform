package bekezhan.io.universityserviceplatform.dto;

import bekezhan.io.universityserviceplatform.entity.ServiceRequest;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceRequestResponseDTO {
    private Long id;
    private String title;
    private String description;
    private ServiceRequest.RequestStatus status;
    private Long studentId;
    private Long categoryId;
}
