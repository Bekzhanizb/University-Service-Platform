package bekezhan.io.universityserviceplatform.service.implementatoins;

import bekezhan.io.universityserviceplatform.dto.ServiceRequestDTO;
import bekezhan.io.universityserviceplatform.entity.*;
import bekezhan.io.universityserviceplatform.mapper.ServiceRequestMapper;
import bekezhan.io.universityserviceplatform.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceRequestService {

    private final ServiceRequestRepository requestRepository;
    private final ServiceCategoryRepository categoryRepository;
    private final StudentProfileRepository studentRepository;
    private final ServiceRequestMapper mapper;
    private final NotificationService notificationService;

    @Transactional
    public ServiceRequest createRequest(ServiceRequestDTO dto, Long studentId) {
        StudentProfile student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        ServiceCategory category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        ServiceRequest request = ServiceRequest.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(ServiceRequest.RequestStatus.NEW)
                .student(student)
                .category(category)
                .createdAt(LocalDateTime.now())
                .build();

        ServiceRequest saved = requestRepository.save(request);

        notificationService.createNotification(
                student.getUser(),
                "Ваша заявка '" + saved.getTitle() + "' создана"
        );

        return saved;
    }

    public List<ServiceRequest> getAllRequests() {
        return requestRepository.findAll();
    }

    public List<ServiceRequest> getStudentRequests(Long studentId) {
        return requestRepository.findByStudentId(studentId);
    }

    public ServiceRequest getRequestById(Long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
    }

    @Transactional
    public ServiceRequest updateStatus(Long id, ServiceRequest.RequestStatus status) {
        ServiceRequest request = getRequestById(id);
        request.setStatus(status);
        ServiceRequest updated = requestRepository.save(request);

        notificationService.createNotification(
                request.getStudent().getUser(),
                "Статус заявки '" + request.getTitle() + "' изменён на " + status
        );

        return updated;
    }
}