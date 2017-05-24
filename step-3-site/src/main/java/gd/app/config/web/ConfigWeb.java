package gd.app.config.web;

import gd.infrastructure.enviroment.Environment;
import gd.infrastructure.log.LogMarker;
import gd.infrastructure.log.LogProducer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class ConfigWeb extends WebMvcConfigurerAdapter {

    // =========================================================================
    // SERVICES
    // =========================================================================
    @Autowired
    private Environment environment;

    private static final Logger LOGGER = LogProducer.getLogger(ConfigWeb.class);

    // =========================================================================
    // CONFIGURATION
    // =========================================================================
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);

        // read folder from where images will be served
        String imagesFolder = environment.readValue("GD_DATA_IMAGES");
        if(StringUtils.isBlank(imagesFolder)){
            imagesFolder = environment.getCurrentDir() + "/images";
        }
        imagesFolder = "file:///" + imagesFolder + "/";                
        LOGGER.info(LogMarker.INIT, "Images folder | path={}", imagesFolder);

        // configure urls for assets and images
        registry
                .addResourceHandler("/assets/**", "/images/**")
                .addResourceLocations("classpath:/static/dist/", imagesFolder)
                .resourceChain(false)
                .addResolver(new GzipResourceResolver())
                .addResolver(new PathResourceResolver());
    }
}
