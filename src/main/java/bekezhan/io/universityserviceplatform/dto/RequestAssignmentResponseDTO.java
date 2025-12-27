package bekezhan.io.universityserviceplatform.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestAssignmentResponseDTO {
    private Long id;
    private Long requestId;
    private Long teacherId;
}

