package bekezhan.io.universityserviceplatform.service.implementatoins;

import bekezhan.io.universityserviceplatform.dto.ServiceCategoryRequestDTO;
import bekezhan.io.universityserviceplatform.dto.ServiceCategoryResponseDTO;
import bekezhan.io.universityserviceplatform.entity.ServiceCategory;
import bekezhan.io.universityserviceplatform.mapper.ServiceCategoryMapper;
import bekezhan.io.universityserviceplatform.repository.ServiceCategoryRepository;
import bekezhan.io.universityserviceplatform.service.ServiceCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceCategoryServiceImpl implements ServiceCategoryService {

    private final ServiceCategoryRepository categoryRepository;
    private final ServiceCategoryMapper mapper;

    @Override
    @Transactional
    public ServiceCategoryResponseDTO createCategory(ServiceCategoryRequestDTO dto) {
        ServiceCategory category = mapper.toEntity(dto);
        ServiceCategory saved = categoryRepository.save(category);
        return mapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceCategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceCategoryResponseDTO getCategoryById(Long id) {
        ServiceCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return mapper.toResponseDTO(category);
    }
}
