package bekezhan.io.universityserviceplatform.controller;

import bekezhan.io.universityserviceplatform.dto.ServiceRequestDTO;
import bekezhan.io.universityserviceplatform.entity.ServiceRequest;
import bekezhan.io.universityserviceplatform.service.implementatoins.ServiceRequestServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class ServiceRequestController {

    private final ServiceRequestServiceImpl requestService;

    @PostMapping
    public ResponseEntity<ServiceRequest> createRequest(
            @RequestBody ServiceRequestDTO dto,
            @RequestParam Long studentId) {
        return ResponseEntity.ok(requestService.createRequest(dto, studentId));
    }

    @GetMapping
    public ResponseEntity<List<ServiceRequest>> getAllRequests() {
        return ResponseEntity.ok(requestService.getAllRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceRequest> getRequest(@PathVariable Long id) {
        return ResponseEntity.ok(requestService.getRequestById(id));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ServiceRequest>> getStudentRequests(@PathVariable Long studentId) {
        return ResponseEntity.ok(requestService.getStudentRequests(studentId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ServiceRequest> updateStatus(
            @PathVariable Long id,
            @RequestParam ServiceRequest.RequestStatus status) {
        return ResponseEntity.ok(requestService.updateStatus(id, status));
    }
}