package bekezhan.io.universityserviceplatform.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teacher_profiles")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String department;
    private String academicTitle;
}
