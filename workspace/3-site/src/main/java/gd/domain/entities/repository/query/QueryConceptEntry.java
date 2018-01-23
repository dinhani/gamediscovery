package gd.domain.entities.repository.query;

import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.ConceptType;
import gd.infrastructure.database.AbstractDatabaseCommand;
import gd.infrastructure.log.LogMarker;
import gd.infrastructure.log.LogProducer;
import gd.infrastructure.steriotype.GDQuery;
import java.util.Collection;
import java.util.Optional;
import org.neo4j.ogm.cypher.Filter;
import org.slf4j.Logger;

@GDQuery
public class QueryConceptEntry extends AbstractDatabaseCommand {

    private static final Logger LOGGER = LogProducer.getLogger(QueryConceptEntry.class);

    public Optional<ConceptEntry> execute(ConceptType type, String entryUid, boolean fetchRelationships) {
        return execute(type.getTargetClass(), entryUid, fetchRelationships);
    }

    public Optional<ConceptEntry> execute(Class<? extends ConceptEntry> type, String entryUid, boolean fetchRelationships) {
        LOGGER.trace(LogMarker.DB, "Retrieving concept entry | type={}, entryUid={}", type, entryUid);

        // prepare
        Filter filterByUid = new Filter("uid", entryUid);
        int fetchDepth = fetchRelationships ? 1 : 0;

        // execute
        Collection<? extends ConceptEntry> nodesFound = neo4jSession.loadAll(type, filterByUid, fetchDepth);

        // check if found or not
        if (!nodesFound.isEmpty()) {
            return Optional.of(nodesFound.iterator().next());
        } else {
            return Optional.empty();
        }
    }

}
