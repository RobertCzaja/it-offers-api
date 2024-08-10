package pl.api.itoffers.security.application.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix="jwt.token")
public class JwtParamsDto {
    private String secret;
    private Integer lifetime;
}
