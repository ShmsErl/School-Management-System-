package sehmus.school_management_system.payload.mappers;

import lombok.Data;
import org.springframework.stereotype.Component;
import sehmus.school_management_system.models.concretes.Lesson;
import sehmus.school_management_system.payload.requests.concretes.LessonRequest;
import sehmus.school_management_system.payload.responses.concretes.LessonResponse;

@Data
@Component
public class LessonMapper {

    public Lesson mapLessonRequestToLesson(LessonRequest lessonRequest){
        return Lesson.builder()
                .lessonName(lessonRequest.getLessonName())
                .creditScore(lessonRequest.getCreditScore())
                .isCompulsory(lessonRequest.getIsCompulsory())
                .build();
    }

    public LessonResponse mapLessonToLessonResponse(Lesson lesson){
        return LessonResponse.builder()
                .lessonId(lesson.getId())
                .lessonName(lesson.getLessonName())
                .creditScore(lesson.getCreditScore())
                .isCompulsory(lesson.getIsCompulsory())
                .build();
    }

}