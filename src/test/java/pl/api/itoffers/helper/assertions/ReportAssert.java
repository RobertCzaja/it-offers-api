package pl.api.itoffers.helper.assertions;

public class ReportAssert {

  public static String expectedReport() {
    return """
        Provider: NO_FLUFF_JOBS
        Started at: 17:28:05 (10-01-2025)
        Finished at: 17:31:28 (10-01-2025)
        Time: 3m 23s
        New offers for technologies:
        java: 1
        php: 2
        """;
  }
}
