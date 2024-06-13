package sehmus.school_management_system.controller.business;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sehmus.school_management_system.payload.requests.concretes.LessonProgramRequest;
import sehmus.school_management_system.payload.responses.concretes.LessonProgramResponse;
import sehmus.school_management_system.payload.responses.concretes.ResponseMessage;
import sehmus.school_management_system.services.business.LessonProgramService;

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
}
