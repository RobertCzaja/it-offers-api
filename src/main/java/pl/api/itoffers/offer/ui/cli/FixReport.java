package pl.api.itoffers.offer.ui.cli;

public class FixReport {
    private int limit;
    private int fetchedCount;
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
        return "Script finished successfully: " + "\n" +
            "fetchedCount: " + fetchedCount + "\n" +
            "limit: " + limit + "\n" +
            "processingStarted: " + processingStarted + "\n" +
            "migrated: " + migrated + "\n";
    }
}
