package pl.api.itoffers.integration.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.helper.ApiAuthorizationHelper;
import pl.api.itoffers.helper.AuthorizationCredentials;
import pl.api.itoffers.security.ui.controller.TestController;

import static org.assertj.core.api.Assertions.assertThat;

public class RolesITest extends AbstractITest {
    @Autowired
    private TestRestTemplate template;
    @Autowired
    private ApiAuthorizationHelper apiAuthorizationHelper;

    @Test
    void testShouldAllowAdminToUseEndpointWithUserRole() {

        ResponseEntity<String> response = template.exchange(
                TestController.PATH,
                HttpMethod.GET,
                new HttpEntity<>(apiAuthorizationHelper.getHeaders(AuthorizationCredentials.ADMIN)),
                String.class
        );

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }
}
