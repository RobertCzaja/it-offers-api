package pl.api.itoffers.security.application.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import pl.api.itoffers.security.domain.User;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class JwtService {
    @Value("${jwt.token.secret}")
    private String secret;

    public JwtService(){}

    public String createToken(User user) {
        Date tokenCreateTime = new Date();
        Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(60*60*1000));
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("firstName",user.getFirstName())
                .claim("lastName",user.getLastName())
                .setExpiration(tokenValidity)
                .signWith(SignatureAlgorithm.HS256, JwtService.getSignInKey(secret))
                .compact();
    }

    private static Key getSignInKey(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims parseJwtClaims(String token) {

        JwtParser jwtLocalParser = Jwts.parser().setSigningKey(secret).build();
        return jwtLocalParser.parseClaimsJws(token).getBody();
    }

    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }

    public String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");

        return (bearerToken != null && bearerToken.startsWith("Bearer "))
            ? bearerToken.substring("Bearer ".length())
            : null;
    }

    public boolean validateClaims(Claims claims) throws AuthenticationException {
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            throw e;
        }
    }

    public String getEmail(Claims claims) {
        return claims.getSubject();
    }

    private List<String> getRoles(Claims claims) {
        return (List<String>) claims.get("roles");
    }
}
