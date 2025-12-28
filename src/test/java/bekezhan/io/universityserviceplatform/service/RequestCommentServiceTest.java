package bekezhan.io.universityserviceplatform.service;


import bekezhan.io.universityserviceplatform.dto.RequestCommentRequestDTO;
import bekezhan.io.universityserviceplatform.dto.RequestCommentResponseDTO;
import bekezhan.io.universityserviceplatform.entity.*;
import bekezhan.io.universityserviceplatform.mapper.RequestCommentMapper;
import bekezhan.io.universityserviceplatform.repository.RequestCommentRepository;
import bekezhan.io.universityserviceplatform.repository.ServiceRequestRepository;
import bekezhan.io.universityserviceplatform.repository.UserRepository;
import bekezhan.io.universityserviceplatform.service.implementatoins.RequestCommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequestCommentServiceTest {

    @Mock
    private RequestCommentRepository commentRepository;

    @Mock
    private ServiceRequestRepository requestRepository;

    @Mock
    private RequestCommentMapper mapper;

    @Mock
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private RequestCommentServiceImpl commentService;

    private User currentUser;
    private User studentUser;
    private ServiceRequest serviceRequest;
    private RequestComment comment;
    private RequestCommentRequestDTO requestDTO;
    private RequestCommentResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        currentUser = User.builder()
                .id(1L)
                .email("teacher@example.com")
                .build();

        studentUser = User.builder()
                .id(2L)
                .email("student@example.com")
                .build();

        StudentProfile studentProfile = StudentProfile.builder()
                .id(1L)
                .user(studentUser)
                .build();

        serviceRequest = ServiceRequest.builder()
                .id(1L)
                .title("Test Request")
                .student(studentProfile)
                .build();

        comment = RequestComment.builder()
                .id(1L)
                .request(serviceRequest)
                .user(currentUser)
                .message("Test comment")
                .createdAt(LocalDateTime.now())
                .build();

        requestDTO = RequestCommentRequestDTO.builder()
                .requestId(1L)
                .message("Test comment")
                .build();

        responseDTO = RequestCommentResponseDTO.builder()
                .id(1L)
                .message("Test comment")
                .userId(1L)
                .build();
    }

    @Test
    void addCommentTest() {
        when(requestRepository.findById(1L)).thenReturn(Optional.of(serviceRequest));
        when(userRepository.findById(1L)).thenReturn(Optional.of(currentUser)); // userId passed = 1L
        when(commentRepository.save(any(RequestComment.class))).thenReturn(comment);
        when(mapper.toResponseDTO(comment)).thenReturn(responseDTO);

        RequestCommentResponseDTO result = commentService.addComment(requestDTO, 1L);

        assertNotNull(result);
        assertEquals("Test comment", result.getMessage());
        assertEquals(1L, result.getUserId());

        verify(requestRepository).findById(1L);
        verify(userRepository).findById(1L);
        verify(commentRepository).save(any(RequestComment.class));
        verify(mapper).toResponseDTO(comment);
    }

    @Test
    void getCommentsByRequestId() {
        when(commentRepository.findByRequestId(1L)).thenReturn(Collections.singletonList(comment));
        when(mapper.toResponseDTO(comment)).thenReturn(responseDTO);

        List<RequestCommentResponseDTO> result = commentService.getCommentsByRequest(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(responseDTO.getMessage(), result.get(0).getMessage());
    }
}

