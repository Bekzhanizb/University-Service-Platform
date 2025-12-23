package bekezhan.io.universityserviceplatform.repository;

import bekezhan.io.universityserviceplatform.entity.RequestComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestCommentRepository extends JpaRepository<RequestComment, Long> {
}
