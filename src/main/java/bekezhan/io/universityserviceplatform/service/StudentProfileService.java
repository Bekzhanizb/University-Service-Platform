package bekezhan.io.universityserviceplatform.service;

import bekezhan.io.universityserviceplatform.dto.StudentProfileRequestDTO;
import bekezhan.io.universityserviceplatform.dto.StudentProfileResponseDTO;

public interface StudentProfileService {
    StudentProfileResponseDTO createProfile(StudentProfileRequestDTO dto);
    StudentProfileResponseDTO getProfileByUserId(Long userId);
    StudentProfileResponseDTO updateProfile(Long id, StudentProfileRequestDTO dto);
}