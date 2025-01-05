package pl.api.itoffers.integration.provider.nofluffjobs;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.provider.nofluffjobs.NoFluffJobsConnector;

public class NoFluffJobsConnectorITest extends AbstractITest {

  @Autowired private NoFluffJobsConnector connector;

  @BeforeEach
  public void setUp() {
    super.setUp();
    // todo clear state
  }

  @Test
  void ______() {

    // todo add implementation
    assertThat("").isNotNull();
  }
}
