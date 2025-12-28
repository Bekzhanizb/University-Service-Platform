package bekezhan.io.universityserviceplatform.service;

import bekezhan.io.universityserviceplatform.dto.ServiceRequestDTO;
import bekezhan.io.universityserviceplatform.dto.ServiceRequestResponseDTO;
import bekezhan.io.universityserviceplatform.entity.ServiceRequest.RequestStatus;

import java.util.List;

public interface ServiceRequestService {
    ServiceRequestResponseDTO createRequest(ServiceRequestDTO dto);
    ServiceRequestResponseDTO getRequestById(Long id);
    List<ServiceRequestResponseDTO> getMyRequests();
    List<ServiceRequestResponseDTO> getAllRequests();
    ServiceRequestResponseDTO updateStatus(Long id, RequestStatus status);
}
