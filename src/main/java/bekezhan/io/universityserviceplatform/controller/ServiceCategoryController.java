package bekezhan.io.universityserviceplatform.controller;

import bekezhan.io.universityserviceplatform.dto.ServiceCategoryRequestDTO;
import bekezhan.io.universityserviceplatform.dto.ServiceCategoryResponseDTO;
import bekezhan.io.universityserviceplatform.service.ServiceCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class ServiceCategoryController {

    private final ServiceCategoryService serviceCategoryService;

    @PostMapping
    public ResponseEntity<ServiceCategoryResponseDTO> createCategory(@RequestBody ServiceCategoryRequestDTO dto) {
        return ResponseEntity.ok(serviceCategoryService.createCategory(dto));
    }

    @GetMapping
    public ResponseEntity<List<ServiceCategoryResponseDTO>> getAllCategories() {
        return ResponseEntity.ok(serviceCategoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceCategoryResponseDTO> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceCategoryService.getCategoryById(id));
    }
}