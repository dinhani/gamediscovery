package gd.infrastructure.prerender;

import gd.infrastructure.di.DIService;
import gd.infrastructure.enviroment.Environment;
import gd.infrastructure.log.LogProducer;
import gd.infrastructure.steriotype.GDService;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

@GDService
public class PreRender {

    @Autowired
    private Environment environment;

    private Resource renderScript;

    private static final Logger LOGGER = LogProducer.getLogger(PreRender.class);

    // =========================================================================
    // INITIALIZATION
    // =========================================================================
    @PostConstruct
    public void init() {
        renderScript = DIService.getResource("classpath:scripts/prerender.rb");
    }

    // =========================================================================
    // CHECKS
    // =========================================================================
    public boolean shouldPreRender(HttpServletRequest request) {
        return shouldPreRender(request.getHeader("User-Agent"));
    }

    public boolean shouldPreRender(String agentName) {
        agentName = agentName.toLowerCase();
        return agentName.contains("googlebot") || agentName.contains("facebook");
    }

    // =========================================================================
    // EXECUTION
    // =========================================================================
    public Optional<String> preRender(HttpServletRequest request) {
        // prepare
        String urlToRender = request.getRequestURL().toString();
        if (request.getQueryString() != null) {
            urlToRender += "?" + request.getQueryString();
        }

        LOGGER.info("Prerendering | url={}", urlToRender);

        // render
        Optional<String> opRenderedContent = environment.executeScript("ruby", renderScript, urlToRender);
        return opRenderedContent.map(renderedContent -> {
            return StringUtils.replace(renderedContent, "<script type=\"text/javascript\" src=\"/app/assets/gamediscovery.js\"></script>", "");
        });
    }
}
