package ua.sirkostya009.userservice.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ua.sirkostya009.userservice.dto.UserDto;
import ua.sirkostya009.userservice.dto.UserPasswordDto;
import ua.sirkostya009.userservice.service.UserService;

@RestController
@EnableMethodSecurity
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    @GetMapping("/self")
    public UserDto self(JwtAuthenticationToken token) {
        return UserDto.of(service.findByUsername(token.getName()));
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('SCOPE_ADD_USERS')")
    public UserDto add(@RequestBody @Valid UserPasswordDto dto) {
        return UserDto.of(service.save(dto));
    }

    @GetMapping("/disable/{id}")
    @PreAuthorize("hasAuthority('SCOPE_DISABLE_USERS')")
    public void disable(@PathVariable String id) {
        service.disable(id);
    }

    @GetMapping("/enable/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ENABLE_USERS')")
    public void enable(@PathVariable String id) {
        service.enable(id);
    }
}
