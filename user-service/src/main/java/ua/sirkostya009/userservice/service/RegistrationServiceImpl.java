package ua.sirkostya009.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.sirkostya009.userservice.dto.MailJob;
import ua.sirkostya009.userservice.dto.UserPasswordDto;
import ua.sirkostya009.userservice.exception.AuthException;
import ua.sirkostya009.userservice.exception.NotFoundException;
import ua.sirkostya009.userservice.model.Authority;
import ua.sirkostya009.userservice.model.Token;
import ua.sirkostya009.userservice.model.User;
import ua.sirkostya009.userservice.repository.TokenRepository;
import ua.sirkostya009.userservice.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final KafkaTemplate<String, MailJob> template;
    private final PasswordEncoder encoder;

    @Value("${kafka.topics.mail-topic}")
    private String mailTopic;

    @Override
    public void register(UserPasswordDto request) {
        if (userRepository.findByUsername(request.username()).isPresent())
            throw new AuthException("username already taken");

        var user = userRepository.save(User.builder()
                .email(request.email())
                .username(request.username())
                .password(encoder.encode(request.password()))
                .firstName(request.firstName())
                .lastName(request.lastName())
                .authorities(Authority.Presets.USER)
                .build());

        var token = tokenRepository.save(Token.builder()
                .issuedAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(1))
                .user(user)
                .build());

        template.send(mailTopic, new MailJob(user.getEmail(), token.getId()));
    }

    @Override
    @Transactional
    public void confirm(String uuid) {
        var token = tokenRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("token " + uuid + " not found"));

        token.getUser().setEnabled(true);

        tokenRepository.delete(token);
    }
}
