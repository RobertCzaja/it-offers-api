package pl.api.itoffers.data.nfj;

public class NoFluffJobsParams {
  public record JsonOffer(String list, String details) {}

  private static final String MAIN_CATALOG = "src/test/java/pl/api/itoffers/data/nfj/";
  public static final String LIST_PHP_JSON_PATH = MAIN_CATALOG + "listPhp.json";

  public static JsonOffer A1_PHP =
      new JsonOffer(
          NoFluffJobsParams.MAIN_CATALOG + "A1listOfferPhp.json",
          NoFluffJobsParams.MAIN_CATALOG + "A1detailsOfferPhp.json");

  public static final String DETAILS_JAVA_JSON_PATH = MAIN_CATALOG + "detailsJava.json";

  /**
   * The extension is 'txt' because html file changed consideration the whole project in the GitHub
   * from Java to HTML
   */
  public static final String LIST_PHP_HTML_PATH = MAIN_CATALOG + "listPhpAsHtml.txt";

  public static final String LIST_JAVA_HTML_PATH = MAIN_CATALOG + "listJavaAsHtml.txt";
  public static final String DETAILS_JAVA_HTML_PATH = MAIN_CATALOG + "detailsJavaAsHtml.txt";
}
