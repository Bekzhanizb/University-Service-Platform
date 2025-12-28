package bekezhan.io.universityserviceplatform.service;

import bekezhan.io.universityserviceplatform.dto.ServiceCategoryRequestDTO;
import bekezhan.io.universityserviceplatform.dto.ServiceCategoryResponseDTO;

import java.util.List;

public interface ServiceCategoryService {
    ServiceCategoryResponseDTO createCategory(ServiceCategoryRequestDTO dto);
    List<ServiceCategoryResponseDTO> getAllCategories();
    ServiceCategoryResponseDTO getCategoryById(Long id);
}