package gd.domain.entities.repository.cache;

import com.google.common.collect.Sets;
import gd.domain.entities.converter.ConceptTypeConverter;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.ConceptType;
import gd.domain.shared.GD;
import gd.infrastructure.log.LogMarker;
import gd.infrastructure.log.LogProducer;
import gd.infrastructure.reflection.Reflection;
import gd.infrastructure.steriotype.GDCache;
import java.util.Collection;
import java.util.SortedSet;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

@GDCache
public class ConceptTypeCache {

    private SortedSet<ConceptType> conceptTypes;
    private static final Logger LOGGER = LogProducer.getLogger(ConceptTypeCache.class);

    // =========================================================================
    // SERVICES
    // =========================================================================
    @Autowired
    private Reflection reflection;

    @Autowired
    private ConceptTypeConverter converter;

    // =========================================================================
    // INITIALATION
    // =========================================================================
    @PostConstruct
    public void init() {
        LOGGER.debug(LogMarker.INIT, "Initializing concept types cache");

        // get classes
        Collection<Class<? extends ConceptEntry>> conceptClasses = reflection.getClasses(GD.GRAPH_ENTITIES_PACKAGE, ConceptEntry.class);
        conceptClasses.add(ConceptEntry.class);

        // convert
        Collection<ConceptType> types = converter.fromClasses(conceptClasses);

        // store
        conceptTypes = Sets.newTreeSet(types);
    }

    // =========================================================================
    // GETTERS
    // =========================================================================
    public SortedSet<ConceptType> getConceptTypes() {
        return conceptTypes;
    }
}
