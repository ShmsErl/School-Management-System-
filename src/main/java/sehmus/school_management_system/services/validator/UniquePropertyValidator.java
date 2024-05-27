package sehmus.school_management_system.services.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sehmus.school_management_system.exception.ConflictException;
import sehmus.school_management_system.models.concretes.User;
import sehmus.school_management_system.payload.messages.ErrorMessages;
import sehmus.school_management_system.payload.requests.abstracts.AbstractUserRequest;
import sehmus.school_management_system.repositories.UserRepository;

@Component
@RequiredArgsConstructor
public class UniquePropertyValidator {

    private final UserRepository userRepository;

    public void checkUniqueProperties(User user, AbstractUserRequest userRequest){

        String updatedUsername = "";
        String updatedSsn = "";
        String updatedEmail = "";
        String updatedPhone = "";
        boolean isChanged = false;

        if (!user.getUsername().equalsIgnoreCase(userRequest.getUsername())){

            updatedUsername = userRequest.getUsername();
            isChanged = true;

        }

        if (!user.getSsn().equalsIgnoreCase(userRequest.getSsn())){

            updatedSsn = userRequest.getSsn();
            isChanged = true;

        }

        if (!user.getEmail().equalsIgnoreCase(userRequest.getEmail())){

            updatedEmail = userRequest.getEmail();
            isChanged = true;

        }

        if (!user.getPhoneNumber().equalsIgnoreCase(userRequest.getPhoneNumber())){

            updatedPhone = userRequest.getPhoneNumber();
            isChanged = true;

        }

        if (isChanged){

            checkDuplicate(updatedUsername, updatedSsn, updatedPhone, updatedEmail);

        }


    }

    public void checkDuplicate(String username, String ssn, String phoneNumber, String email){

        if (userRepository.existsByUsername(username)) throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_USERNAME, username));

        if (userRepository.existsBySsn(ssn)) throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_SSN, ssn));

        if (userRepository.existsByPhoneNumber(phoneNumber)) throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_PHONE_NUMBER, phoneNumber));

        if (userRepository.existsByEmail(email)) throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_EMAIL, email));

    }

}
