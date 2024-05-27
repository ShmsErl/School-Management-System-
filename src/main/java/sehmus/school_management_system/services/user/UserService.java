package sehmus.school_management_system.services.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sehmus.school_management_system.exception.ResourceNotFoundException;
import sehmus.school_management_system.models.concretes.User;
import sehmus.school_management_system.models.enums.RoleType;
import sehmus.school_management_system.payload.mappers.UserMapper;
import sehmus.school_management_system.payload.messages.ErrorMessages;
import sehmus.school_management_system.payload.messages.SuccessMessages;
import sehmus.school_management_system.payload.requests.concretes.UserRequest;
import sehmus.school_management_system.payload.responses.concretes.ResponseMessage;
import sehmus.school_management_system.payload.responses.concretes.UserResponse;
import sehmus.school_management_system.repositories.UserRepository;
import sehmus.school_management_system.services.helper.MethodHelper;
import sehmus.school_management_system.services.helper.PageableHelper;
import sehmus.school_management_system.services.validator.UniquePropertyValidator;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final UserMapper userMapper;
    private final UserRoleService userRoleService;
    private final PageableHelper pageableHelper;
    private final MethodHelper methodHelper;


    public ResponseMessage<UserResponse> saveUser(UserRequest userRequest, String userRole) {
        uniquePropertyValidator.checkDuplicate(userRequest.getUsername(),
                userRequest.getSsn(),
                userRequest.getPhoneNumber(),
                userRequest.getEmail());

        User user = userMapper.mapUserRequestToUser(userRequest);

        if (userRole.equalsIgnoreCase(RoleType.ADMIN.getName())) {

            if (Objects.equals(userRequest.getUsername(), "Admin")) {
                user.setBuiltIn(true);
            }

            user.setUserRole(userRoleService.getUserRole(RoleType.ADMIN));

        } else if (userRole.equalsIgnoreCase("Dean")) {

            user.setUserRole(userRoleService.getUserRole(RoleType.MANAGER));

        } else if (userRole.equalsIgnoreCase("ViceDean")) {

            user.setUserRole(userRoleService.getUserRole(RoleType.ASSISTANT_MANAGER));

        } else
            throw new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_USER_ROLE_MESSAGE, userRole));

        User savedUser = userRepository.save(user);

        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_CREATE)
                .returnBody(userMapper.mapUserToUserResponse(savedUser))
                .build();

    }

    public Page<UserResponse> getUsersByPage(int page, int size, String sort, String type, String userRole) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        return userRepository.findByUserByRole(userRole, pageable).map(userMapper::mapUserToUserResponse);

    }


}
