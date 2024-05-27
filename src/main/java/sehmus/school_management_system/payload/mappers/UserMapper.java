package sehmus.school_management_system.payload.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sehmus.school_management_system.models.concretes.User;
import sehmus.school_management_system.payload.requests.abstracts.BaseUserRequest;
import sehmus.school_management_system.payload.responses.concretes.UserResponse;

@Component
@RequiredArgsConstructor
public class UserMapper {


    private final PasswordEncoder passwordEncoder;

    public User mapUserRequestToUser(BaseUserRequest userRequest){
        return User.builder()
                .username(userRequest.getUsername())
                .name(userRequest.getName())
                .surname(userRequest.getSurname())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .ssn(userRequest.getSsn())
                .birthDay(userRequest.getBirthDay())
                .birthPlace(userRequest.getBirthPlace())
                .phoneNumber(userRequest.getPhoneNumber())
                .gender(userRequest.getGender())
                .email(userRequest.getEmail())
                .builtIn(userRequest.getBuiltIn())
                .isAdvisor(false)
                .build();

    }

    public UserResponse mapUserToUserResponse(User user){

        return UserResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender())
                .birthDay(user.getBirthDay())
                .birthPlace(user.getBirthPlace())
                .ssn(user.getSsn())
                .email(user.getEmail())
                .userRole(user.getUserRole().getRoleType().name)
                .build();

    }
}
