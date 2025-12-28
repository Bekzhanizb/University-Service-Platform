package bekezhan.io.universityserviceplatform.service;

import bekezhan.io.universityserviceplatform.dto.TeacherProfileRequestDTO;
import bekezhan.io.universityserviceplatform.dto.TeacherProfileResponseDTO;

public interface TeacherProfileService {
    TeacherProfileResponseDTO createProfile(TeacherProfileRequestDTO dto);
    TeacherProfileResponseDTO getProfileByUserId(Long userId);
    TeacherProfileResponseDTO updateProfile(Long id, TeacherProfileRequestDTO dto);
}