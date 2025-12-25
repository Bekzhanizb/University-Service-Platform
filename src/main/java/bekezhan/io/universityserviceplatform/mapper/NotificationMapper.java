package bekezhan.io.universityserviceplatform.mapper;

import bekezhan.io.universityserviceplatform.dto.NotificationDTO;
import bekezhan.io.universityserviceplatform.entity.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationDTO toDTO(Notification entity);

    Notification toEntity(NotificationDTO dto);
}