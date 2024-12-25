package pl.api.itoffers.shared.http.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends RuntimeException {

    private final HttpStatus httpStatus;

    public ValidationException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public static final ValidationException ofInvalidProperty(String propertyName)
    {
        return new ValidationException(HttpStatus.BAD_REQUEST, "Invalid "+propertyName);
    }

    public static final ValidationException becauseOf(String message)
    {
        return new ValidationException(HttpStatus.BAD_REQUEST, message);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
