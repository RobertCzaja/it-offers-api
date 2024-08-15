package pl.api.itoffers.unit.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.api.itoffers.security.application.dto.JwtParamsDto;
import pl.api.itoffers.security.application.service.AuthorizationService;
import pl.api.itoffers.security.application.service.JwtService;
import pl.api.itoffers.security.infrastructure.UserInMemoryRepository;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorizationServiceTest {

    private AuthorizationService service;

    @BeforeEach
    public void setup() {
        JwtParamsDto jwtParams = new JwtParamsDto();
        jwtParams.setSecret("2D4A614E645267556B58703273357638792F423F4428472B4B6250655368566D");
        jwtParams.setLifetime(3600);

        this.service = new AuthorizationService(
                new UserInMemoryRepository(),
                new JwtService(jwtParams),
                new BCryptPasswordEncoder()
        );
    }

    @Test
    public void testShouldGenerateTokenForUserThatExists() {

        String token = service.getToken(UserInMemoryRepository.EMAIL_USER, UserInMemoryRepository.PASSWORD_USER);
        assertThat(token).isNotNull();
    }

    @Test
    public void testCannotReturnTokenWhenPasswordIncorrect() {

        String token = service.getToken(UserInMemoryRepository.EMAIL_USER, "wrongPassword");
        assertThat(token).isNull();
    }

    // TODO add test which check token expiration
}
