package bekezhan.io.universityserviceplatform.service;

import bekezhan.io.universityserviceplatform.dto.NotificationResponseDTO;

import java.util.List;

public interface NotificationService {
    void createNotification(Long userId, String message);
    List<NotificationResponseDTO> getMyNotifications();
    void markAsRead(Long notificationId);
    void deleteNotification(Long notificationId);
}