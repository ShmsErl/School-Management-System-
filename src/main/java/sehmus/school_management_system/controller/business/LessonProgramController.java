package sehmus.school_management_system.controller.business;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sehmus.school_management_system.services.business.LessonProgramService;

@RestController
@RequestMapping("/lessonProgram")
@RequiredArgsConstructor
public class LessonProgramController {

    private final LessonProgramService lessonProgramService;
}
