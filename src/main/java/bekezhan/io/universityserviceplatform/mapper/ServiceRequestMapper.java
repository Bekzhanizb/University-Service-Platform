package bekezhan.io.universityserviceplatform.mapper;

import bekezhan.io.universityserviceplatform.dto.ServiceRequestDTO;
import bekezhan.io.universityserviceplatform.dto.ServiceRequestResponseDTO;
import bekezhan.io.universityserviceplatform.entity.ServiceRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServiceRequestMapper {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "category.id", target = "categoryId")
    ServiceRequestResponseDTO toResponseDTO(ServiceRequest request);

    @Mapping(source = "categoryId", target = "category.id")
    ServiceRequest toEntity(ServiceRequestDTO dto);
}