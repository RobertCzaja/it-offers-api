package pl.api.itoffers.data.jjit;

public class JustJoinItParams {
  private static final String MAIN_CATALOG = "src/test/java/pl/api/itoffers/data/jjit/";
  public static final String ALL_LOCATIONS_PAYLOAD_PATH = MAIN_CATALOG + "allLocationsPhp.json";

  /**
   * in december 2024 JJIT changed structure of the HTML file under that endpoint so it-offers
   * parser needs to be adjusted
   */
  public static final String V2_ALL_LOCATIONS_PAYLOAD_B1_DECEMBER_PATH_HTML =
      MAIN_CATALOG + "v2/allLocationsScenarioB1December.html";

  public static final String ALL_LOCATIONS_PAYLOAD_B1_DECEMBER_PATH_JSON =
      MAIN_CATALOG + "allLocationsScenarioB1December.json";
  public static final String ALL_LOCATIONS_PAYLOAD_B2_DECEMBER_PATH_JSON =
      MAIN_CATALOG + "allLocationsScenarioB2December.json";
  public static final String ALL_LOCATIONS_PAYLOAD_B3_DECEMBER_PATH_JSON =
      MAIN_CATALOG + "allLocationsScenarioB3December.json";

  public static final String V2_ALL_LOCATIONS_PHP_DUPLICATED_1_HTML =
      MAIN_CATALOG + "v2/allLocationsPhpDuplicated1AsHtml.txt";
  public static final String V2_ALL_LOCATIONS_PHP_DUPLICATED_2_HTML =
      MAIN_CATALOG + "v2/allLocationsPhpDuplicated2AsHtml.txt";
}
