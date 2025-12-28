package bekezhan.io.universityserviceplatform.service;

import bekezhan.io.universityserviceplatform.dto.ServiceRequestDTO;
import bekezhan.io.universityserviceplatform.dto.ServiceRequestResponseDTO;
import bekezhan.io.universityserviceplatform.entity.*;
import bekezhan.io.universityserviceplatform.mapper.ServiceRequestMapper;
import bekezhan.io.universityserviceplatform.repository.*;
import bekezhan.io.universityserviceplatform.service.implementatoins.ServiceRequestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceRequestServiceTest {

    @Mock
    private ServiceRequestRepository requestRepository;

    @Mock
    private StudentProfileRepository studentProfileRepository;

    @Mock
    private ServiceCategoryRepository categoryRepository;

    @Mock
    private ServiceRequestMapper mapper;

    @Mock
    private AuthService authService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private ServiceRequestServiceImpl requestService;

    private User currentUser;
    private StudentProfile studentProfile;
    private ServiceCategory category;
    private ServiceRequest serviceRequest;
    private ServiceRequestDTO requestDTO;
    private ServiceRequestResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        currentUser = User.builder()
                .id(1L)
                .email("student@example.com")
                .role(User.Role.STUDENT)
                .build();

        studentProfile = StudentProfile.builder()
                .id(1L)
                .user(currentUser)
                .studentCode("ST001")
                .build();

        category = ServiceCategory.builder()
                .id(1L)
                .name("Academic Support")
                .build();

        serviceRequest = ServiceRequest.builder()
                .id(1L)
                .title("Need help")
                .description("Description")
                .status(ServiceRequest.RequestStatus.NEW)
                .student(studentProfile)
                .category(category)
                .build();

        requestDTO = ServiceRequestDTO.builder()
                .title("Need help")
                .description("Description")
                .categoryId(1L)
                .build();

        responseDTO = ServiceRequestResponseDTO.builder()
                .id(1L)
                .title("Need help")
                .status(ServiceRequest.RequestStatus.NEW)
                .build();
    }

    @Test
    void createRequest_Success() {
        when(authService.getCurrentUser()).thenReturn(currentUser);
        when(studentProfileRepository.findByUserId(currentUser.getId()))
                .thenReturn(Optional.of(studentProfile));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(mapper.toEntity(requestDTO)).thenReturn(serviceRequest);
        when(requestRepository.save(any(ServiceRequest.class))).thenReturn(serviceRequest);
        when(mapper.toResponseDTO(serviceRequest)).thenReturn(responseDTO);

        ServiceRequestResponseDTO result = requestService.createRequest(requestDTO);

        assertNotNull(result);
        assertEquals(responseDTO.getTitle(), result.getTitle());
        verify(requestRepository).save(any(ServiceRequest.class));
        verify(notificationService).createNotification(anyLong(), anyString());
    }

    @Test
    void createRequest_StudentProfileNotFound_ThrowsException() {
        when(authService.getCurrentUser()).thenReturn(currentUser);
        when(studentProfileRepository.findByUserId(currentUser.getId()))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> requestService.createRequest(requestDTO));
        verify(requestRepository, never()).save(any(ServiceRequest.class));
    }

    @Test
    void updateStatus_Success() {
        when(requestRepository.findById(1L)).thenReturn(Optional.of(serviceRequest));
        when(requestRepository.save(any(ServiceRequest.class))).thenReturn(serviceRequest);
        when(mapper.toResponseDTO(serviceRequest)).thenReturn(responseDTO);

        ServiceRequestResponseDTO result = requestService.updateStatus(
                1L, ServiceRequest.RequestStatus.IN_PROGRESS);

        assertNotNull(result);
        verify(requestRepository).save(any(ServiceRequest.class));
        verify(notificationService).createNotification(anyLong(), anyString());
    }
}
