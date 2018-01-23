package gd.infrastructure.enviroment;

import gd.infrastructure.di.DIService;
import gd.infrastructure.log.LogMarker;
import gd.infrastructure.log.LogProducer;
import gd.infrastructure.steriotype.GDService;
import java.io.IOException;
import java.util.jar.Manifest;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;

@GDService
public class Environment {

    @Autowired
    private ResourceLoader resource;

    // CONSTANTS
    private static final String PROJECT_STAGE_DEVELOPMENT = "development";
    private static final String PROJECT_STAGE_PRODUCTION = "production";

    // =========================================================================
    // ENV VARS
    // =========================================================================
    private final String PROJECT_STAGE = "GD_ENVIRONMENT";

    // =========================================================================
    // DATA
    // =========================================================================
    private String currentEnviroment;    

    // =========================================================================
    // SERVICES
    // =========================================================================
    @Autowired
    private DIService di;

    // LOGGER
    private final Logger LOGGER = LogProducer.getLogger(Environment.class);

    // =========================================================================
    // INITIALIZATION
    // =========================================================================
    @PostConstruct
    public void init() {
        LOGGER.info(LogMarker.INIT, "Initializing Environment");
        initProjectStage();        
    }

    private void initProjectStage() {
        // check if already initialized
        if (currentEnviroment != null) {
            return;
        }

        // read fro env
        String tempEnvironment = System.getenv(PROJECT_STAGE);

        // if not set, use production
        // if set a invalid value, use production
        if (StringUtils.isBlank(tempEnvironment)) {
            LOGGER.warn(LogMarker.INIT, "{} variable is not set in the environment. Defaulting to {}", PROJECT_STAGE, PROJECT_STAGE_PRODUCTION);
            tempEnvironment = PROJECT_STAGE_PRODUCTION;
        } else if (!tempEnvironment.equalsIgnoreCase(PROJECT_STAGE_DEVELOPMENT) && !tempEnvironment.equalsIgnoreCase(PROJECT_STAGE_PRODUCTION)) {
            LOGGER.warn(LogMarker.INIT, "{} variable has an invalid value {}. Defaulting to {}", PROJECT_STAGE, tempEnvironment, PROJECT_STAGE_PRODUCTION);
            tempEnvironment = PROJECT_STAGE_PRODUCTION;
        }

        // set project stage
        currentEnviroment = tempEnvironment;
        LOGGER.trace(LogMarker.INIT, "Setting project stage | stage={}", currentEnviroment);
    }

    // =========================================================================
    // CHECKS
    // =========================================================================
    public boolean isProduction() {
        return currentEnviroment.equalsIgnoreCase(PROJECT_STAGE_PRODUCTION);
    }

    public boolean isDevelopment() {
        return currentEnviroment.equalsIgnoreCase(PROJECT_STAGE_DEVELOPMENT);
    }

    // =========================================================================
    // READS
    // =========================================================================
    /**
     * Read an environment variable value.
     *
     * @param environmentVariable The environment variable name that will be
     * read.
     * @return The value of the environment variable.
     */
    public String readValue(String environmentVariable) {
        LOGGER.debug(LogMarker.DB, "Reading {} environment variable", environmentVariable);
        return System.getenv(environmentVariable);
    }

    // =========================================================================
    // GETTERS
    // =========================================================================
    public String getCurrentDir() {
        return System.getProperty("user.dir");
    }
}
