package ua.sirkostya009.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthenticationRequest(
        @NotBlank
        @NotNull
        String login,
        @NotBlank
        @NotNull
        String password
) {
}
