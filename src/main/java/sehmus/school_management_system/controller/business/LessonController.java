package sehmus.school_management_system.controller.business;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sehmus.school_management_system.payload.requests.concretes.LessonRequest;
import sehmus.school_management_system.payload.responses.concretes.LessonResponse;
import sehmus.school_management_system.payload.responses.concretes.ResponseMessage;
import sehmus.school_management_system.services.business.LessonService;

@RestController
@RequestMapping("/lesson")
@RequiredArgsConstructor
public class LessonController {


    private final LessonService lessonService;

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @PostMapping("/save")
    public ResponseMessage<LessonResponse> saveLesson(@Valid @RequestBody LessonRequest lessonRequest){

        return lessonService.saveLesson(lessonRequest);

    }

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @DeleteMapping("/delete/{id}")
    public ResponseMessage deleteLesson(@PathVariable Long id){

        return lessonService.deleteById(id);

    }
}
