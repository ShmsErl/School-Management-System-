package sehmus.school_management_system.payload.responses.concretes;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sehmus.school_management_system.models.enums.Note;
import sehmus.school_management_system.models.enums.Term;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentInfoResponse {

    private Long id;

    private Double midtermExam;

    private Double finalExam;

    private Integer absentee;

    private String infoNote;

    private String lessonName;

    private int creditScore;

    private boolean isCompulsory;

    private Term educationTerm;

    private Double average;

    private Note note;

    private StudentResponse studentResponse;

}
