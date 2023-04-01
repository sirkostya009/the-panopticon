package ua.sirkostya009.ui.model;

public record User(
        String email,
        String username,
        String password,
        String firstName,
        String lastName
) {
}
