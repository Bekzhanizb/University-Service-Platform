package bekezhan.io.universityserviceplatform.repository;

import bekezhan.io.universityserviceplatform.entity.RequestAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequestAssignmentRepository extends JpaRepository<RequestAssignment, Long> {
    Optional<RequestAssignment> findByRequestId(Long requestId);
}