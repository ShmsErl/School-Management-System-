package sehmus.school_management_system.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sehmus.school_management_system.payload.requests.concretes.ChooseLessonProgramRequest;
import sehmus.school_management_system.payload.requests.concretes.StudentRequest;
import sehmus.school_management_system.payload.requests.concretes.StudentUpdateRequestWithoutPassword;
import sehmus.school_management_system.payload.responses.concretes.ResponseMessage;
import sehmus.school_management_system.payload.responses.concretes.StudentResponse;
import sehmus.school_management_system.services.user.StudentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;


    @PreAuthorize("hasAnyAuthority('Admin')")
    @PostMapping("/save")
    public ResponseEntity<ResponseMessage<StudentResponse>> saveStudent(@Valid @RequestBody StudentRequest studentRequest){

        return ResponseEntity.ok(studentService.saveStudent(studentRequest));

    }

    @PreAuthorize("hasAnyAuthority('Student')")
    @PatchMapping("/updateStudent")
    public ResponseEntity<String> updateStudent(
            @RequestBody @Valid StudentUpdateRequestWithoutPassword studentUpdateRequestWithoutPassword,
            HttpServletRequest httpServletRequest){

        return studentService.updateStudent(studentUpdateRequestWithoutPassword, httpServletRequest);

    }

    @PreAuthorize("hasAnyAuthority('Admin', 'Dean', 'ViceDean')")
    @PutMapping("/updateStudentForManagers/{id}")
    public ResponseMessage<StudentResponse> updateStudentForManagers(@PathVariable Long id,
                                                                     @RequestBody @Valid StudentRequest studentRequest){


        return studentService.updateStudentForManagers(id, studentRequest);

    }

    @PreAuthorize("hasAnyAuthority('Student')")
    @PostMapping("/addLessonProgram")
    public ResponseMessage<StudentResponse> addLessonProgram(HttpServletRequest httpServletRequest,
                                                             @Valid @RequestBody ChooseLessonProgramRequest request){

        return studentService.addLessonProgram(httpServletRequest, request);

    }

    @PreAuthorize("hasAnyAuthority('Admin', 'Dean', 'ViceDean')")
    @GetMapping("/changeStatus")
    public ResponseMessage changeStatus(@RequestParam Long id,
                                        @RequestParam boolean status){

        return studentService.changeStatus(id, status);

    }


}
