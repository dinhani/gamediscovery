package gd.infrastructure.enviroment;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import gd.infrastructure.di.DIService;
import gd.infrastructure.error.ErrorMessages;
import gd.infrastructure.log.LogMarker;
import gd.infrastructure.log.LogProducer;
import gd.infrastructure.steriotype.GDService;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.jar.Manifest;
import javax.annotation.PostConstruct;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@GDService
public class Environment {

    @Autowired
    private ResourceLoader resource;

    // CONSTANTS
    private static final String PROJECT_STAGE_DEVELOPMENT = "development";
    private static final String PROJECT_STAGE_PRODUCTION = "production";
    private static final String SCRIPTS_FOLDER = "./scripts/prerender/";

    // =========================================================================
    // ENV VARS
    // =========================================================================
    private final String PROJECT_STAGE = "GD_ENVIRONMENT";

    // =========================================================================
    // DATA
    // =========================================================================
    private String currentEnviroment;
    private String currentVersion;
    private final Map<String, String> scripts = Maps.newHashMap();

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
        initProjectVersion();
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
            LOGGER.warn(LogMarker.INIT, "{} variable has not a valid value {}. Defaulting to {}", tempEnvironment, PROJECT_STAGE, PROJECT_STAGE_PRODUCTION);
            tempEnvironment = PROJECT_STAGE_PRODUCTION;
        }

        // set project stage
        currentEnviroment = tempEnvironment;
        LOGGER.trace(LogMarker.INIT, "Setting project stage | stage={}", currentEnviroment);
    }

    private void initProjectVersion() {
        try {
            Manifest manifest = new Manifest(resource.getResource("/META-INF/MANIFEST.MF").getInputStream());
            currentVersion = manifest.getMainAttributes().getValue("Build-Time");
        } catch (IOException ex) {
            currentVersion = "";
        }
    }

    // =========================================================================
    // EXECUTION
    // =========================================================================
    public Optional<String> executeScript(String command, Resource script, String args) {
        // validate
        Preconditions.checkArgument(StringUtils.isNotBlank(command), ErrorMessages.NOT_BLANK, "command");
        Preconditions.checkArgument(script != null, ErrorMessages.NOT_NULL, "script");

        // execute
        try {
            // get the script path
            String scriptPath = scripts.get(script.getFilename());

            // create the script in the filesystem if not already created
            if (scriptPath == null) {
                // read and write script to filesystem
                scriptPath = script.getFilename();
                String scriptContent = IOUtils.toString(script.getInputStream());
                FileUtils.writeStringToFile(new File(SCRIPTS_FOLDER + scriptPath), scriptContent);

                // save reference
                scripts.put(script.getFilename(), scriptPath);
            }

            // execute script
            command += " " + SCRIPTS_FOLDER + scriptPath + " " + args;
            LOGGER.info("Executing command | command=\"{}\"", command);
            Process process = Runtime.getRuntime().exec(command);

            // read output
            String scriptOutput = IOUtils.toString(process.getInputStream());
            process.waitFor();

            // return output
            return Optional.of(scriptOutput);
        } catch (IOException | InterruptedException e) {
            LOGGER.error("Error executing script | message={}", e.getMessage(), e);
            return Optional.empty();
        }
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
    /**
     * Get the current application URL base. It is different if running is
     * production or development.
     *
     * @return The application URL.
     */
    public String getApplicationURL() {
        if (isDevelopment()) {
            return "http://localhost:3001";
        } else {
            return "http://www.gamediscovery.net";
        }
    }

    /**
     * Get the current application build version. The version is generated with
     * Maven using the build date.
     *
     * @return The application version.
     */
    public String getApplicationVersion() {
        return currentVersion;
    }
}
