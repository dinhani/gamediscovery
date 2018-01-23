package gd.domain.entities.repository.query;

import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.ConceptType;
import gd.infrastructure.database.AbstractDatabaseCommand;
import gd.infrastructure.log.LogMarker;
import gd.infrastructure.log.LogProducer;
import gd.infrastructure.steriotype.GDQuery;
import java.util.Collection;
import org.neo4j.ogm.cypher.Filter;
import org.slf4j.Logger;

@GDQuery
public class QueryConceptEntryExists extends AbstractDatabaseCommand {

    private static final Logger LOGGER = LogProducer.getLogger(QueryConceptEntryExists.class);

    public boolean execute(ConceptType type, String entryUid) {
        LOGGER.trace(LogMarker.DB, "Checking existence of concept entry | {}, entryUid={}", type, entryUid);

        // prepare
        Filter filterByUid = new Filter("uid", entryUid);

        // execute
        Collection<? extends ConceptEntry> nodesFound = neo4jSession.loadAll(type.getTargetClass(), filterByUid, 0);

        // check if found or not
        if (!nodesFound.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

}
