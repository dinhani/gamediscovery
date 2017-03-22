package gd.infrastructure.analytics;

import com.google.common.collect.Maps;
import gd.infrastructure.steriotype.GDService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

@GDService
public class Analytics {

    @Autowired
    private AnalyticsProvider analytics;

    public void track(String eventName, String... data) {

        Map<String, Object> eventData = Maps.newHashMap();

        String key = "";
        String value = "";
        for (int i = 0; i < data.length; i++) {
            // current position is key
            if (i % 2 == 0) {
                key = data[i];
            }
            // current position is value
            if (i % 2 == 1) {
                value = data[i];
            }

            // add event data
            if (i % 2 == 1) {
                eventData.put(key, value);
                key = "";
                value = "";
            }
        }

        // track
        analytics.track(eventName, eventData);
    }
}
