package pl.api.itoffers.unit.provider.justjoinit.service.extractor.v2;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.api.itoffers.data.jjit.JustJoinItParams;
import pl.api.itoffers.helper.assertions.JjitOffersAssert;
import pl.api.itoffers.provider.justjoinit.exception.JustJoinItException;
import pl.api.itoffers.provider.justjoinit.service.extractor.v2.PayloadFromJsonExtractor;
import pl.api.itoffers.shared.utils.fileManager.FileManager;

public class OffersListFromJsonPayloadExtractorTest {
  private FileManager fileManager = new FileManager();
  private PayloadFromJsonExtractor payloadFromJsonExtractor;

  @BeforeEach
  void setUp() {
    this.payloadFromJsonExtractor = new PayloadFromJsonExtractor();
  }

  @Test
  public void testShouldCorrectlyExtractOffersFromJson() throws IOException {
    String jjitJson =
        this.fileManager.readFile(JustJoinItParams.ALL_LOCATIONS_PAYLOAD_B1_DECEMBER_PATH_JSON);

    ArrayList<Map<String, Object>> offers = payloadFromJsonExtractor.extractPayload(jjitJson);

    JjitOffersAssert.expectsSize(100, offers);
  }

  @Test
  public void testShouldCorrectlyExtractOffersFromJsonThatHaveMultipleQueriesElements()
      throws IOException {
    String jjitJson =
        this.fileManager.readFile(JustJoinItParams.ALL_LOCATIONS_PAYLOAD_B2_DECEMBER_PATH_JSON);

    ArrayList<Map<String, Object>> offers = payloadFromJsonExtractor.extractPayload(jjitJson);

    JjitOffersAssert.expectsSize(100, offers);
  }

  @Test
  public void testShouldCorrectlyExtractEmptyOfferListWhenThereIsNothingInRawPayload()
      throws IOException {
    String jjitJson =
        this.fileManager.readFile(JustJoinItParams.ALL_LOCATIONS_PAYLOAD_B3_DECEMBER_PATH_JSON);

    ArrayList<Map<String, Object>> offers = payloadFromJsonExtractor.extractPayload(jjitJson);

    JjitOffersAssert.expectsSize(0, offers);
  }

  @Test
  public void testCannotExtractOffersFromJsonWhenJsonIsAnEmptyString() throws IOException {
    assertThrows(JustJoinItException.class, () -> payloadFromJsonExtractor.extractPayload(""));
    assertThrows(JustJoinItException.class, () -> payloadFromJsonExtractor.extractPayload(null));
  }
}
