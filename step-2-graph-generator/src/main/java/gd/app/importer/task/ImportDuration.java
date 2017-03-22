package gd.app.importer.task;

import gd.app.importer.model.Node;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.gameplay.Duration;
import gd.infrastructure.steriotype.GDTask;
import java.util.Collection;

@GDTask
public class ImportDuration extends ImportTask {

    public static final Node DURATION_QUICK = Node.withPredefinedIdAndName(Duration.class, "really-short", "Really Short (less than 4 hours)");
    public static final Node DURATION_SHORT = Node.withPredefinedIdAndName(Duration.class, "short", "Short (between 4 and 10 hours)");
    public static final Node DURATION_AVERAGE = Node.withPredefinedIdAndName(Duration.class, "average", "Average (between 10 and 20 hours)");
    public static final Node DURATION_LONG = Node.withPredefinedIdAndName(Duration.class, "long", "Long (more than 20 hours)");

    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return Duration.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        return getPredefinedNodes();
    }

}
