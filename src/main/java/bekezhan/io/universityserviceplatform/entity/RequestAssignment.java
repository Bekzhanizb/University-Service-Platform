package bekezhan.io.universityserviceplatform.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "request_assignments")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "request_id", unique = true)
    private ServiceRequest request;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private TeacherProfile teacher;

    private LocalDateTime assignedAt;
}

