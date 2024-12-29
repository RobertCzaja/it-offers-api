package pl.api.itoffers.security.application.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import pl.api.itoffers.security.application.dto.JwtParamsDto;
import pl.api.itoffers.security.domain.User;
import pl.api.itoffers.shared.utils.clock.ClockInterface;

@Component
public class JwtService {

  private static final String AUTHORITIES_KEY = "roles";

  private final JwtParamsDto jwtParamsDto;
  private final ClockInterface clock;

  public JwtService(JwtParamsDto jwtParamsDto, ClockInterface clock) {
    this.jwtParamsDto = jwtParamsDto;
    this.clock = clock;
  }

  public String createToken(User user) {
    Date tokenValidity =
        new Date(
            clock.currentDate().getTime() + TimeUnit.SECONDS.toMillis(jwtParamsDto.getLifetime()));
    return Jwts.builder()
        .subject(user.getEmail())
        .claim("firstName", user.getFirstName())
        .claim("lastName", user.getLastName())
        .claim(AUTHORITIES_KEY, user.getRoles())
        .setExpiration(tokenValidity)
        .signWith(SignatureAlgorithm.HS256, JwtService.getSignInKey(jwtParamsDto.getSecret()))
        .compact();
  }

  private static Key getSignInKey(String secret) {
    byte[] keyBytes = Decoders.BASE64.decode(secret);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public Claims parseJwtClaims(String token) {
    return Jwts.parser()
        .setSigningKey(JwtService.getSignInKey(jwtParamsDto.getSecret()))
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public String extractToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    return (bearerToken != null && bearerToken.startsWith("Bearer "))
        ? bearerToken.substring(7)
        : null;
  }

  public Claims resolveClaims(HttpServletRequest req, String token) {
    try {
      return parseJwtClaims(token);
    } catch (ExpiredJwtException ex) {
      req.setAttribute("expired", ex.getMessage());
      throw ex;
    } catch (Exception ex) {
      req.setAttribute("invalid", ex.getMessage());
      throw ex;
    }
  }

  public boolean validateClaims(Claims claims) throws AuthenticationException {
    return claims.getExpiration().after(clock.currentDate());
  }

  public List<String> getAuthorities(Claims claims) {
    return (List<String>) claims.get(AUTHORITIES_KEY);
  }
}
