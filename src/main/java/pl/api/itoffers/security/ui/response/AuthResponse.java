package pl.api.itoffers.security.ui.response;

public class AuthResponse {
    private String token = "some token";

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
