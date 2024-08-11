package pl.api.itoffers.security.domain.exception;

public class CouldNotCreateUser extends RuntimeException {
    private CouldNotCreateUser(String message) {
        super(message);
    }
    public static CouldNotCreateUser becauseEmailIsAlreadyRegistered(String email)
    {
        return new CouldNotCreateUser(String.format("Email %s is already registered", email));
    }
}
