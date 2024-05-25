package sehmus.school_management_system.payload.requests.concretes;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import sehmus.school_management_system.payload.requests.abstracts.BaseUserRequest;

import java.util.Set;

@Getter
@Setter
@SuperBuilder
@RequiredArgsConstructor
public class TeacherRequest extends BaseUserRequest {

    @NotNull(message = "Please select Lesson Program")
    private Set<Long> lessonProgramIdList;

    @NotNull(message = "Please select isAdvisor Teacher")
    private Boolean isAdvisorTeacher;

}
