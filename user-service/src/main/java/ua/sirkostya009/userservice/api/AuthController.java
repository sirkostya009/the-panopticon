package ua.sirkostya009.userservice.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.sirkostya009.userservice.dto.AuthenticationRequest;
import ua.sirkostya009.userservice.dto.UserPasswordDto;
import ua.sirkostya009.userservice.service.AuthenticationService;
import ua.sirkostya009.userservice.service.RegistrationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final RegistrationService registrationService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public void register(@RequestBody @Valid UserPasswordDto request) {
        registrationService.register(request);
    }

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody @Valid AuthenticationRequest request) {
        return authenticationService.authenticate(request);
    }
}
