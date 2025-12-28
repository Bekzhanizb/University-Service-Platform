package bekezhan.io.universityserviceplatform.mapper;

import bekezhan.io.universityserviceplatform.dto.RequestCommentRequestDTO;
import bekezhan.io.universityserviceplatform.dto.RequestCommentResponseDTO;
import bekezhan.io.universityserviceplatform.entity.RequestComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RequestCommentMapper {

    @Mapping(source = "user.id", target = "userId")
    RequestCommentResponseDTO toResponseDTO(RequestComment comment);

    @Mapping(source = "requestId", target = "request.id")
    RequestComment toEntity(RequestCommentRequestDTO dto);
}