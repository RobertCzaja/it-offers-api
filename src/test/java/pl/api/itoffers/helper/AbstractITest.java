package pl.api.itoffers.helper;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles(profiles = "test")
public abstract class AbstractITest {
  @LocalServerPort private Integer port;

  public static PostgreSQLContainer<?> postgres;

  static {
    postgres =
        new PostgreSQLContainer<>("postgres:15.2")
            .withUsername("admin")
            .withPassword("admin")
            .withDatabaseName("it-offers");
  }

  @BeforeAll
  public static void beforeAll() {
    postgres.start();
  }

  @BeforeEach
  public void setUp() {
    RestAssured.baseURI = "http://localhost:" + port;
  }

  @DynamicPropertySource
  public static void containersProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
  }
}
