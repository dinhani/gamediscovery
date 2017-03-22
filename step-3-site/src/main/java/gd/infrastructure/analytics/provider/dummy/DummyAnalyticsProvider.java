package gd.infrastructure.analytics.provider.dummy;

import gd.infrastructure.analytics.AnalyticsProvider;
import gd.infrastructure.steriotype.GDService;
import java.util.Map;

@GDService
public class DummyAnalyticsProvider implements AnalyticsProvider {

    @Override
    public void track(String eventName, Map<String, Object> eventData) {

    }

}
