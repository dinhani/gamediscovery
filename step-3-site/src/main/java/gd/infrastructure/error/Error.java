package gd.infrastructure.error;

import com.mindscapehq.raygun4java.core.RaygunClient;
import gd.infrastructure.enviroment.Environment;
import gd.infrastructure.steriotype.GDService;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

@GDService
public class Error {

    @Autowired
    private Environment environment;

    // =========================================================================
    // INITIALIZATION
    // =========================================================================
    @PostConstruct
    public void init() {

    }

    // =========================================================================
    // TRACKING
    // =========================================================================
    public void track(Exception e) {
        if (environment.isProduction()) {
            // I was tracking with Raygun, but not anymore
        }
    }
}
