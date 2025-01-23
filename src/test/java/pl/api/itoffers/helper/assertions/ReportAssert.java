package pl.api.itoffers.helper.assertions;

public class ReportAssert {

  public static String expectedReport() {
    return "Provider: NO_FLUFF_JOBS\n"
        + "Started at: 10 stycznia 2025, 17:28:05\n"
        + "Finished at: 10 stycznia 2025, 17:31:28\n"
        + "Time: 3m 23s\n"
        + "New offers for technologies:\n"
        + "java: 1 \n"
        + "php: 2 \n";
  }
}
