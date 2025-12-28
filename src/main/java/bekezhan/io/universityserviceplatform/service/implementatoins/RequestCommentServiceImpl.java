package bekezhan.io.universityserviceplatform.service.implementatoins;

import bekezhan.io.universityserviceplatform.dto.RequestCommentRequestDTO;
import bekezhan.io.universityserviceplatform.dto.RequestCommentResponseDTO;
import bekezhan.io.universityserviceplatform.entity.RequestComment;
import bekezhan.io.universityserviceplatform.entity.ServiceRequest;
import bekezhan.io.universityserviceplatform.entity.User;
import bekezhan.io.universityserviceplatform.mapper.RequestCommentMapper;
import bekezhan.io.universityserviceplatform.repository.RequestCommentRepository;
import bekezhan.io.universityserviceplatform.repository.ServiceRequestRepository;
import bekezhan.io.universityserviceplatform.repository.UserRepository;
import bekezhan.io.universityserviceplatform.service.RequestCommentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RequestCommentServiceImpl implements RequestCommentService {

    private final RequestCommentRepository commentRepository;
    private final ServiceRequestRepository requestRepository;
    private final UserRepository userRepository;
    private final RequestCommentMapper mapper;

    @Override
    @Transactional
    public RequestCommentResponseDTO addComment(
            RequestCommentRequestDTO dto,
            Long userId
    ) {
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

        return mapper.toResponseDTO(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public List<RequestCommentResponseDTO> getCommentsByRequest(Long requestId) {
        return commentRepository.findByRequestId(requestId)
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }
}
