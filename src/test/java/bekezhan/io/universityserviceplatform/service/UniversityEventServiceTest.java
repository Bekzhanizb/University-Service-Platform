package bekezhan.io.universityserviceplatform.service;


import bekezhan.io.universityserviceplatform.dto.UniversityEventRequestDTO;
import bekezhan.io.universityserviceplatform.dto.UniversityEventResponseDTO;
import bekezhan.io.universityserviceplatform.entity.UniversityEvent;
import bekezhan.io.universityserviceplatform.entity.User;
import bekezhan.io.universityserviceplatform.mapper.UniversityEventMapper;
import bekezhan.io.universityserviceplatform.repository.UniversityEventRepository;
import bekezhan.io.universityserviceplatform.service.implementatoins.UniversityEventServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UniversityEventServiceTest {

    @Mock
    private UniversityEventRepository eventRepository;

    @Mock
    private UniversityEventMapper mapper;

    @Mock
    private AuthService authService;

    @InjectMocks
    private UniversityEventServiceImpl eventService;

    private User currentUser;
    private UniversityEvent event;
    private UniversityEventRequestDTO requestDTO;
    private UniversityEventResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        currentUser = User.builder()
                .id(1L)
                .email("admin@example.com")
                .role(User.Role.ADMIN)
                .build();

        event = UniversityEvent.builder()
                .id(1L)
                .title("Career Fair")
                .description("Annual career fair")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusHours(8))
                .createdBy(currentUser)
                .build();

        requestDTO = UniversityEventRequestDTO.builder()
                .title("Career Fair")
                .description("Annual career fair")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusHours(8))
                .build();

        responseDTO = UniversityEventResponseDTO.builder()
                .id(1L)
                .title("Career Fair")
                .description("Annual career fair")
                .build();
    }

    @Test
    void createEvent_Success() {
        when(authService.getCurrentUser()).thenReturn(currentUser);
        when(mapper.toEntity(requestDTO)).thenReturn(event);
        when(eventRepository.save(any(UniversityEvent.class))).thenReturn(event);
        when(mapper.toResponseDTO(event)).thenReturn(responseDTO);

        UniversityEventResponseDTO result = eventService.createEvent(requestDTO);

        assertNotNull(result);
        assertEquals(responseDTO.getTitle(), result.getTitle());
        verify(eventRepository).save(any(UniversityEvent.class));
    }

    @Test
    void getAllEvents_Success() {
        when(eventRepository.findAll()).thenReturn(Arrays.asList(event));
        when(mapper.toResponseDTO(event)).thenReturn(responseDTO);

        List<UniversityEventResponseDTO> result = eventService.getAllEvents();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(responseDTO.getTitle(), result.get(0).getTitle());
    }

    @Test
    void getEventById_Success() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(mapper.toResponseDTO(event)).thenReturn(responseDTO);

        UniversityEventResponseDTO result = eventService.getEventById(1L);

        assertNotNull(result);
        assertEquals(responseDTO.getTitle(), result.getTitle());
    }

    @Test
    void getEventById_NotFound_ThrowsException() {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> eventService.getEventById(1L));
    }
}

