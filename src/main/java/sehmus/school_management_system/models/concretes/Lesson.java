package sehmus.school_management_system.models.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lessonName;

    private Integer creditScore;

    private Boolean isCompulsory;

    @JsonIgnore
    @ManyToMany(mappedBy = "lessons", cascade = CascadeType.REMOVE)
    private Set<LessonProgram> lessonPrograms;

}
