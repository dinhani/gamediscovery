package gd.infrastructure.di;

import gd.infrastructure.log.LogMarker;
import gd.infrastructure.log.LogProducer;
import gd.infrastructure.steriotype.GDService;
import java.util.Collection;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

@GDService
public class DIService {

    private static ApplicationContext ctx;
    private static final Logger LOGGER = LogProducer.getLogger(DIService.class);

    // =========================================================================
    // INITIALIZATION
    // =========================================================================
    @Autowired
    private ApplicationContext _ctx;

    @PostConstruct
    public void init() {
        LOGGER.debug(LogMarker.INIT, "Initializing DIService | ctx={}", _ctx);
        ctx = _ctx;
    }

    // =========================================================================
    // FACTORY
    // =========================================================================
    public static <T> T getBean(Class<T> type) {
        return ctx.getBean(type);
    }

    public static <T> Collection<T> getBeansOfType(Class<T> type) {
        return ctx.getBeansOfType(type).values();
    }

    public static Resource getResource(String name) {
        return ctx.getResource(name);
    }

}
