package pl.api.itoffers.security.framework;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.api.itoffers.security.application.service.JwtService;
import pl.api.itoffers.shared.http.exception.HttpExceptionHandler;

import java.io.IOException;
import java.util.ArrayList;

@Component
@Order(1)
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final HttpExceptionHandler exceptionHandler;

    public JwtAuthorizationFilter(JwtService jwtService, HttpExceptionHandler exceptionHandler) {
        this.jwtService = jwtService;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String accessToken = jwtService.extractToken(request);

            if (null == accessToken) {
                filterChain.doFilter(request, response);
                return;
            }

            Claims claims = jwtService.resolveClaims(request, accessToken);

            if (null != claims & jwtService.validateClaims(claims)) {
                String email = claims.getSubject();
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(email, "", new ArrayList<>())
                );
            }
        } catch (Exception e) {
            exceptionHandler.handle(e, response);
        }
        filterChain.doFilter(request, response);
    }
}
