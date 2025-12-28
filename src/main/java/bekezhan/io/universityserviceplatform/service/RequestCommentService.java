package bekezhan.io.universityserviceplatform.service;

import bekezhan.io.universityserviceplatform.dto.RequestCommentRequestDTO;
import bekezhan.io.universityserviceplatform.dto.RequestCommentResponseDTO;

import java.util.List;

public interface RequestCommentService {
    RequestCommentResponseDTO addComment(RequestCommentRequestDTO dto, Long userId);
    List<RequestCommentResponseDTO> getCommentsByRequest(Long requestId);
}
