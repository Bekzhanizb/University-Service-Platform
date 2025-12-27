package bekezhan.io.universityserviceplatform.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestCommentResponseDTO {
    private Long id;
    private String message;
    private Long userId;
}
