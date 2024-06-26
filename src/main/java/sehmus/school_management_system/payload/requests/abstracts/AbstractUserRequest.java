package sehmus.school_management_system.payload.requests.abstracts;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sehmus.school_management_system.models.enums.Gender;

import java.time.LocalDate;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class AbstractUserRequest {
    @NotNull(message = "Please enter your username")
    @Size(min = 4, max = 16,message = "Your username should be at least 4 characters.")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your username must consist of the characters.")
    private String username;

    @NotNull(message = "Please enter your name")
    @Size(min = 4, max = 16,message = "Your name should be at least 4 characters.")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your name must consist of the characters.")
    private String name;

    @NotNull(message = "Please enter your surname")
    @Size(min = 4, max = 16,message = "Your surname should be at least 4 characters.")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your surname must consist of the characters.")
    private String surname;

    @NotNull(message = "Please enter your birthday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past(message = "Your birthday can not be in the future")
    private LocalDate birthDay;

    @NotNull
    @Pattern(regexp = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$",
            message = "Please enter valid SSN number")
    private String ssn;

    @NotNull(message = "Please enter your birthplace")
    @Size(min = 2, max = 16,message = "Your birthplace should be at least 2 characters.")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your birthplace must consist of the characters.")
    private String birthPlace;


    @NotNull(message = "Please enter your phone number")
    @Size(min = 12, max = 12,message = "Your phone number should be 12 characters long")
    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
            message = "Please enter valid phone number")
    private String phoneNumber;

    @NotNull(message = "Please enter your gender")
    private Gender gender;

    @NotNull(message = "Please enter your email")
    @Email(message = "Please enter valid email")
    @Size(min=5, max=50 , message = "Your email should be between 5 and 50 chars")
    private String email;


}