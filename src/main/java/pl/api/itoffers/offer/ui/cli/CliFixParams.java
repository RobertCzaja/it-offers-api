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

    public enum Mode {
        test,
        migration
    }
}
