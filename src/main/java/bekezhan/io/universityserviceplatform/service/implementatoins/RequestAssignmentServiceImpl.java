package bekezhan.io.universityserviceplatform.service.implementatoins;

import bekezhan.io.universityserviceplatform.dto.RequestAssignmentRequestDTO;
import bekezhan.io.universityserviceplatform.dto.RequestAssignmentResponseDTO;
import bekezhan.io.universityserviceplatform.entity.RequestAssignment;
import bekezhan.io.universityserviceplatform.entity.ServiceRequest;
import bekezhan.io.universityserviceplatform.entity.TeacherProfile;
import bekezhan.io.universityserviceplatform.mapper.RequestAssignmentMapper;
import bekezhan.io.universityserviceplatform.repository.RequestAssignmentRepository;
import bekezhan.io.universityserviceplatform.repository.ServiceRequestRepository;
import bekezhan.io.universityserviceplatform.repository.TeacherProfileRepository;
import bekezhan.io.universityserviceplatform.service.RequestAssignmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class RequestAssignmentServiceImpl implements RequestAssignmentService {

    private final RequestAssignmentRepository assignmentRepository;
    private final ServiceRequestRepository requestRepository;
    private final TeacherProfileRepository teacherRepository;
    private final RequestAssignmentMapper mapper;

    @Override
    @Transactional
    public RequestAssignmentResponseDTO assignTeacher(RequestAssignmentRequestDTO dto) {

        ServiceRequest request = requestRepository.findById(dto.getRequestId())
                .orElseThrow(() -> new RuntimeException("Request not found"));

        TeacherProfile teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        if (assignmentRepository.existsByRequestId(request.getId())) {
            throw new RuntimeException("Request already assigned");
        }

        RequestAssignment assignment = RequestAssignment.builder()
                .request(request)
                .teacher(teacher)
                .assignedAt(LocalDateTime.now())
                .build();

        return mapper.toResponseDTO(assignmentRepository.save(assignment));
    }

    @Override
    @Transactional
    public RequestAssignmentResponseDTO getByRequestId(Long requestId) {
        RequestAssignment assignment = assignmentRepository.findByRequestId(requestId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        return mapper.toResponseDTO(assignment);
    }
}
