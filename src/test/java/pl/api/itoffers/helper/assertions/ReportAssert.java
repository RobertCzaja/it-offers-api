package pl.api.itoffers.helper.assertions;

public class ReportAssert {

  public static String expectedReport() {
    return "Provider: NO_FLUFF_JOBS\n"
        + "Started at: 17:28:05 (10-01-2025)\n"
        + "Finished at: 17:31:28 (10-01-2025)\n"
        + "Time: 3m 23s\n"
        + "New offers for technologies:\n"
        + "java: 1 \n"
        + "php: 2 \n";
  }
}
