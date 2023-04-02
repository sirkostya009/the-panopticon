package ua.sirkostya009.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import ua.sirkostya009.userservice.dto.AuthenticationRequest;
import ua.sirkostya009.userservice.exception.AuthException;
import ua.sirkostya009.userservice.repository.UserRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    @Override
    public String authenticate(AuthenticationRequest request) {
        var user = repository.findByUsernameOrEmail(request.login(), request.login())
                .orElseThrow(() -> new AuthException("user with login " + request.login() + " could not be found"));

        if (!passwordEncoder.matches(request.password(), user.getPassword()))
            throw new AuthException("password is incorrect");

        if (!user.isEnabled())
            throw new AuthException("user is disabled");

        return jwtEncoder.encode(
                JwtEncoderParameters.from(
                        JwtClaimsSet.builder()
                                .issuedAt(Instant.now())
                                .subject(user.getUsername())
                                .issuer("auth")
                                .audience(List.of(
                                        "/auth/",
                                        "/library/"
                                ))
                                .claim("scp", user.getAuthorities())
                                .claim("uid", user.getId())
                                .expiresAt(Instant.now().plus(7, ChronoUnit.DAYS))
                                .build()
                )
        ).getTokenValue();
    }
}
