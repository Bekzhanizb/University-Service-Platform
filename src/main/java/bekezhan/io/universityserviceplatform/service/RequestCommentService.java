package bekezhan.io.universityserviceplatform.service;

import bekezhan.io.universityserviceplatform.dto.RequestCommentDTO;
import bekezhan.io.universityserviceplatform.entity.*;
import bekezhan.io.universityserviceplatform.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestCommentService {

    private final RequestCommentRepository commentRepository;
    private final ServiceRequestRepository requestRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Transactional
    public RequestComment addComment(RequestCommentDTO dto, Long userId) {
        ServiceRequest request = requestRepository.findById(dto.getRequestId())
                .orElseThrow(() -> new RuntimeException("Request not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        RequestComment comment = RequestComment.builder()
                .request(request)
                .user(user)
                .message(dto.getMessage())
                .createdAt(LocalDateTime.now())
                .build();

        RequestComment saved = commentRepository.save(comment);

        notificationService.createNotification(
                request.getStudent().getUser(),
                "Новый комментарий к заявке: " + request.getTitle()
        );

        return saved;
    }

    public List<RequestComment> getCommentsByRequestId(Long requestId) {
        return commentRepository.findByRequestId(requestId);
    }
}