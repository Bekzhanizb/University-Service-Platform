package bekezhan.io.universityserviceplatform.controller;

import bekezhan.io.universityserviceplatform.entity.EventParticipant;
import bekezhan.io.universityserviceplatform.service.EventParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participants")
@RequiredArgsConstructor
public class EventParticipantController {

    private final EventParticipantService participantService;

    @PostMapping
    public ResponseEntity<EventParticipant> registerForEvent(
            @RequestParam Long eventId,
            @RequestParam Long userId) {
        return ResponseEntity.ok(participantService.registerForEvent(eventId, userId));
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<EventParticipant>> getEventParticipants(@PathVariable Long eventId) {
        return ResponseEntity.ok(participantService.getEventParticipants(eventId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<EventParticipant>> getUserEvents(@PathVariable Long userId) {
        return ResponseEntity.ok(participantService.getUserEvents(userId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<EventParticipant> updateStatus(
            @PathVariable Long id,
            @RequestParam EventParticipant.ParticipantStatus status) {
        return ResponseEntity.ok(participantService.updateStatus(id, status));
    }
}