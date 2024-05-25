package sehmus.school_management_system.payload.requests.abstracts;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseUserRequest extends AbstractUserRequest{

    @NotNull(message = "Please enter your password!")
    @Size(min = 8, max = 60, message = "Your password should be at least 8 characters and maximum 60 characters.")
    private String password;

    private Boolean builtIn = false;

}