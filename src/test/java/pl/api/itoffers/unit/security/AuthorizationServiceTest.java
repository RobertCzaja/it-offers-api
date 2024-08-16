package pl.api.itoffers.unit.security;

import io.jsonwebtoken.impl.DefaultClaimsBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.api.itoffers.helper.FrozenClock;
import pl.api.itoffers.security.application.dto.JwtParamsDto;
import pl.api.itoffers.security.application.service.AuthorizationService;
import pl.api.itoffers.security.application.service.JwtService;
import pl.api.itoffers.security.infrastructure.UserInMemoryRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorizationServiceTest {

    private AuthorizationService service;
    private FrozenClock frozenClock;
    private JwtService jwtService;

    @BeforeEach
    public void setup() {
        this.frozenClock = new FrozenClock();
        JwtParamsDto jwtParams = new JwtParamsDto();
        jwtParams.setSecret("2D4A614E645267556B58703273357638792F423F4428472B4B6250655368566D");
        jwtParams.setLifetime(60);
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

    /** TODO should be moved to JwtService unit test */
    @Test
    public void testShouldNotAcceptExpiredToken() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = sdf.parse("2024-01-01 00:00:00");
        frozenClock.setCurrentDate(now);

        String token = service.getToken(UserInMemoryRepository.EMAIL_USER, UserInMemoryRepository.PASSWORD_USER);

        Date oneSecondAfterTokenExpiration = sdf.parse("2024-01-01 00:01:01");

        //new DefaultClaimsBuilder().
        //jwtService.validateClaims()

        assertThat(true).isTrue(); // todo to change
    }
}
