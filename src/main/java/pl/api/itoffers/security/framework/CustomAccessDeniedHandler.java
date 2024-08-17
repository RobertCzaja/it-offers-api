package pl.api.itoffers.security.framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import pl.api.itoffers.security.ui.response.AccessDeniedResponse;

import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public CustomAccessDeniedHandler() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        response.setContentType("application/json");

        // todo check in one test if response contains correct message
        // todo make full DI fo ObjectMapper

        String serialized = objectMapper.writeValueAsString(new AccessDeniedResponse());

        response.getWriter().write(serialized);
        response.setStatus(HttpStatus.FORBIDDEN.value());
    }
}
