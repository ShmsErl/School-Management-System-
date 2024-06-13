package sehmus.school_management_system.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sehmus.school_management_system.payload.requests.concretes.AddLessonProgramToTeacherRequest;
import sehmus.school_management_system.payload.requests.concretes.TeacherRequest;
import sehmus.school_management_system.payload.responses.concretes.ResponseMessage;
import sehmus.school_management_system.payload.responses.concretes.StudentResponse;
import sehmus.school_management_system.payload.responses.concretes.UserResponse;
import sehmus.school_management_system.services.user.TeacherService;

import java.util.List;

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

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @GetMapping("/getAllAdvisorTeacher")
    public List<UserResponse> getAllAdvisorTeacher(){

        return teacherService.getAllAdvisorTeacher();

    }

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @PostMapping("/addLessonProgram")
    public ResponseMessage<UserResponse> chooseLesson(@Valid @RequestBody AddLessonProgramToTeacherRequest addLessonProgramToTeacherRequest){

        return teacherService.addLessonProgramToTeacher(addLessonProgramToTeacherRequest);

    }

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @PutMapping("/updateTeacher/{userId}")
    public ResponseMessage<UserResponse> updateTeacherByManagers(@Valid @RequestBody TeacherRequest teacherRequest,
                                                                 @PathVariable Long userId){

        return teacherService.updateTeacher(teacherRequest, userId);

    }

    @PreAuthorize("hasAnyAuthority('Teacher')")
    @GetMapping("/getAllStudentByAdvisorTeacher")
    public List<StudentResponse> getAllStudentByAdvisorTeacher(HttpServletRequest httpServletRequest){

        return teacherService.getAllStudentByAdvisorTeacher(httpServletRequest);

    }


}
