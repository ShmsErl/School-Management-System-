package sehmus.school_management_system.payload.responses.concretes;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import sehmus.school_management_system.models.concretes.LessonProgram;
import sehmus.school_management_system.payload.responses.abstracts.BaseUserResponse;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentResponse extends BaseUserResponse {

    private Set<LessonProgram> lessonProgramSet;

    private int studentNumber;

    private String motherName;

    private String fatherName;

    private boolean isActive;

}
