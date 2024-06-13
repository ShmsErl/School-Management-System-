package sehmus.school_management_system.controller.business;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sehmus.school_management_system.models.concretes.Lesson;
import sehmus.school_management_system.payload.requests.concretes.LessonRequest;
import sehmus.school_management_system.payload.responses.concretes.LessonResponse;
import sehmus.school_management_system.payload.responses.concretes.ResponseMessage;
import sehmus.school_management_system.services.business.LessonService;

import java.util.Set;

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

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @GetMapping("/getLessonByName")
    public ResponseMessage<LessonResponse> getLessonByLessonName(@RequestParam String lessonName){

        return lessonService.getLessonByLessonName(lessonName);

    }


    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @GetMapping("/findByPage")
    public Page<LessonResponse> getLessonByPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "lessonName") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ){

        return lessonService.findLessonByPage(page, size, sort, type);

    }

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @GetMapping("/idSet")
    public Set<Lesson> getAllLessonByIdSet(@RequestParam(name = "lessonId") Set<Long> idSet){

        return lessonService.getLessonByIdSet(idSet);

    }

}
