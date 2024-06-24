package sehmus.school_management_system.services.helper;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import sehmus.school_management_system.exception.BadRequestException;
import sehmus.school_management_system.exception.ConflictException;
import sehmus.school_management_system.exception.ResourceNotFoundException;
import sehmus.school_management_system.models.concretes.User;
import sehmus.school_management_system.models.enums.RoleType;
import sehmus.school_management_system.payload.messages.ErrorMessages;
import sehmus.school_management_system.repositories.UserRepository;

import java.util.List;

@Component
@AllArgsConstructor
public class MethodHelper {

    private final UserRepository userRepository;

    public User isUserExist(Long userId){
        return userRepository.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, userId))
        );
    }

    public void checkBuiltIn(User user){

        if (user.getBuiltIn()) throw new BadRequestException(String.format(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE));


    }

    public User loadUserByName(String username){

        User user = userRepository.findByUsername(username);

        if (user == null){

            throw new UsernameNotFoundException(
                    String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE_USERNAME, username));
        }

        return user;

    }

    public void checkIsAdvisor(User user){

        if(!user.getIsAdvisor()){

            throw new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_ADVISOR_MESSAGE,user.getId()));

        }
    }

    public void checkRole(User user, RoleType roleType){

        if(!user.getUserRole().getRoleType().equals(roleType)){

            throw new ConflictException(ErrorMessages.NOT_HAVE_EXPECTED_ROLE_USER);

        }

    }

    public List<User> getUserList(List<Long> idList){

        return userRepository.findByIdList(idList);

    }


}