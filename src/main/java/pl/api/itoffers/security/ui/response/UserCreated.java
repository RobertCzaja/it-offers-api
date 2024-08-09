package pl.api.itoffers.security.ui.response;

import lombok.Data;

@Data
public class UserCreated {
    private final Long userId;
    private final String message = "User created";

    public UserCreated(Long userId) {
        this.userId = userId;
    }
}
