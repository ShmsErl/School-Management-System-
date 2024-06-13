package sehmus.school_management_system.controller.business;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sehmus.school_management_system.payload.requests.concretes.StudentInfoRequest;
import sehmus.school_management_system.payload.requests.concretes.StudentInfoUpdateRequest;
import sehmus.school_management_system.payload.responses.concretes.ResponseMessage;
import sehmus.school_management_system.payload.responses.concretes.StudentInfoResponse;
import sehmus.school_management_system.services.business.StudentInfoService;

@RestController
@RequestMapping("/studentInfo")
@RequiredArgsConstructor
public class StudentInfoController {

    private final StudentInfoService studentInfoService;


    @PreAuthorize("hasAnyAuthority('Teacher')")
    @PostMapping("/save")
    public ResponseMessage<StudentInfoResponse> saveStudentInfo(
            HttpServletRequest httpServletRequest,
            @RequestBody @Valid StudentInfoRequest studentInfoRequest){

        return studentInfoService.saveStudentInfo(httpServletRequest, studentInfoRequest);

    }
    @PreAuthorize("hasAnyAuthority('Admin', 'Teacher')")
    @PutMapping("/update/{studentInfoId}")
    public ResponseMessage<StudentInfoResponse> updateStudentInfo(@Valid @RequestBody StudentInfoUpdateRequest studentInfoUpdateRequest,
                                                                  @PathVariable Long studentInfoId){

        return studentInfoService.updateStudentInfo(studentInfoUpdateRequest, studentInfoId);

    }

    @PreAuthorize("hasAnyAuthority('Admin', 'Teacher')")
    @DeleteMapping("/delete/{id}")
    public ResponseMessage delete(@PathVariable Long id){

        return studentInfoService.deleteStudentInfo(id);

    }

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @GetMapping("/findStudentInfoByPage")
    public Page<StudentInfoResponse> findStudentInfoByPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "absentee") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ){

        return studentInfoService.findStudentInfoByPage(page, size, sort, type);

    }

    @PreAuthorize("hasAnyAuthority('Admin', 'Teacher')")
    @GetMapping("/getAll/{id}")
    public ResponseEntity<StudentInfoResponse> getStudentInfoById (@PathVariable Long id){

        return ResponseEntity.ok(studentInfoService.findStudentInfoById(id));

    }




}
