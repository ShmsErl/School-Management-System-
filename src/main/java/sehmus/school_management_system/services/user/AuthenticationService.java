package sehmus.school_management_system.services.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sehmus.school_management_system.payload.mappers.UserMapper;
import sehmus.school_management_system.payload.requests.authentication.LoginRequest;
import sehmus.school_management_system.payload.responses.authentication.AuthResponse;
import sehmus.school_management_system.repositories.UserRepository;
import sehmus.school_management_system.security.jwt.JwtUtils;
import sehmus.school_management_system.security.service.UserDetailsImpl;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final UserMapper userMapper;




    public AuthResponse authenticateUser(LoginRequest loginRequest) {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token =  jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Set<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        String userRole = roles.stream().findFirst().get();

        AuthResponse.AuthResponseBuilder responseBuilder = AuthResponse.builder();
        responseBuilder.username(userDetails.getUsername());
        responseBuilder.token(token);
        responseBuilder.name(userDetails.getName());
        responseBuilder.ssn(userDetails.getSsn());
        responseBuilder.role(userRole);

        return responseBuilder.build();


    }









}
