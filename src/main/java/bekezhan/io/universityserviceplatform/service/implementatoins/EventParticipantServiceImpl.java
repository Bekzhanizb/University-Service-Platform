package bekezhan.io.universityserviceplatform.service.implementatoins;

import bekezhan.io.universityserviceplatform.dto.EventParticipantResponseDTO;
import bekezhan.io.universityserviceplatform.entity.EventParticipant;
import bekezhan.io.universityserviceplatform.entity.EventParticipant.ParticipantStatus;
import bekezhan.io.universityserviceplatform.entity.UniversityEvent;
import bekezhan.io.universityserviceplatform.entity.User;
import bekezhan.io.universityserviceplatform.mapper.EventParticipantMapper;
import bekezhan.io.universityserviceplatform.repository.EventParticipantRepository;
import bekezhan.io.universityserviceplatform.repository.UniversityEventRepository;
import bekezhan.io.universityserviceplatform.service.AuthService;
import bekezhan.io.universityserviceplatform.service.EventParticipantService;
import bekezhan.io.universityserviceplatform.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventParticipantServiceImpl implements EventParticipantService {

    private final EventParticipantRepository participantRepository;
    private final UniversityEventRepository eventRepository;
    private final EventParticipantMapper mapper;
    private final AuthService authService;
    private final NotificationService notificationService;

    @Override
    @Transactional
    public EventParticipantResponseDTO registerForEvent(Long eventId) {
        User currentUser = authService.getCurrentUser();

        UniversityEvent event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        EventParticipant participant = EventParticipant.builder()
                .event(event)
                .user(currentUser)
                .status(ParticipantStatus.REGISTERED)
                .build();

        EventParticipant saved = participantRepository.save(participant);

        notificationService.createNotification(
                currentUser.getId(),
                "You registered for event: " + event.getTitle()
        );

        return mapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventParticipantResponseDTO> getEventParticipants(Long eventId) {
        return participantRepository.findByEventId(eventId).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventParticipantResponseDTO> getMyEvents() {
        User currentUser = authService.getCurrentUser();
        return participantRepository.findByUserId(currentUser.getId()).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventParticipantResponseDTO updateStatus(Long participantId, ParticipantStatus status) {
        EventParticipant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new RuntimeException("Participant not found"));

        participant.setStatus(status);
        EventParticipant updated = participantRepository.save(participant);

        return mapper.toResponseDTO(updated);
    }
}
