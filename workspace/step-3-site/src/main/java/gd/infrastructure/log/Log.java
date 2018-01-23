package gd.infrastructure.log;

import gd.infrastructure.steriotype.GDService;
import org.slf4j.MDC;

@GDService
public class Log {

    public void generateTrackingToMDC() {
        long tracking = System.currentTimeMillis();
        MDC.put("tracking", Long.toString(tracking));
    }

    public void removeTrackingFromMDC() {
        MDC.remove("tracking");
    }
}
