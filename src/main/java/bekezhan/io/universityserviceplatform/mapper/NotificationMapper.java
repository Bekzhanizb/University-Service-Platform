package bekezhan.io.universityserviceplatform.mapper;

import bekezhan.io.universityserviceplatform.dto.NotificationRequestDTO;
import bekezhan.io.universityserviceplatform.dto.NotificationResponseDTO;
import bekezhan.io.universityserviceplatform.entity.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationResponseDTO toResponseDTO(Notification notification);

    Notification toEntity(NotificationRequestDTO dto);
}