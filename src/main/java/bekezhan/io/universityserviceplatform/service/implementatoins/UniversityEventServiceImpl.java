package bekezhan.io.universityserviceplatform.service.implementatoins;

import bekezhan.io.universityserviceplatform.dto.UniversityEventRequestDTO;
import bekezhan.io.universityserviceplatform.dto.UniversityEventResponseDTO;
import bekezhan.io.universityserviceplatform.entity.UniversityEvent;
import bekezhan.io.universityserviceplatform.entity.User;
import bekezhan.io.universityserviceplatform.mapper.UniversityEventMapper;
import bekezhan.io.universityserviceplatform.repository.UniversityEventRepository;
import bekezhan.io.universityserviceplatform.service.AuthService;
import bekezhan.io.universityserviceplatform.service.UniversityEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UniversityEventServiceImpl implements UniversityEventService {

    private final UniversityEventRepository eventRepository;
    private final UniversityEventMapper mapper;
    private final AuthService authService;

    @Override
    @Transactional
    public UniversityEventResponseDTO createEvent(UniversityEventRequestDTO dto) {
        User currentUser = authService.getCurrentUser();

        UniversityEvent event = mapper.toEntity(dto);
        event.setCreatedBy(currentUser);

        UniversityEvent saved = eventRepository.save(event);
        return mapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UniversityEventResponseDTO> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UniversityEventResponseDTO getEventById(Long id) {
        UniversityEvent event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        return mapper.toResponseDTO(event);
    }
}

