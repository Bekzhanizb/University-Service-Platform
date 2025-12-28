package bekezhan.io.universityserviceplatform.service;

import bekezhan.io.universityserviceplatform.dto.UniversityEventRequestDTO;
import bekezhan.io.universityserviceplatform.dto.UniversityEventResponseDTO;

import java.util.List;

public interface UniversityEventService {
    UniversityEventResponseDTO createEvent(UniversityEventRequestDTO dto);
    List<UniversityEventResponseDTO> getAllEvents();
    UniversityEventResponseDTO getEventById(Long id);
}