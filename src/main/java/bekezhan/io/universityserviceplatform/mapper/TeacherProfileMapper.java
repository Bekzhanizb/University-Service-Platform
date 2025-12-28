package bekezhan.io.universityserviceplatform.mapper;

import bekezhan.io.universityserviceplatform.dto.TeacherProfileRequestDTO;
import bekezhan.io.universityserviceplatform.dto.TeacherProfileResponseDTO;
import bekezhan.io.universityserviceplatform.entity.TeacherProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeacherProfileMapper {

    @Mapping(source = "user.id", target = "userId")
    TeacherProfileResponseDTO toResponseDTO(TeacherProfile profile);

    @Mapping(source = "userId", target = "user.id")
    TeacherProfile toEntity(TeacherProfileRequestDTO dto);
}