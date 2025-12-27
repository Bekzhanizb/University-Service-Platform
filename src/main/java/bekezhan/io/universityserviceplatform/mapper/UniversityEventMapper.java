package bekezhan.io.universityserviceplatform.mapper;

import bekezhan.io.universityserviceplatform.dto.UniversityEventRequestDTO;
import bekezhan.io.universityserviceplatform.entity.UniversityEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UniversityEventMapper {

    UniversityEventRequestDTO toDTO(UniversityEvent entity);

    UniversityEvent toEntity(UniversityEventRequestDTO dto);
}