package bekezhan.io.universityserviceplatform.mapper;

import bekezhan.io.universityserviceplatform.dto.ServiceRequestDTO;
import bekezhan.io.universityserviceplatform.entity.ServiceRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServiceRequestMapper {

    @Mapping(source = "category.id", target = "categoryId")
    ServiceRequestDTO toDTO(ServiceRequest entity);

    @Mapping(source = "categoryId", target = "category.id")
    ServiceRequest toEntity(ServiceRequestDTO dto);
}