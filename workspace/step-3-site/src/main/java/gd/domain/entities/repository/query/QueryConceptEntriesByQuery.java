package gd.domain.entities.repository.query;

import gd.domain.entities.entity.ConceptEntry;
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
public class QueryConceptEntriesByQuery extends AbstractDatabaseCommand {

    private static final Logger LOGGER = LogProducer.getLogger(QueryConceptEntriesByQuery.class);

    @Autowired
    private LuceneIndex lucene;

    public QueryListingResult<ConceptEntry> execute(String query, QueryPagination pagination) {
        LOGGER.trace(LogMarker.DB, "Retrieving concept entries | query={}, {}", query, pagination);

        // load entries from lucene
        Collection<ConceptEntry> entries = lucene.query(query, pagination.getLimit());

        // convert
        return QueryListingResult.create(entries);
    }

}
