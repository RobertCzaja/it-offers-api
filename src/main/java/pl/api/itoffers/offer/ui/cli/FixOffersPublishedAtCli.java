package pl.api.itoffers.offer.ui.cli;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

/**
 * OneUse - should be used only once, only for data fix
 * @deprecated
 */
@Slf4j
@ShellComponent
@Transactional
@AllArgsConstructor
public class FixOffersPublishedAtCli {

    @ShellMethod(key="i")
    public void fixAllOffersWithWrongPublishedAt(
        @ShellOption(defaultValue = "test") String mode,
        @ShellOption(defaultValue = "1") String limit
    ) {
        CliFixParams params = new CliFixParams(mode, Integer.valueOf(limit));
        log.info("Input: {}", params);
    }
}
