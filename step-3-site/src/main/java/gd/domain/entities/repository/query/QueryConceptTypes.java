package gd.domain.entities.repository.query;

import com.google.common.collect.Sets;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.ConceptType;
import gd.domain.entities.repository.cache.ConceptTypeCache;
import gd.infrastructure.log.LogMarker;
import gd.infrastructure.log.LogProducer;
import gd.infrastructure.steriotype.GDQuery;
import java.util.Collection;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

@GDQuery
public class QueryConceptTypes {

    @Autowired
    private ConceptTypeCache cache;

    private static final Logger LOGGER = LogProducer.getLogger(QueryConceptTypes.class);

    public Collection<ConceptType> execute() {
        LOGGER.trace(LogMarker.DB, "Retrieving concept types");
        Collection<ConceptType> typesWithoutConceptEntryType = cache.getConceptTypes().stream().filter(c -> c.getTargetClass() != ConceptEntry.class).collect(Collectors.toSet());

        return Sets.newTreeSet(typesWithoutConceptEntryType);
    }
}
