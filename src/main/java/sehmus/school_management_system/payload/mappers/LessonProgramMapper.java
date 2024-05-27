package sehmus.school_management_system.payload.mappers;

import lombok.Data;
import org.springframework.stereotype.Component;
import sehmus.school_management_system.models.concretes.EducationTerm;
import sehmus.school_management_system.models.concretes.Lesson;
import sehmus.school_management_system.models.concretes.LessonProgram;
import sehmus.school_management_system.payload.requests.concretes.LessonProgramRequest;
import sehmus.school_management_system.payload.responses.concretes.LessonProgramResponse;

import java.util.Set;

@Data
@Component
public class LessonProgramMapper {

    public LessonProgram mapLessonProgramRequestToLessonProgram(LessonProgramRequest lessonProgramRequest,
                                                                Set<Lesson> lessonSet, EducationTerm educationTerm){
        return LessonProgram.builder()
                .startTime(lessonProgramRequest.getStartTime())
                .stopTime(lessonProgramRequest.getStopTime())
                .day(lessonProgramRequest.getDay())
                .lessons(lessonSet)
                .educationTerm(educationTerm)
                .build();
    }

    public LessonProgramResponse mapLessonProgramToLessonProgramResponse(LessonProgram lessonProgram){
        //TODO how to return teacher and students
        return LessonProgramResponse.builder()
                .day(lessonProgram.getDay())
                .educationTerm(lessonProgram.getEducationTerm())
                .startTime(lessonProgram.getStartTime())
                .stopTime(lessonProgram.getStopTime())
                .lessonProgramId(lessonProgram.getId())
                .lessonName(lessonProgram.getLessons())
//        .teachers(lessonProgram.getUsers().stream().filter(x->x.getUserRole().getRoleType().getName().equals("Teacher")).collect(
//            Collectors.toSet()))
                .build();
    }

}
