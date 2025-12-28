package bekezhan.io.universityserviceplatform.controller;

import bekezhan.io.universityserviceplatform.dto.StudentProfileRequestDTO;
import bekezhan.io.universityserviceplatform.dto.StudentProfileResponseDTO;
import bekezhan.io.universityserviceplatform.dto.TeacherProfileRequestDTO;
import bekezhan.io.universityserviceplatform.dto.TeacherProfileResponseDTO;
import bekezhan.io.universityserviceplatform.service.StudentProfileService;
import bekezhan.io.universityserviceplatform.service.TeacherProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final StudentProfileService studentProfileService;
    private final TeacherProfileService teacherProfileService;


    @PostMapping("/students")
    public ResponseEntity<StudentProfileResponseDTO> createStudentProfile(@RequestBody StudentProfileRequestDTO dto) {
        return ResponseEntity.ok(studentProfileService.createProfile(dto));
    }

    @GetMapping("/students/user/{userId}")
    public ResponseEntity<StudentProfileResponseDTO> getStudentProfileByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(studentProfileService.getProfileByUserId(userId));
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<StudentProfileResponseDTO> updateStudentProfile(
            @PathVariable Long id,
            @RequestBody StudentProfileRequestDTO dto
    ) {
        return ResponseEntity.ok(studentProfileService.updateProfile(id, dto));
    }

    @PostMapping("/teachers")
    public ResponseEntity<TeacherProfileResponseDTO> createTeacherProfile(@RequestBody TeacherProfileRequestDTO dto) {
        return ResponseEntity.ok(teacherProfileService.createProfile(dto));
    }

    @GetMapping("/teachers/user/{userId}")
    public ResponseEntity<TeacherProfileResponseDTO> getTeacherProfileByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(teacherProfileService.getProfileByUserId(userId));
    }

    @PutMapping("/teachers/{id}")
    public ResponseEntity<TeacherProfileResponseDTO> updateTeacherProfile(
            @PathVariable Long id,
            @RequestBody TeacherProfileRequestDTO dto
    ) {
        return ResponseEntity.ok(teacherProfileService.updateProfile(id, dto));
    }
}