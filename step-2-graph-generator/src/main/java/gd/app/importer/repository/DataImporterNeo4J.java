package gd.app.importer.repository;

import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.industry.Game;
import gd.infrastructure.database.AbstractDatabaseCommand;
import gd.infrastructure.log.LogMarker;
import gd.infrastructure.log.LogProducer;
import gd.infrastructure.steriotype.GDService;
import java.util.Collections;
import java.util.Map;
import org.slf4j.Logger;

@GDService
public class DataImporterNeo4J extends AbstractDatabaseCommand {

    private static final Logger LOGGER = LogProducer.getLogger(DataImporterNeo4J.class);

    // =========================================================================
    // EXECUTE
    // =========================================================================
    public void update(String cypher, Map<String, Object> parameters) {
        neo4jSession.query(cypher, parameters);
    }

    // =========================================================================
    // INDEX
    // =========================================================================
    public void createIndex(Class<? extends ConceptEntry> type, String field) {
        LOGGER.info(LogMarker.DB, "Creating {} {} index", type.getSimpleName(), field);

        String cypher = String.format("CREATE INDEX ON :%s(%s)", type.getSimpleName(), field);
        neo4jSession.query(cypher, Collections.EMPTY_MAP);
    }

    public void deleteIndex(Class<? extends ConceptEntry> type, String field) {
        LOGGER.info(LogMarker.DB, "Deleting {} {} index", type.getSimpleName(), field);

        String cypher = String.format("DELETE INDEX ON :%s(%s)", type.getSimpleName(), field);
        neo4jSession.query(cypher, Collections.EMPTY_MAP);
    }

    // =========================================================================
    // ORPHAN
    // =========================================================================
    public void deleteIrrelevantNodes(Class<? extends ConceptEntry> type, int minimumToKeep) {
        LOGGER.info(LogMarker.DB, "Deleting {} irrelevant nodes", type.getSimpleName());
        String cypher = String.format("MATCH (n:%s)-[]-(g:%s) ", type.getSimpleName(), Game.class.getSimpleName())
                + " WITH n, count(*) as c "
                + " WHERE c < " + minimumToKeep
                + " DETACH DELETE n";

        neo4jSession.query(cypher, Collections.EMPTY_MAP);
    }

    public void deleteOrphanNodes(Class<? extends ConceptEntry> type) {
        LOGGER.info(LogMarker.DB, "Deleting {} orphan nodes", type.getSimpleName());
        String cypher = String.format("MATCH (n:%s) ", type.getSimpleName())
                + "OPTIONAL MATCH (n)-[r]-(m) "
                + "WITH n, m "
                + "WHERE m IS NULL "
                + "DELETE n";

        neo4jSession.query(cypher, Collections.EMPTY_MAP);
    }

}
