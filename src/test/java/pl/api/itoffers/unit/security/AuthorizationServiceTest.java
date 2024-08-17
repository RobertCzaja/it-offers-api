package pl.api.itoffers.unit.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.api.itoffers.helper.FrozenClock;
import pl.api.itoffers.security.application.dto.JwtParamsDto;
import pl.api.itoffers.security.application.service.AuthorizationService;
import pl.api.itoffers.security.application.service.JwtService;
import pl.api.itoffers.security.infrastructure.UserInMemoryRepository;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AuthorizationServiceTest {

    private static final Integer TOKEN_EXPIRATION_IN_SECONDS = 60;

    private AuthorizationService service;
    private FrozenClock frozenClock;
    private JwtService jwtService;

    @BeforeEach
    public void setup() {
        this.frozenClock = new FrozenClock();
        JwtParamsDto jwtParams = new JwtParamsDto();
        jwtParams.setSecret("2D4A614E645267556B58703273357638792F423F4428472B4B6250655368566D");
        jwtParams.setLifetime(TOKEN_EXPIRATION_IN_SECONDS);
        this.jwtService = new JwtService(jwtParams, this.frozenClock);

        this.service = new AuthorizationService(
                new UserInMemoryRepository(),
                this.jwtService,
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

    @Test()
    public void testShouldNotAcceptExpiredToken() throws ParseException {
        frozenClock.setCurrentDate(nowChangedBySeconds(-TOKEN_EXPIRATION_IN_SECONDS));

        String token = service.getToken(UserInMemoryRepository.EMAIL_USER, UserInMemoryRepository.PASSWORD_USER);

        assertThrows(ExpiredJwtException.class, () -> jwtService.parseJwtClaims(token));
    }

    private static Date nowChangedBySeconds(Integer seconds)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }
}
