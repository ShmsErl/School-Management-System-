package sehmus.school_management_system.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sehmus.school_management_system.payload.requests.concretes.TeacherRequest;
import sehmus.school_management_system.payload.responses.concretes.ResponseMessage;
import sehmus.school_management_system.payload.responses.concretes.UserResponse;
import sehmus.school_management_system.services.user.TeacherService;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PreAuthorize("hasAnyAuthority('Admin')")
    @PostMapping("/save")
    public ResponseEntity<ResponseMessage<UserResponse>> saveTeacher(@Valid @RequestBody TeacherRequest teacherRequest){

        return ResponseEntity.ok(teacherService.saveTeacher(teacherRequest));
    }

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher')")
    @GetMapping("/deleteAdvisorTeacherById/{id}")
    public ResponseMessage<UserResponse> deleteTeacherById(@PathVariable Long id){

        return teacherService.changeAdvisorTeacherStatus(id);

    }

}
