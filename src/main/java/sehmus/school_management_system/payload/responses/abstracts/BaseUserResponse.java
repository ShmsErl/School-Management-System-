package sehmus.school_management_system.payload.responses.abstracts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import sehmus.school_management_system.models.enums.Gender;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BaseUserResponse {

    private Long userId;

    private String username;

    private String name;

    private String surname;

    private LocalDate birthDay;

    private String ssn;

    private String birthPlace;

    private String phoneNumber;

    private Gender gender;

    private String email;

    private String userRole;

}
