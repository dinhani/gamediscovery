package gd.app.importer.task;

import gd.app.importer.model.Node;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.industry.Award;
import gd.infrastructure.steriotype.GDTask;
import java.util.Collection;

@GDTask
public class ImportAward extends ImportTask {

    public static final Node GAME_OF_THE_YEAR = Node.withPredefinedName(Award.class, "Game of the Year (GOTY)");

    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return Award.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        return getPredefinedNodes();
    }

}
