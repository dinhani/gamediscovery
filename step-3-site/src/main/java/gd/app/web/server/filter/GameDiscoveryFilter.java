package gd.app.web.server.filter;

import gd.infrastructure.di.DIService;
import gd.infrastructure.enviroment.Environment;
import gd.infrastructure.log.Log;
import gd.infrastructure.log.LogMarker;
import gd.infrastructure.log.LogProducer;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class GameDiscoveryFilter extends GenericFilterBean {

    private static Log log;
    private static Environment environment;

    private static final Logger LOGGER = LogProducer.getLogger(GameDiscoveryFilter.class);

    // =========================================================================
    // EXECUTION
    // =========================================================================
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            // init services that are necessary
            lazyInitServices();

            // generate toWWW unique tracking code for this request
            generateTracking();

            // log the client request
            logRequest(httpRequest);

            // proceed with filter chain
            chain.doFilter(httpRequest, httpResponse);

        } finally {
            removeTracking();
        }
    }

    private void lazyInitServices() {
        if (log == null) {
            log = DIService.getBean(Log.class);
        }
        if (environment == null) {
            environment = DIService.getBean(Environment.class);
        }
    }

    // =========================================================================
    //  TRACKING
    // =========================================================================
    private void generateTracking() {
        log.generateTrackingToMDC();
    }

    private void removeTracking() {
        log.removeTrackingFromMDC();
    }

    // =========================================================================
    // REQUEST
    // =========================================================================
    private void logRequest(HttpServletRequest request) {
        // uri
        String uri = request.getRequestURI();
        if (request.getQueryString() != null) {
            uri += "?" + request.getQueryString();
        }
        uri = StringUtils.rightPad(uri, 80);

        // user agent
        String userAgent = request.getHeader("User-Agent");

        // log
        LOGGER.info(LogMarker.REQUEST, "uri={}, user-agent={}", uri, userAgent);
    }

}
