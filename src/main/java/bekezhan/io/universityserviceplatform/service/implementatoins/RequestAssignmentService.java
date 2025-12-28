package bekezhan.io.universityserviceplatform.service.implementatoins;

import bekezhan.io.universityserviceplatform.entity.*;
import bekezhan.io.universityserviceplatform.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RequestAssignmentService {

    private final RequestAssignmentRepository assignmentRepository;
    private final ServiceRequestRepository requestRepository;
    private final TeacherProfileRepository teacherRepository;
    private final NotificationService notificationService;

    @Transactional
    public RequestAssignment assignTeacher(Long requestId, Long teacherId) {
        ServiceRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        TeacherProfile teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        RequestAssignment assignment = RequestAssignment.builder()
                .request(request)
                .teacher(teacher)
                .assignedAt(LocalDateTime.now())
                .build();

        request.setStatus(ServiceRequest.RequestStatus.IN_PROGRESS);
        requestRepository.save(request);

        RequestAssignment saved = assignmentRepository.save(assignment);

        notificationService.createNotification(
                request.getStudent().getUser(),
                "Преподаватель назначен на вашу заявку: " + teacher.getUser().getFullName()
        );

        notificationService.createNotification(
                teacher.getUser(),
                "Вам назначена заявка: " + request.getTitle()
        );

        return saved;
    }

    public RequestAssignment getAssignmentByRequestId(Long requestId) {
        return assignmentRepository.findByRequestId(requestId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));
    }
}