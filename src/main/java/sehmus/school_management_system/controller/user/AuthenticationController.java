package sehmus.school_management_system.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sehmus.school_management_system.payload.messages.SuccessMessages;
import sehmus.school_management_system.payload.requests.authentication.LoginRequest;
import sehmus.school_management_system.payload.requests.authentication.UpdatePasswordRequest;
import sehmus.school_management_system.payload.responses.authentication.AuthResponse;
import sehmus.school_management_system.payload.responses.concretes.UserResponse;
import sehmus.school_management_system.services.user.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser (@RequestBody @Valid LoginRequest loginRequest){

        return ResponseEntity.ok(authenticationService.authenticateUser(loginRequest));

    }

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher','Student')")
    @PatchMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest,
                                                 HttpServletRequest httpServletRequest){

        authenticationService.updatePassword(updatePasswordRequest, httpServletRequest);

        return ResponseEntity.ok(SuccessMessages.PASSWORD_CHANGED_RESPONSE_MESSAGE);

    }

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher','Student')")
    @GetMapping("/user")
    public ResponseEntity<UserResponse> findByUsername(HttpServletRequest httpServletRequest){

        return ResponseEntity.ok(authenticationService.findByUsername(httpServletRequest));

    }
}
