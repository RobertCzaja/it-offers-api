package pl.api.itoffers.unit.provider.justjoinit.service.v1;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import org.junit.Test;
import pl.api.itoffers.data.jjit.JustJoinItParams;
import pl.api.itoffers.helper.assertions.JjitOffersAssert;
import pl.api.itoffers.provider.justjoinit.service.v1.JustJoinItPayloadExtractor;
import pl.api.itoffers.shared.utils.fileManager.FileManager;

public class JustJoinItPayloadExtractorTest {

  @Test
  public void shouldExtractOffersFromValidPayload() throws IOException {

    String justJoinItPayload = FileManager.readFile(JustJoinItParams.ALL_LOCATIONS_PAYLOAD_PATH);

    ArrayList<Map<String, Object>> offers =
        new JustJoinItPayloadExtractor().extract(justJoinItPayload);

    JjitOffersAssert.expectsSize(87, offers);
  }
}
