package sehmus.school_management_system.models.concretes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import sehmus.school_management_system.models.enums.Gender;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "t_users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String ssn;

    private String name;

    private String surname;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDay;

    private String birthPlace;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(unique = true)
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    private Boolean builtIn;

    private String motherName;

    private String fatherName;

    private int studentNumber;

    private boolean isActive;

    private Boolean isAdvisor;

    private Long advisorTeacherId;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne()
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserRole userRole;

    @ManyToMany
    @JoinTable(name = "user_lessonProgram",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_program_id"))
    private Set<LessonProgram> lessonProgramList;
}
