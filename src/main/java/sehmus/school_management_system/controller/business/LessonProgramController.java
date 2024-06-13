package sehmus.school_management_system.controller.business;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sehmus.school_management_system.payload.requests.concretes.LessonProgramRequest;
import sehmus.school_management_system.payload.responses.concretes.LessonProgramResponse;
import sehmus.school_management_system.payload.responses.concretes.ResponseMessage;
import sehmus.school_management_system.services.business.LessonProgramService;

import java.util.List;
import java.util.Set;

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

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @GetMapping("/getById/{id}")
    public LessonProgramResponse getById(@PathVariable Long id){

        return lessonProgramService.getLessonProgramById(id);

    }

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @DeleteMapping("/delete/{id}")
    public ResponseMessage deleteById(@PathVariable Long id){

        return lessonProgramService.deleteById(id);

    }

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher','Student')")
    @GetMapping("/getLessonProgramByPage")
    public Page<LessonProgramResponse> getLessonProgramByPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "day") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type){

        return lessonProgramService.getLessonProgramByPage(page,size,sort,type);

    }

    @PreAuthorize("hasAnyAuthority('Teacher')")
    @GetMapping("/getAllLessonProgramByTeacher")
    public Set<LessonProgramResponse> getAllLessonProgramByTeacherUsername(HttpServletRequest httpServletRequest){

        return lessonProgramService.getAllLessonProgramByTeacherUsername(httpServletRequest);

    }

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @GetMapping("/getAllLessonProgramByTeacherId/{teacherId}")
    public Set<LessonProgramResponse> getAllLessonProgramByTeacherId(@PathVariable Long teacherId){

        return lessonProgramService.getAllLessonProgramByTeacherId(teacherId);

    }


}
