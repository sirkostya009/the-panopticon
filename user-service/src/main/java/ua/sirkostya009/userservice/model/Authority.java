package ua.sirkostya009.userservice.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum Authority implements GrantedAuthority {
    VIEW_BOOKS,
    ADD_BOOKS,
    REMOVE_BOOKS,
    GIVE_BOOKS,
    MODIFY_BOOKS,
    ADD_USERS,
    DISABLE_USERS;

    public interface Presets {
        Set<Authority> USER = Set.of(Authority.VIEW_BOOKS);
        Set<Authority> SUPER_USER = Arrays.stream(Authority.values()).collect(Collectors.toSet());
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
