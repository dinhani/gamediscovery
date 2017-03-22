package gd.domain.entities.repository.query;

import com.google.common.collect.Maps;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.industry.Game;
import gd.domain.entities.entity.industry.Platform;
import gd.infrastructure.database.AbstractDatabaseCommand;
import gd.infrastructure.database.QueryListingResult;
import gd.infrastructure.log.LogMarker;
import gd.infrastructure.log.LogProducer;
import gd.infrastructure.steriotype.GDQuery;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;

@GDQuery
public class QueryConceptEntriesByUIDs extends AbstractDatabaseCommand {

    private static final Logger LOGGER = LogProducer.getLogger(QueryConceptEntriesByUIDs.class);

    // QUERIES
    private static final String QUERY_FOR_ADDITIONAL_DETAILS = String.format(""
            // not games
            + "MATCH p=(c:%s) WHERE c.uid IN {conceptEntriesUids} "
            + "RETURN p "
            // union
            + "UNION ALL "
            // games
            + "MATCH (g:%s) WHERE g.uid IN {gamesUids} "
            + "WITH g "
            + "MATCH p=(g)-[*0..1]-(ct:%s) WHERE ct:%s "
            + "RETURN p",
            ConceptEntry.class.getSimpleName(), Game.class.getSimpleName(), ConceptEntry.class.getSimpleName(), Platform.class.getSimpleName());

    // =========================================================================
    // EXECUTION
    // =========================================================================
    public QueryListingResult<ConceptEntry> execute(Collection<String> uids) {
        return QueryListingResult.create(getConceptEntries(uids));
    }

    public QueryListingResult<ConceptEntry> execute(Map<String, Integer> uids) {
        LOGGER.trace(LogMarker.DB, "Retrieving concept entries by UIDs | uids={}", uids);

        Iterable<ConceptEntry> entries = getConceptEntries(uids.keySet());
        Iterable<ConceptEntry> sortedEntries = sortConceptEntries(entries, uids);

        return QueryListingResult.create(sortedEntries);
    }

    // =========================================================================
    // STEPS
    // =========================================================================
    private Iterable<ConceptEntry> getConceptEntries(Collection<String> uids) {
        // retrieve concept entries from database        
        Map<String, Object> queryParameters = Maps.newHashMapWithExpectedSize(1);
        queryParameters.put("conceptEntriesUids", uids.stream().filter(uid -> !uid.startsWith("game-")).collect(Collectors.toList()));
        queryParameters.put("gamesUids", uids.stream().filter(uid -> uid.startsWith("game-")).collect(Collectors.toList()));

        return neo4jSession.query(ConceptEntry.class, QUERY_FOR_ADDITIONAL_DETAILS, queryParameters);
    }

    private Collection<ConceptEntry> sortConceptEntries(Iterable<ConceptEntry> entries, Map<String, Integer> uidsWithOrder) {
        ConceptEntry[] sortedEntries = new ConceptEntry[uidsWithOrder.size()];

        entries.forEach(entry -> {
            Integer position = uidsWithOrder.get(entry.getUid());
            // it can be null because OGM returns all entities that matches the type, not only the starting nodes
            if (position != null) {
                sortedEntries[position] = entry;
            }
        });

        // remove null elements from the array
        // I don't know yet why there are null elements
        return Arrays.stream(sortedEntries).filter(entry -> entry != null).collect(Collectors.toList());
    }
}
