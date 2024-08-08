package pl.api.itoffers.shared.http.exception;

public class ErrorResponse {
    private final String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public static final ErrorResponse create()
    {
        return new ErrorResponse("Bad payload request!");
    }

    public String getMessage() {
        return message;
    }
}
