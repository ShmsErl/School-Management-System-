package sehmus.school_management_system.payload.requests.concretes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import sehmus.school_management_system.payload.requests.abstracts.AbstractUserRequest;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class UserRequestWithoutPassword extends AbstractUserRequest {



}