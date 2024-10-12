package pl.api.itoffers.offer.ui.cli;

import lombok.ToString;

@ToString
public class CliFixParams {
    private Mode mode;
    private Integer limit;

    public CliFixParams(String mode, Integer limit) {
        this.mode = CliFixParams.Mode.valueOf(mode);
        this.limit = limit;
    }

    public boolean isForceMode() {
        return this.mode == Mode.migrationforce || this.mode == Mode.testforce;
    }

    public boolean isMigration() {
        return this.mode == Mode.migration || this.mode == Mode.migrationforce;
    }

    public enum Mode {
        test,
        testforce,
        migration,
        migrationforce
    }
}
