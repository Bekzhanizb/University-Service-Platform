package bekezhan.io.universityserviceplatform.service.implementatoins;

import bekezhan.io.universityserviceplatform.dto.ServiceRequestDTO;
import bekezhan.io.universityserviceplatform.dto.ServiceRequestResponseDTO;
import bekezhan.io.universityserviceplatform.entity.ServiceCategory;
import bekezhan.io.universityserviceplatform.entity.ServiceRequest;
import bekezhan.io.universityserviceplatform.entity.ServiceRequest.RequestStatus;
import bekezhan.io.universityserviceplatform.entity.StudentProfile;
import bekezhan.io.universityserviceplatform.entity.User;
import bekezhan.io.universityserviceplatform.mapper.ServiceRequestMapper;
import bekezhan.io.universityserviceplatform.repository.ServiceCategoryRepository;
import bekezhan.io.universityserviceplatform.repository.ServiceRequestRepository;
import bekezhan.io.universityserviceplatform.repository.StudentProfileRepository;
import bekezhan.io.universityserviceplatform.service.AuthService;
import bekezhan.io.universityserviceplatform.service.NotificationService;
import bekezhan.io.universityserviceplatform.service.ServiceRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceRequestServiceImpl implements ServiceRequestService {

    private final ServiceRequestRepository requestRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final ServiceCategoryRepository categoryRepository;
    private final ServiceRequestMapper mapper;
    private final AuthService authService;
    private final NotificationService notificationService;

    @Override
    @Transactional
    public ServiceRequestResponseDTO createRequest(ServiceRequestDTO dto) {
        User currentUser = authService.getCurrentUser();

        StudentProfile student = studentProfileRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Student profile not found"));

        ServiceCategory category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        ServiceRequest request = mapper.toEntity(dto);
        request.setStudent(student);
        request.setCategory(category);
        request.setStatus(RequestStatus.NEW);
        request.setCreatedAt(LocalDateTime.now());

        ServiceRequest saved = requestRepository.save(request);

        notificationService.createNotification(
                currentUser.getId(),
                "Your request '" + saved.getTitle() + "' has been created"
        );

        return mapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceRequestResponseDTO getRequestById(Long id) {
        ServiceRequest request = requestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        User currentUser = authService.getCurrentUser();

        if (currentUser.getRole() == User.Role.STUDENT &&
                !request.getStudent().getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Access denied");
        }

        return mapper.toResponseDTO(request);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceRequestResponseDTO> getMyRequests() {
        User currentUser = authService.getCurrentUser();

        StudentProfile student = studentProfileRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Student profile not found"));

        return requestRepository.findByStudentId(student.getId()).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceRequestResponseDTO> getAllRequests() {
        return requestRepository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ServiceRequestResponseDTO updateStatus(Long id, RequestStatus status) {
        ServiceRequest request = requestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setStatus(status);
        ServiceRequest updated = requestRepository.save(request);

        notificationService.createNotification(
                request.getStudent().getUser().getId(),
                "Your request status changed to: " + status
        );

        return mapper.toResponseDTO(updated);
    }
}
