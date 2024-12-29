package pl.api.itoffers.shared.utils.cli;

import lombok.ToString;

@ToString
public class CliFixParams {
  private final Mode mode;

  public CliFixParams(String mode) {
    this.mode = CliFixParams.Mode.valueOf(mode);
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
