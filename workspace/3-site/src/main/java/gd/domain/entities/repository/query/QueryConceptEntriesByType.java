package gd.domain.entities.repository.query;

import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.ConceptType;
import gd.domain.entities.entity.industry.Game;
import gd.domain.entities.entity.industry.Platform;
import gd.domain.entities.repository.LuceneIndex;
import gd.infrastructure.database.QueryPagination;
import gd.infrastructure.database.AbstractDatabaseCommand;
import gd.infrastructure.database.QueryListingResult;
import gd.infrastructure.log.LogMarker;
import gd.infrastructure.log.LogProducer;
import gd.infrastructure.steriotype.GDQuery;
import java.util.Collection;
import java.util.Collections;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

@GDQuery
public class QueryConceptEntriesByType extends AbstractDatabaseCommand {

    @Autowired
    private LuceneIndex lucene;

    private static final Logger LOGGER = LogProducer.getLogger(QueryConceptEntriesByType.class);

    public QueryListingResult<ConceptEntry> execute(ConceptType type, QueryPagination pagination) {
        LOGGER.trace(LogMarker.DB, "Retrieving concept entries | {}, type={}, {}", type, pagination);

        // load ids from lucene
        Collection<ConceptEntry> entries = lucene.query("", type.getUid(), pagination.getLimit());

        // convert
        return QueryListingResult.create(entries);
    }

}
