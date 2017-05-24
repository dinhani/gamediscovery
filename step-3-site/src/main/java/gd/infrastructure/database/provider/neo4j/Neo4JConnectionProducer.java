package gd.infrastructure.database.provider.neo4j;

import gd.domain.shared.GD;
import gd.infrastructure.enviroment.Environment;
import gd.infrastructure.log.LogMarker;
import gd.infrastructure.log.LogProducer;
import gd.infrastructure.steriotype.GDProducer;
import java.io.File;
import java.net.URISyntaxException;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.ogm.drivers.embedded.driver.EmbeddedDriver;
import org.neo4j.ogm.service.Components;
import org.neo4j.ogm.session.Neo4jSession;
import org.neo4j.ogm.session.SessionFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@GDProducer
public class Neo4JConnectionProducer {

    // ENV VARS
    private static final String NEO4J_PATH = "GD_DATA_NEO4J";

    // SERVICES
    @Autowired
    private Environment environment;

    private SessionFactory sessionFactory;

    private static final Logger LOGGER = LogProducer.getLogger(Neo4JConnectionProducer.class);

    // =========================================================================
    // INITIALIZATION
    // =========================================================================
    @PostConstruct
    public void init() {
        LOGGER.info(LogMarker.INIT, "Initializing Neo4J");
        Components.setDriver(new EmbeddedDriver(graph()));
        sessionFactory = new SessionFactory(GD.GRAPH_ENTITIES_PACKAGE);
    }

    @Bean
    public GraphDatabaseService graph() {
        String neo4jFolder = environment.readValue(NEO4J_PATH);
        neo4jFolder = StringUtils.isNotBlank(neo4jFolder) ? neo4jFolder : "database";
        LOGGER.info(LogMarker.INIT, "Neo4J folder | path={}", neo4jFolder);

        GraphDatabaseService graph = new GraphDatabaseFactory()
                .newEmbeddedDatabaseBuilder(new File(neo4jFolder))
                .setConfig(GraphDatabaseSettings.pagecache_memory, "64M")
                .setConfig(GraphDatabaseSettings.allow_store_upgrade, "true")
                .setConfig(GraphDatabaseSettings.keep_logical_logs, "false")
                .newGraphDatabase();
        return graph;
    }

    @Bean
    public SessionFactory sessionFactory() {
        return sessionFactory;
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Neo4jSession neo4jSessionWeb() throws URISyntaxException, IllegalAccessException {
        return (Neo4jSession) sessionFactory.openSession();
    }
}
