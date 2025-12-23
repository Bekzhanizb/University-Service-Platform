package bekezhan.io.universityserviceplatform.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {
    private Long id;
    private String message;
    private Boolean isRead;
}
