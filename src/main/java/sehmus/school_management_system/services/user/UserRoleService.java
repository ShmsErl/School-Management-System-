package sehmus.school_management_system.services.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sehmus.school_management_system.exception.ResourceNotFoundException;
import sehmus.school_management_system.models.concretes.UserRole;
import sehmus.school_management_system.models.enums.RoleType;
import sehmus.school_management_system.payload.messages.ErrorMessages;
import sehmus.school_management_system.repositories.UserRoleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRole getUserRole(RoleType roleType){
        return userRoleRepository.findByEnumRoleEquals(roleType)
                .orElseThrow(()-> new ResourceNotFoundException(ErrorMessages.ROLE_NOT_FOUND));

    }

    public List<UserRole> getAllUserRole(){
        return userRoleRepository.findAll();

    }
}
