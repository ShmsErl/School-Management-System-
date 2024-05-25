package sehmus.school_management_system.models.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sehmus.school_management_system.models.enums.Note;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer absentee;

    private Double midtermExam;

    private Double finalExam;

    private Double examAverage;

    private String infoNote;

    @Enumerated(EnumType.STRING)
    private Note letterGrade;

    @JsonIgnore
    @ManyToOne
    private User teacher;

    @JsonIgnore
    @ManyToOne
    private User student;

    @ManyToOne
    private Lesson lesson;

    @OneToOne
    private EducationTerm educationTerm;


}

