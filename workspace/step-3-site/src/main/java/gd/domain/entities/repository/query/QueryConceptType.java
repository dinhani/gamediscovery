package gd.domain.entities.repository.query;

import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.ConceptType;
import gd.domain.entities.repository.cache.ConceptTypeCache;
import gd.infrastructure.log.LogProducer;
import gd.infrastructure.steriotype.GDQuery;
import java.util.Optional;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

@GDQuery
public class QueryConceptType {

    @Autowired
    private ConceptTypeCache cache;

    private static final Logger LOGGER = LogProducer.getLogger(QueryConceptType.class);

    public Optional<ConceptType> execute(Class<? extends ConceptEntry> clazz) {
        return execute(clazz.getSimpleName());
    }

    public Optional<ConceptType> execute(String typeUid) {
        return cache.getConceptTypes().stream().filter((t) -> t.getUid().equalsIgnoreCase(typeUid)).findFirst();
    }
}
