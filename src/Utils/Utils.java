package Utils;

import java.time.Duration;
import java.time.Instant;

public class Utils {
    public long getElapsedTime(Func f) {
        Instant start = Instant.now();
        f.func();
        Instant end = Instant.now();
        return Duration.between(start, end).toMillis();
    }
}
