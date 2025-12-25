package bekezhan.io.universityserviceplatform.controller;

import bekezhan.io.universityserviceplatform.dto.RequestCommentDTO;
import bekezhan.io.universityserviceplatform.entity.RequestComment;
import bekezhan.io.universityserviceplatform.service.RequestCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class RequestCommentController {

    private final RequestCommentService commentService;

    @PostMapping
    public ResponseEntity<RequestComment> addComment(
            @RequestBody RequestCommentDTO dto,
            @RequestParam Long userId) {
        return ResponseEntity.ok(commentService.addComment(dto, userId));
    }

    @GetMapping("/request/{requestId}")
    public ResponseEntity<List<RequestComment>> getComments(@PathVariable Long requestId) {
        return ResponseEntity.ok(commentService.getCommentsByRequestId(requestId));
    }
}