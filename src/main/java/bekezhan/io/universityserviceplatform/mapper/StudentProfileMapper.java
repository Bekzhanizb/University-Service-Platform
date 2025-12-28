package bekezhan.io.universityserviceplatform.mapper;

import bekezhan.io.universityserviceplatform.dto.StudentProfileRequestDTO;
import bekezhan.io.universityserviceplatform.dto.StudentProfileResponseDTO;
import bekezhan.io.universityserviceplatform.entity.StudentProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentProfileMapper {

    @Mapping(source = "user.id", target = "userId")
    StudentProfileResponseDTO toResponseDTO(StudentProfile profile);

    @Mapping(source = "userId", target = "user.id")
    StudentProfile toEntity(StudentProfileRequestDTO dto);
}