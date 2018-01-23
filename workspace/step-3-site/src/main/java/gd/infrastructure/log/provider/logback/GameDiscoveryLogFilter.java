package gd.infrastructure.log.provider.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import gd.infrastructure.log.LogMarker;
import org.slf4j.Marker;

public class GameDiscoveryLogFilter extends Filter<ILoggingEvent> {

    @Override
    public FilterReply decide(ILoggingEvent event) {
        Marker logMarker = event.getMarker();
        if (logMarker == LogMarker.REQUEST || logMarker == LogMarker.PROFILE) {
            return FilterReply.DENY;
        } else {
            return FilterReply.ACCEPT;
        }
    }

}
