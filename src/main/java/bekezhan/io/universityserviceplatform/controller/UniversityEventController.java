package bekezhan.io.universityserviceplatform.controller;

import bekezhan.io.universityserviceplatform.dto.UniversityEventRequestDTO;
import bekezhan.io.universityserviceplatform.entity.UniversityEvent;
import bekezhan.io.universityserviceplatform.service.UniversityEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class UniversityEventController {

    private final UniversityEventService eventService;

    @PostMapping
    public ResponseEntity<UniversityEvent> createEvent(
            @RequestBody UniversityEventRequestDTO dto,
            @RequestParam Long createdById) {
        return ResponseEntity.ok(eventService.createEvent(dto, createdById));
    }

    @GetMapping
    public ResponseEntity<List<UniversityEvent>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UniversityEvent> getEvent(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }
}