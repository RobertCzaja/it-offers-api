package pl.api.itoffers.shared.utils.monitor;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@NoArgsConstructor
public class MemoryUsage {
    private static final int DATA_SIZE = 1024 * 1024;
    private Runtime runtime = Runtime.getRuntime();

    public void log(String context) {
        log.info(
            "[memory-usage] used: {}; free: {}; allocated: {}; max: {}: context: {}",
            format(runtime.totalMemory() - runtime.freeMemory()),
            format(runtime.freeMemory()),
            format(runtime.totalMemory()),
            format(runtime.maxMemory()),
            context
        );
    }

    private static String format(long memoryValue) {
        return memoryValue/DATA_SIZE + " MB";
    }
}
