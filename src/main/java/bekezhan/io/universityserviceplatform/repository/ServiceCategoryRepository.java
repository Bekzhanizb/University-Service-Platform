package bekezhan.io.universityserviceplatform.repository;

import bekezhan.io.universityserviceplatform.entity.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Long> {
}
