package gd.domain.entities.repository.query;

import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.ConceptType;
import gd.infrastructure.database.QueryPagination;
import gd.infrastructure.database.AbstractDatabaseCommand;
import gd.infrastructure.database.QueryListingResult;
import gd.infrastructure.log.LogMarker;
import gd.infrastructure.log.LogProducer;
import gd.domain.entities.repository.LuceneIndex;
import gd.infrastructure.steriotype.GDQuery;
import java.util.Collection;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

@GDQuery
public class QueryConceptEntriesByTypeAndQuery extends AbstractDatabaseCommand {

    @Autowired
    private LuceneIndex lucene;

    private static final Logger LOGGER = LogProducer.getLogger(QueryConceptEntriesByTypeAndQuery.class);

    public QueryListingResult<ConceptEntry> execute(ConceptType type, String query, QueryPagination pagination) {
        LOGGER.trace(LogMarker.DB, "Retrieving concept entries | {}, query={}, {}", type, query, pagination);

        // load ids from lucene
        Collection<ConceptEntry> entries = lucene.query(query, type.getUid(), pagination.getLimit());

        // convert
        return QueryListingResult.create(entries);
    }

}
