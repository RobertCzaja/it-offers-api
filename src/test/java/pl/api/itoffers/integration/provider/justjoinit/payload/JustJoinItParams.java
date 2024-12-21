package pl.api.itoffers.integration.provider.justjoinit.payload;

public class JustJoinItParams {
    private final static String MAIN_CATALOG = "src/test/java/pl/api/itoffers/integration/provider/justjoinit/payload/";
    public final static String ALL_LOCATIONS_PAYLOAD_PATH = MAIN_CATALOG + "allLocationsPhp.json";
    public final static String ALL_LOCATIONS_PAYLOAD_A1_PATH = MAIN_CATALOG + "allLocationsPhpScenarioA1.json";
    public final static String ALL_LOCATIONS_PAYLOAD_A2_PATH = MAIN_CATALOG + "allLocationsPhpScenarioA2.json";
    public final static String ALL_LOCATIONS_PAYLOAD_DUPLICATED_1_PATH = MAIN_CATALOG + "allLocationsPhpScenarioDuplicated1.json";
    public final static String ALL_LOCATIONS_PAYLOAD_DUPLICATED_2_PATH = MAIN_CATALOG + "allLocationsPhpScenarioDuplicated2.json";
    public final static String ALL_LOCATIONS_PAYLOAD_DUPLICATED_3_PATH = MAIN_CATALOG + "allLocationsPhpScenarioDuplicated3.json";
    /** in december 2024 JJIT changed structure of the HTML file under that endpoint so it-offers parser needs to be adjusted */
    public final static String ALL_LOCATIONS_PAYLOAD_B1_DECEMBER = MAIN_CATALOG + "allLocationsScenarioB1December.html";
}
