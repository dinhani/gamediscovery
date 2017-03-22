package gd.app.importer.step;

import gd.domain.shared.GD;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public abstract class AbstractStep {

    private static ApplicationContext ctx;

    public final ApplicationContext initSpringContext() {
        System.getProperties().setProperty("spring.profiles.active", "standalone");
        if (ctx == null) {
            ctx = new FileSystemXmlApplicationContext(GD.SPRING_BEANS_FILE);
        }

        return ctx;
    }
}
