package pl.api.itoffers.data.nfj;

public class NoFluffJobsParams {
  public record JsonOffer(String list, String details) {}

  private static final String MAIN_CATALOG = "src/test/java/pl/api/itoffers/data/nfj/";
  public static final String LIST_PHP_JSON_PATH = MAIN_CATALOG + "listPhp.json";

  public static JsonOffer A1_PHP =
      new JsonOffer(
          NoFluffJobsParams.MAIN_CATALOG + "A1listOfferPhp.json",
          NoFluffJobsParams.MAIN_CATALOG + "A1detailsOfferPhp.json");
  public static JsonOffer A2_PHP =
      new JsonOffer(
          NoFluffJobsParams.MAIN_CATALOG + "A2listOfferPhp.json",
          NoFluffJobsParams.MAIN_CATALOG + "A1detailsOfferPhp.json");
  public static JsonOffer A3_PHP =
      new JsonOffer(
          NoFluffJobsParams.MAIN_CATALOG + "A3listOfferPhp.json",
          NoFluffJobsParams.MAIN_CATALOG + "A1detailsOfferPhp.json");

  public static String B1_E2E_PHP_LIST = MAIN_CATALOG + "scenario1/listPhpAsHtml.txt";
  public static String B1_E2E_PHP_1_SLUG = "frontend-engineer-react-box-warsaw";
  public static String B1_E2E_PHP_1_DETAILS = MAIN_CATALOG + "scenario1/detailsPhp1AsHtml.txt";
  public static String B1_E2E_PHP_2_SLUG = "software-engineer-backend-java-box-warsaw";
  public static String B1_E2E_PHP_2_DETAILS = MAIN_CATALOG + "scenario1/detailsPhp2AsHtml.txt";
  public static String B1_E2E_JAVA_LIST = MAIN_CATALOG + "scenario1/listJavaAsHtml.txt";
  public static String B1_E2E_JAVA_1_SLUG = "senior-software-engineer-salon-ops-phorest-remote";
  public static String B1_E2E_JAVA_1_DETAILS = MAIN_CATALOG + "scenario1/detailsJava1AsHtml.txt";
  public static String B1_E2E_JAVA_2_SLUG = "remote-senior-fullstack-developer-tempest-labs";
  public static String B1_E2E_JAVA_2_DETAILS = MAIN_CATALOG + "scenario1/detailsJava2AsHtml.txt";

  public static final String DETAILS_JAVA_JSON_PATH = MAIN_CATALOG + "detailsJava.json";

  /**
   * The extension is 'txt' because html file changed consideration the whole project in the GitHub
   * from Java to HTML
   */
  public static final String LIST_PHP_HTML_PATH = MAIN_CATALOG + "listPhpAsHtml.txt";

  public static final String LIST_JAVA_HTML_PATH = MAIN_CATALOG + "listJavaAsHtml.txt";
  public static final String DETAILS_JAVA_HTML_PATH = MAIN_CATALOG + "detailsJavaAsHtml.txt";
}
