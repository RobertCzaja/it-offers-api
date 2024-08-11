package pl.api.itoffers.shared.http.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends RuntimeException {

    private int httpStatusCode;

    public ValidationException(int httpStatusCode, String message) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }

    public static final ValidationException ofInvalidProperty(String propertyName)
    {
        return new ValidationException(HttpStatus.BAD_REQUEST.value(), "Invalid "+propertyName);
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}
