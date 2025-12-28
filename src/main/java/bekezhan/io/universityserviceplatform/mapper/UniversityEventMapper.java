package bekezhan.io.universityserviceplatform.mapper;

import bekezhan.io.universityserviceplatform.dto.UniversityEventRequestDTO;
import bekezhan.io.universityserviceplatform.dto.UniversityEventResponseDTO;
import bekezhan.io.universityserviceplatform.entity.UniversityEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UniversityEventMapper {

    UniversityEventResponseDTO toResponseDTO(UniversityEvent event);

    UniversityEvent toEntity(UniversityEventRequestDTO dto);
}