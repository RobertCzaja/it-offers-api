package pl.api.itoffers.data.jjit;

public class JustJoinItParams {
  private static final String MAIN_CATALOG = "src/test/java/pl/api/itoffers/data/jjit/";
  public static final String ALL_LOCATIONS_PAYLOAD_PATH = MAIN_CATALOG + "allLocationsPhp.json";
  public static final String ALL_LOCATIONS_PAYLOAD_A1_PATH =
      MAIN_CATALOG + "allLocationsPhpScenarioA1.json";
  public static final String ALL_LOCATIONS_PAYLOAD_A2_PATH =
      MAIN_CATALOG + "allLocationsPhpScenarioA2.json";
  public static final String ALL_LOCATIONS_PAYLOAD_DUPLICATED_1_PATH =
      MAIN_CATALOG + "allLocationsPhpScenarioDuplicated1.json";
  public static final String ALL_LOCATIONS_PAYLOAD_DUPLICATED_2_PATH =
      MAIN_CATALOG + "allLocationsPhpScenarioDuplicated2.json";
  public static final String ALL_LOCATIONS_PAYLOAD_DUPLICATED_3_PATH =
      MAIN_CATALOG + "allLocationsPhpScenarioDuplicated3.json";

  /**
   * in december 2024 JJIT changed structure of the HTML file under that endpoint so it-offers
   * parser needs to be adjusted
   */
  public static final String ALL_LOCATIONS_PAYLOAD_B1_DECEMBER_PATH_HTML =
      MAIN_CATALOG + "allLocationsScenarioB1December.html";

  public static final String ALL_LOCATIONS_PAYLOAD_B1_DECEMBER_PATH_JSON =
      MAIN_CATALOG + "allLocationsScenarioB1December.json";
  public static final String ALL_LOCATIONS_PAYLOAD_B2_DECEMBER_PATH_JSON =
      MAIN_CATALOG + "allLocationsScenarioB2December.json";
}
