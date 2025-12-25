package bekezhan.io.universityserviceplatform.mapper;

import bekezhan.io.universityserviceplatform.dto.EventParticipantDTO;
import bekezhan.io.universityserviceplatform.entity.EventParticipant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventParticipantMapper {

    @Mapping(source = "event.id", target = "eventId")
    @Mapping(source = "user.id", target = "userId")
    EventParticipantDTO toDTO(EventParticipant entity);

    @Mapping(source = "eventId", target = "event.id")
    @Mapping(source = "userId", target = "user.id")
    EventParticipant toEntity(EventParticipantDTO dto);
}