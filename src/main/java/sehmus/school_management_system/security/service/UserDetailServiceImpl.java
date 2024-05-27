package sehmus.school_management_system.security.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sehmus.school_management_system.models.concretes.User;
import sehmus.school_management_system.services.helper.MethodHelper;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final MethodHelper methodHelper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = methodHelper.loadUserByName(username);

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getIsAdvisor(),
                user.getPassword(),
                user.getSsn(),
                user.getUserRole().getRoleType().getName());
    }
}