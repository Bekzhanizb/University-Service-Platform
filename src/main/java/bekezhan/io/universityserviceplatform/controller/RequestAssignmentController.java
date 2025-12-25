package bekezhan.io.universityserviceplatform.controller;

import bekezhan.io.universityserviceplatform.entity.RequestAssignment;
import bekezhan.io.universityserviceplatform.service.RequestAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
public class RequestAssignmentController {

    private final RequestAssignmentService assignmentService;

    @PostMapping
    public ResponseEntity<RequestAssignment> assignTeacher(
            @RequestParam Long requestId,
            @RequestParam Long teacherId) {
        return ResponseEntity.ok(assignmentService.assignTeacher(requestId, teacherId));
    }

    @GetMapping("/request/{requestId}")
    public ResponseEntity<RequestAssignment> getAssignment(@PathVariable Long requestId) {
        return ResponseEntity.ok(assignmentService.getAssignmentByRequestId(requestId));
    }
}