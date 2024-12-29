package pl.api.itoffers.shared.utils.cli;

public class FixReport {
  private final int limit;
  private final int fetchedCount;
  private int processingStarted = 0;
  private int migrated = 0;

  public FixReport(Integer limit, Integer fetchedCount) {
    this.limit = limit;
    this.fetchedCount = fetchedCount;
  }

  public boolean limitReached() {
    return processingStarted >= limit;
  }

  public void startProcessing() {
    processingStarted++;
  }

  public void migratedSuccessfully() {
    migrated++;
  }

  @Override
  public String toString() {
    return "Script finished successfully: "
        + "\n"
        + "fetchedCount: "
        + fetchedCount
        + "\n"
        + "limit: "
        + limit
        + "\n"
        + "processingStarted: "
        + processingStarted
        + "\n"
        + "migrated: "
        + migrated
        + "\n";
  }
}
