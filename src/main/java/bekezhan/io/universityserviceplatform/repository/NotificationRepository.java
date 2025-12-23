package bekezhan.io.universityserviceplatform.repository;

import bekezhan.io.universityserviceplatform.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
