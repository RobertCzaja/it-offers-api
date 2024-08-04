package pl.api.itoffers.security.framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
            handleException(e, response);
        }
        filterChain.doFilter(request, response);
    }

    private void handleException(Exception e, HttpServletResponse response) throws IOException {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("message", "Authentication Error");
        errorDetails.put("details",e.getMessage());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        mapper.writeValue(response.getWriter(), errorDetails);
    }
}
