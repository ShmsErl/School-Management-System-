package sehmus.school_management_system.payload.responses.concretes;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sehmus.school_management_system.models.concretes.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeetingResponse {

    private Long id;

    private String description;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime stopTime;

    private Long advisorTeacherId;

    private String teacherName;

    private String teacherSsn;

    private String username;

    private List<User> students;

}
