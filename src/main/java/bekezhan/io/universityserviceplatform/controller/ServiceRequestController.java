package bekezhan.io.universityserviceplatform.controller;

import bekezhan.io.universityserviceplatform.dto.*;
import bekezhan.io.universityserviceplatform.entity.ServiceRequest.RequestStatus;
import bekezhan.io.universityserviceplatform.service.RequestAssignmentService;
import bekezhan.io.universityserviceplatform.service.RequestCommentService;
import bekezhan.io.universityserviceplatform.service.ServiceRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class ServiceRequestController {

    private final ServiceRequestService serviceRequestService;
    private final RequestCommentService requestCommentService;
    private final RequestAssignmentService requestAssignmentService;


    @PostMapping
    public ResponseEntity<ServiceRequestResponseDTO> createRequest(@RequestBody ServiceRequestDTO dto) {
        return ResponseEntity.ok(serviceRequestService.createRequest(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceRequestResponseDTO> getRequestById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceRequestService.getRequestById(id));
    }

    @GetMapping("/my")
    public ResponseEntity<List<ServiceRequestResponseDTO>> getMyRequests() {
        return ResponseEntity.ok(serviceRequestService.getMyRequests());
    }

    @GetMapping
    public ResponseEntity<List<ServiceRequestResponseDTO>> getAllRequests() {
        return ResponseEntity.ok(serviceRequestService.getAllRequests());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ServiceRequestResponseDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam RequestStatus status
    ) {
        return ResponseEntity.ok(serviceRequestService.updateStatus(id, status));
    }


    @PostMapping("/{requestId}/comments")
    public ResponseEntity<RequestCommentResponseDTO> addComment(
            @PathVariable Long requestId,
            @RequestBody RequestCommentRequestDTO dto,
            @RequestParam Long userId
    ) {
        dto.setRequestId(requestId); // Set from path
        return ResponseEntity.ok(requestCommentService.addComment(dto, userId));
    }

    @GetMapping("/{requestId}/comments")
    public ResponseEntity<List<RequestCommentResponseDTO>> getComments(@PathVariable Long requestId) {
        return ResponseEntity.ok(requestCommentService.getCommentsByRequest(requestId));
    }


    @PostMapping("/{requestId}/assign")
    public ResponseEntity<RequestAssignmentResponseDTO> assignTeacher(
            @PathVariable Long requestId,
            @RequestBody RequestAssignmentRequestDTO dto
    ) {
        dto.setRequestId(requestId);
        return ResponseEntity.ok(requestAssignmentService.assignTeacher(dto));
    }

    @GetMapping("/{requestId}/assignment")
    public ResponseEntity<RequestAssignmentResponseDTO> getAssignment(@PathVariable Long requestId) {
        return ResponseEntity.ok(requestAssignmentService.getByRequestId(requestId));
    }
}