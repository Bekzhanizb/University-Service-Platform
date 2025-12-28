package bekezhan.io.universityserviceplatform.mapper;

import bekezhan.io.universityserviceplatform.dto.ServiceCategoryRequestDTO;
import bekezhan.io.universityserviceplatform.dto.ServiceCategoryResponseDTO;
import bekezhan.io.universityserviceplatform.entity.ServiceCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceCategoryMapper {

    ServiceCategoryResponseDTO toResponseDTO(ServiceCategory category);

    ServiceCategory toEntity(ServiceCategoryRequestDTO dto);
}