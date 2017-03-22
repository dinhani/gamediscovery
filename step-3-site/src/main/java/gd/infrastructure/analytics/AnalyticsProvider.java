package gd.infrastructure.analytics;

import java.util.Map;

public interface AnalyticsProvider {

    void track(String eventName, Map<String, Object> eventData);
}
