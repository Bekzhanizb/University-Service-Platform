package bekezhan.io.universityserviceplatform.mapper;

import bekezhan.io.universityserviceplatform.dto.RequestAssignmentRequestDTO;
import bekezhan.io.universityserviceplatform.dto.RequestAssignmentResponseDTO;
import bekezhan.io.universityserviceplatform.entity.RequestAssignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RequestAssignmentMapper {

    @Mapping(source = "request.id", target = "requestId")
    @Mapping(source = "teacher.id", target = "teacherId")
    RequestAssignmentResponseDTO toResponseDTO(RequestAssignment assignment);

    @Mapping(source = "requestId", target = "request.id")
    @Mapping(source = "teacherId", target = "teacher.id")
    RequestAssignment toEntity(RequestAssignmentRequestDTO dto);
}