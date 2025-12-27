package bekezhan.io.universityserviceplatform.dto;

import bekezhan.io.universityserviceplatform.entity.EventParticipant;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventParticipantResponseDTO {
    private Long id;
    private Long eventId;
    private Long userId;
    private EventParticipant.ParticipantStatus status;
}
