package bekezhan.io.universityserviceplatform.service;

import bekezhan.io.universityserviceplatform.dto.UniversityEventDTO;
import bekezhan.io.universityserviceplatform.entity.*;
import bekezhan.io.universityserviceplatform.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityEventService {

    private final UniversityEventRepository eventRepository;
    private final UserRepository userRepository;

    @Transactional
    public UniversityEvent createEvent(UniversityEventDTO dto, Long createdById) {
        User creator = userRepository.findById(createdById)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UniversityEvent event = UniversityEvent.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .createdBy(creator)
                .build();

        return eventRepository.save(event);
    }

    public List<UniversityEvent> getAllEvents() {
        return eventRepository.findAll();
    }

    public UniversityEvent getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }
}