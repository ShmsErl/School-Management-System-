package sehmus.school_management_system.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sehmus.school_management_system.payload.requests.authentication.LoginRequest;
import sehmus.school_management_system.payload.responses.authentication.AuthResponse;
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
}
