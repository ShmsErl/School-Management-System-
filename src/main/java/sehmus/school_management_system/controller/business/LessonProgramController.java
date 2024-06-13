package sehmus.school_management_system.controller.business;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sehmus.school_management_system.payload.requests.concretes.LessonProgramRequest;
import sehmus.school_management_system.payload.responses.concretes.LessonProgramResponse;
import sehmus.school_management_system.payload.responses.concretes.ResponseMessage;
import sehmus.school_management_system.services.business.LessonProgramService;

import java.util.List;

@RestController
@RequestMapping("/lessonProgram")
@RequiredArgsConstructor
public class LessonProgramController {

    private final LessonProgramService lessonProgramService;


    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @PostMapping("/save")
    public ResponseMessage<LessonProgramResponse> saveLessonProgram(@Valid @RequestBody LessonProgramRequest lessonProgramRequest){

        return lessonProgramService.saveLessonProgram(lessonProgramRequest);

    }

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher','Student')")
    @GetMapping("/getAll")
    public List<LessonProgramResponse> getAllLessonProgram(){

        return lessonProgramService.getAll();

    }


    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher','Student')")
    @GetMapping("/getAllUnassigned")
    public List<LessonProgramResponse> getAllUnassigned(){

        return lessonProgramService.getAllUnassigned();

    }

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher','Student')")
    @GetMapping("/getAllAssigned")
    public List<LessonProgramResponse> getAllAssigned(){

        return lessonProgramService.getAllAssigned();

    }
}
