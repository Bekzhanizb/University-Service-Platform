package bekezhan.io.universityserviceplatform.repository;

import bekezhan.io.universityserviceplatform.entity.RequestAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestAssignmentRepository extends JpaRepository<RequestAssignment, Long> {
}
