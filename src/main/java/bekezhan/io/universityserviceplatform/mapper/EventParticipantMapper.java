package bekezhan.io.universityserviceplatform.mapper;

import bekezhan.io.universityserviceplatform.dto.EventParticipantRequestDTO;
import bekezhan.io.universityserviceplatform.dto.EventParticipantResponseDTO;
import bekezhan.io.universityserviceplatform.entity.EventParticipant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventParticipantMapper {

    @Mapping(source = "event.id", target = "eventId")
    @Mapping(source = "user.id", target = "userId")
    EventParticipantResponseDTO toResponseDTO(EventParticipant participant);

    @Mapping(source = "eventId", target = "event.id")
    EventParticipant toEntity(EventParticipantRequestDTO dto);
}