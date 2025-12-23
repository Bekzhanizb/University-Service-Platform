package bekezhan.io.universityserviceplatform.repository;

import bekezhan.io.universityserviceplatform.entity.TeacherProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository  extends JpaRepository<TeacherProfile, Long> {
}
