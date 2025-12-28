package bekezhan.io.universityserviceplatform.controller;

import bekezhan.io.universityserviceplatform.dto.EventParticipantResponseDTO;
import bekezhan.io.universityserviceplatform.dto.UniversityEventRequestDTO;
import bekezhan.io.universityserviceplatform.dto.UniversityEventResponseDTO;
import bekezhan.io.universityserviceplatform.entity.EventParticipant.ParticipantStatus;
import bekezhan.io.universityserviceplatform.service.EventParticipantService;
import bekezhan.io.universityserviceplatform.service.UniversityEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final UniversityEventService universityEventService;
    private final EventParticipantService eventParticipantService;
    

    @PostMapping
    public ResponseEntity<UniversityEventResponseDTO> createEvent(@RequestBody UniversityEventRequestDTO dto) {
        return ResponseEntity.ok(universityEventService.createEvent(dto));
    }

    @GetMapping
    public ResponseEntity<List<UniversityEventResponseDTO>> getAllEvents() {
        return ResponseEntity.ok(universityEventService.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UniversityEventResponseDTO> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(universityEventService.getEventById(id));
    }


    @PostMapping("/{eventId}/register")
    public ResponseEntity<EventParticipantResponseDTO> registerForEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventParticipantService.registerForEvent(eventId));
    }

    @GetMapping("/{eventId}/participants")
    public ResponseEntity<List<EventParticipantResponseDTO>> getEventParticipants(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventParticipantService.getEventParticipants(eventId));
    }

    @GetMapping("/my-events")
    public ResponseEntity<List<EventParticipantResponseDTO>> getMyEvents() {
        return ResponseEntity.ok(eventParticipantService.getMyEvents());
    }

    @PatchMapping("/participants/{participantId}/status")
    public ResponseEntity<EventParticipantResponseDTO> updateParticipantStatus(
            @PathVariable Long participantId,
            @RequestParam ParticipantStatus status
    ) {
        return ResponseEntity.ok(eventParticipantService.updateStatus(participantId, status));
    }
}