package gd.app.graphgenerator.task;

import gd.domain.entities.Node;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.industry.ContentClassification;
import gd.infrastructure.steriotype.GDTask;
import java.util.Collection;

@GDTask
public class ImportContentClassification extends ImportTask {

    // =========================================================================
    // PREDEFINED
    // =========================================================================
    public static final Node EARLY_CHILDHOOD = Node.withPredefinedId(ContentClassification.class, "early-childhood");
    public static final Node EVERYONE = Node.withPredefinedId(ContentClassification.class, "everyone");
    public static final Node EVERYONE_10 = Node.withPredefinedId(ContentClassification.class, "everyone-10");
    public static final Node TEEN = Node.withPredefinedId(ContentClassification.class, "teen");
    public static final Node MATURE = Node.withPredefinedId(ContentClassification.class, "mature-17");
    public static final Node ADULTS_ONLY = Node.withPredefinedId(ContentClassification.class, "adults-only-18");

    // =========================================================================
    // WIKIDATA
    // =========================================================================
    private static final String ESRB = "Q23683568";
    private static final String PEGI = "Q23686802";

    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return ContentClassification.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        return jdbc.query()
                .attributesKeys(INSTANCE_OR_SUBCLASS)
                .attributesValues(ESRB, PEGI)
                .findWikidataEntities();
    }

}
