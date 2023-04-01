package ua.sirkostya009.userservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ua.sirkostya009.userservice.constraint.EmailAddress;
import ua.sirkostya009.userservice.model.User;

public record UserPasswordDto(
        @NotNull
        @EmailAddress
        String email,
        @NotNull
        @NotBlank
        @Min(3)
        @Max(15)
        String username,
        @NotNull
        @NotBlank
        @Min(8)
        @Max(255)
        String password,
        @NotNull
        @NotBlank
        @Max(255)
        String firstName,
        @NotBlank
        @Max(255)
        String lastName
) {
    public static UserPasswordDto of(User user) {
        return new UserPasswordDto(
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName()
        );
    }
}
