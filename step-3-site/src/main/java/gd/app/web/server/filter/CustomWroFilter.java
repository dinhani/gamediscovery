package gd.app.web.server.filter;

import com.mashape.unirest.http.Unirest;
import gd.infrastructure.enviroment.Environment;
import gd.infrastructure.log.LogMarker;
import gd.infrastructure.log.LogProducer;
import gd.infrastructure.steriotype.GDFilter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ro.isdc.wro.http.WroFilter;

@GDFilter
public class CustomWroFilter extends WroFilter {

    private static final Logger LOGGER = LogProducer.getLogger(CustomWroFilter.class);

    @Autowired
    private Environment environment;

    @Override
    protected void doInit(FilterConfig config) throws ServletException {
        // check if running in dev mode
        if (environment.isDevelopment()) {
            LOGGER.info(LogMarker.INIT, "Initializing WroFilter | stage=development");
            getConfiguration().setDebug(true);
            getConfiguration().setMinimizeEnabled(false);
            getConfiguration().setResourceWatcherUpdatePeriod(1);
            getConfiguration().setIgnoreMissingResources(false);
        } else if (environment.isProduction()) {
            LOGGER.info(LogMarker.INIT, "Initializing WroFilter | stage=production");
            // properties config are production and they are built at compile time, so just keep them
        }

        // disable GZip because we are using Jetty GZip filter
        getConfiguration().setGzipEnabled(false);

        // trigger request to init resources
        Unirest.get(environment.getApplicationURL() + "/app/assets/gamediscovery.css").asStringAsync();
        Unirest.get(environment.getApplicationURL() + "/app/assets/gamediscovery.js").asStringAsync();
    }

}
