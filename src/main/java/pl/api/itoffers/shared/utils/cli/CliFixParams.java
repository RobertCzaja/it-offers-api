package pl.api.itoffers.shared.utils.cli;

import lombok.ToString;

@ToString
public class CliFixParams {
  private final Mode mode;

  public CliFixParams(String mode) {
    this.mode = CliFixParams.Mode.valueOf(mode);
  }

  public boolean isForceMode() {
    return this.mode == Mode.MIGRATION_FORCE || this.mode == Mode.TEST_FORCE;
  }

  public boolean isMigration() {
    return this.mode == Mode.MIGRATION || this.mode == Mode.MIGRATION_FORCE;
  }

  public enum Mode {
    TEST,
    TEST_FORCE,
    MIGRATION,
    MIGRATION_FORCE
  }
}
