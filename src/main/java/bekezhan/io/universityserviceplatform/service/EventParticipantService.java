package bekezhan.io.universityserviceplatform.service;

import bekezhan.io.universityserviceplatform.dto.EventParticipantResponseDTO;
import bekezhan.io.universityserviceplatform.entity.EventParticipant.ParticipantStatus;

import java.util.List;

public interface EventParticipantService {
    EventParticipantResponseDTO registerForEvent(Long eventId);
    List<EventParticipantResponseDTO> getEventParticipants(Long eventId);
    List<EventParticipantResponseDTO> getMyEvents();
    EventParticipantResponseDTO updateStatus(Long participantId, ParticipantStatus status);
}
