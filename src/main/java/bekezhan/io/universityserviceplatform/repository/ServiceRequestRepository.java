package bekezhan.io.universityserviceplatform.repository;

import bekezhan.io.universityserviceplatform.entity.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
}
