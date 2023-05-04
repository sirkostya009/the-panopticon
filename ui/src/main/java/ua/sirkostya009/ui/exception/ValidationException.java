package ua.sirkostya009.ui.exception;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {
    private final Object object;

    public ValidationException(String message, Object o) {
        super(message);
        object = o;
    }
}
