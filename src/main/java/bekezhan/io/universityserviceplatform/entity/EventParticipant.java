package bekezhan.io.universityserviceplatform.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "event_participants")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private UniversityEvent event;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private ParticipantStatus status;

    public enum ParticipantStatus {
        REGISTERED,
        ATTENDED,
        ABSENT
    }

}
