package sehmus.school_management_system.payload.requests.concretes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import sehmus.school_management_system.payload.requests.abstracts.BaseUserRequest;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class UserRequest extends BaseUserRequest {
}
