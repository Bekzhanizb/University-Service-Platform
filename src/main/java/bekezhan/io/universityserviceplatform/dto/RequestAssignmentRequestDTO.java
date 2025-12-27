package bekezhan.io.universityserviceplatform.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestAssignmentRequestDTO {
    private Long requestId;
    private Long teacherId;
}

