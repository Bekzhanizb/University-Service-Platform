package bekezhan.io.universityserviceplatform.service;

import bekezhan.io.universityserviceplatform.dto.RequestAssignmentRequestDTO;
import bekezhan.io.universityserviceplatform.dto.RequestAssignmentResponseDTO;

public interface RequestAssignmentService {
    RequestAssignmentResponseDTO assignTeacher(RequestAssignmentRequestDTO dto);
    RequestAssignmentResponseDTO getByRequestId(Long requestId);
}
