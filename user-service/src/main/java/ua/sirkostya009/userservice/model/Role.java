package ua.sirkostya009.userservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER(Authority.Presets.USER),
    SUPER_USER(Authority.Presets.SUPER_USER);

    private final Set<Authority> authorities;
}
