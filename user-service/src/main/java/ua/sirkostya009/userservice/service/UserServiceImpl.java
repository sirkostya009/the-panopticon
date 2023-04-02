package ua.sirkostya009.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.sirkostya009.userservice.dto.UserPasswordDto;
import ua.sirkostya009.userservice.exception.NotFoundException;
import ua.sirkostya009.userservice.model.Authority;
import ua.sirkostya009.userservice.model.User;
import ua.sirkostya009.userservice.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Override
    public User save(UserPasswordDto dto) {
        return repository.save(User.builder()
                .email(dto.email())
                .username(dto.username())
                .password(encoder.encode(dto.password()))
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .authorities(Authority.Presets.USER)
                .isEnabled(true)
                .build());
    }

    @Override
    public User findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("user with id " + id + " not found"));
    }

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("user " + username + " not found"));
    }

    @Override
    @Transactional
    public void disable(String id) {
        findById(id).setEnabled(false);
    }

    @Override
    public void enable(String id) {
        findById(id).setEnabled(true);
    }
}
