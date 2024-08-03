package pl.api.itoffers.security.application.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.api.itoffers.security.application.service.JwtService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(1)
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final ObjectMapper mapper;

    public JwtAuthorizationFilter(JwtService jwtService, ObjectMapper mapper) {
        this.jwtService = jwtService;
        this.mapper = mapper;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // todo it should not be executed on /auth endpoint calling
        Map<String, Object> errorDetails = new HashMap<>();

        try {
            String accessToken = jwtService.resolveToken(request);

            if (null == accessToken) {
                filterChain.doFilter(request, response);
                return;
            }

            Claims claims = jwtService.resolveClaims(request);

            if (null != claims & jwtService.validateClaims(claims)) {
                String email = claims.getSubject();
                SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(email, new ArrayList<>())
                );
            }
        } catch (Exception e) {
            // todo
        }


        // todo !

    }
}
