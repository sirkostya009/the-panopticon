package ua.sirkostya009.mailer.dto;

public record ConfirmationMailDto(
        String email,
        String token
) {
}
