package bekezhan.io.universityserviceplatform.service;

import bekezhan.io.universityserviceplatform.entity.*;
import bekezhan.io.universityserviceplatform.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventParticipantService {

    private final EventParticipantRepository participantRepository;
    private final UniversityEventRepository eventRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Transactional
    public EventParticipant registerForEvent(Long eventId, Long userId) {
        UniversityEvent event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        EventParticipant participant = EventParticipant.builder()
                .event(event)
                .user(user)
                .status(EventParticipant.ParticipantStatus.REGISTERED)
                .build();

        EventParticipant saved = participantRepository.save(participant);

        notificationService.createNotification(
                user,
                "Вы зарегистрированы на событие: " + event.getTitle()
        );

        return saved;
    }

    public List<EventParticipant> getEventParticipants(Long eventId) {
        return participantRepository.findByEventId(eventId);
    }

    public List<EventParticipant> getUserEvents(Long userId) {
        return participantRepository.findByUserId(userId);
    }

    @Transactional
    public EventParticipant updateStatus(Long participantId, EventParticipant.ParticipantStatus status) {
        EventParticipant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new RuntimeException("Participant not found"));

        participant.setStatus(status);
        return participantRepository.save(participant);
    }
}