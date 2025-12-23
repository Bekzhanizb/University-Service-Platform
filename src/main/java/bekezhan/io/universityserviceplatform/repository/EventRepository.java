package bekezhan.io.universityserviceplatform.repository;

import bekezhan.io.universityserviceplatform.entity.UniversityEvent;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepositoryImplementation<UniversityEvent, Long> {
}
