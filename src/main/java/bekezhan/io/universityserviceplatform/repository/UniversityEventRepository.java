package bekezhan.io.universityserviceplatform.repository;

import bekezhan.io.universityserviceplatform.entity.UniversityEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityEventRepository extends JpaRepository<UniversityEvent, Long> {
}