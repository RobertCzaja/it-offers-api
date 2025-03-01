package pl.api.itoffers.helper;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles(profiles = "test")
@EnableAsync
public abstract class AbstractITest {
  @LocalServerPort private Integer port;

  public static PostgreSQLContainer<?> postgres;
  public static MongoDBContainer mongo;

  static {
    postgres =
        new PostgreSQLContainer<>("postgres:15.2")
            .withUsername("admin")
            .withPassword("admin")
            .withDatabaseName("it-offers");
    mongo = new MongoDBContainer("mongo:6.0");
  }

  @BeforeAll
  public static void beforeAll() {
    postgres.start();
    mongo.start();
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
    registry.add("spring.data.mongodb.uri", mongo::getReplicaSetUrl);
  }
}
