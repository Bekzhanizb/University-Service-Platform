package bekezhan.io.universityserviceplatform.mapper;

import bekezhan.io.universityserviceplatform.dto.UniversityEventDTO;
import bekezhan.io.universityserviceplatform.entity.UniversityEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UniversityEventMapper {

    UniversityEventDTO toDTO(UniversityEvent entity);

    UniversityEvent toEntity(UniversityEventDTO dto);
}