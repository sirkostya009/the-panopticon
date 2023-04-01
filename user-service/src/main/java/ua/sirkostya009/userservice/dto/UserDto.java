package ua.sirkostya009.userservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ua.sirkostya009.userservice.constraint.EmailAddress;
import ua.sirkostya009.userservice.model.User;

public record UserDto(
        @NotNull
        String id,
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
        @Max(255)
        String firstName,
        @NotBlank
        @Max(255)
        String lastName
) {
        public static UserDto of(User user) {
                return new UserDto(
                        user.getId(),
                        user.getEmail(),
                        user.getUsername(),
                        user.getFirstName(),
                        user.getLastName()
                );
        }
}
