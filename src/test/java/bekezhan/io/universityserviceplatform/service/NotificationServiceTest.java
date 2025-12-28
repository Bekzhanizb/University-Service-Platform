package bekezhan.io.universityserviceplatform.service;

import bekezhan.io.universityserviceplatform.dto.NotificationResponseDTO;
import bekezhan.io.universityserviceplatform.entity.Notification;
import bekezhan.io.universityserviceplatform.entity.User;
import bekezhan.io.universityserviceplatform.mapper.NotificationMapper;
import bekezhan.io.universityserviceplatform.repository.NotificationRepository;
import bekezhan.io.universityserviceplatform.repository.UserRepository;
import bekezhan.io.universityserviceplatform.service.implementatoins.NotificationServiceImpl;
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
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private NotificationMapper mapper;

    @Mock
    private AuthService authService;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private User user;
    private Notification notification;
    private NotificationResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .email("user@example.com")
                .build();

        notification = Notification.builder()
                .id(1L)
                .user(user)
                .message("Test notification")
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();

        responseDTO = NotificationResponseDTO.builder()
                .id(1L)
                .message("Test notification")
                .isRead(false)
                .build();
    }

    @Test
    void createNotification_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        notificationService.createNotification(1L, "Test notification");

        verify(notificationRepository).save(any(Notification.class));
    }

    @Test
    void getMyNotifications_Success() {
        when(authService.getCurrentUser()).thenReturn(user);
        when(notificationRepository.findByUserIdOrderByCreatedAtDesc(1L))
                .thenReturn(Collections.singletonList(notification));
        when(mapper.toDto(notification)).thenReturn(responseDTO);

        List<NotificationResponseDTO> result = notificationService.getMyNotifications();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(responseDTO.getMessage(), result.get(0).getMessage());
    }

    @Test
    void markAsRead_Success() {
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));
        when(authService.getCurrentUser()).thenReturn(user);
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        notificationService.markAsRead(1L);

        assertTrue(notification.getIsRead());
        verify(notificationRepository).save(notification);
    }

    @Test
    void markAsRead_AccessDenied_ThrowsException() {
        User anotherUser = User.builder().id(2L).build();
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));
        when(authService.getCurrentUser()).thenReturn(anotherUser);

        assertThrows(RuntimeException.class, () -> notificationService.markAsRead(1L));
        verify(notificationRepository, never()).save(any(Notification.class));
    }
}

