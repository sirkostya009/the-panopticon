package ua.sirkostya009.ui.model;

import ua.sirkostya009.ui.exception.ValidationException;

import java.util.regex.Pattern;

public record RegistrationRequest(
        String firstName,
        String lastName,
        String username,
        String email,
        String password,
        String confirmPassword
) {
    public void validate() {
        validateFirstName();
        validateLastName();
        validateUsername();
        validateEmail();
        validatePassword();
    }

    private void validateFirstName() {
        if (firstName == null || firstName.isBlank())
            raise("First name must not be empty");

        if (firstName.length() > 255)
            raise("First name exceeds a character limit of 255");
    }

    private void validateLastName() {
        if (lastName != null) {
            if (lastName.isBlank())
                raise("Last name must not be blank");

            if (lastName.length() > 255)
                raise("Last name exceeds a character limit of 255");
        }
    }

    private void validateUsername() {
        if (username == null || username.isBlank())
            raise("Username must not be blank");

        if (username.length() < 3)
            raise("Username is too short");

        if (username.length() > 15)
            raise("Username is too long");
    }

    private void validateEmail() {
        if (email == null || email.isBlank())
            raise("Email cannot be null");

        if (!Pattern.compile("^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$").matcher(email).matches())
            raise("Email incorrect");
    }

    private void validatePassword() {
        if (password == null || password.isBlank())
            raise("Password cannot be blank");

        if (password.length() < 8)
            raise("Password is too short");

        if (password.length() > 255)
            raise("Password is too long");

        if (!password.equals(confirmPassword))
            raise("Provided passwords dont match");
    }

    private void raise(String message) {
        throw new ValidationException(message, this);
    }
}
