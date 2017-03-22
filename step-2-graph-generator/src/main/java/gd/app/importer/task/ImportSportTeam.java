package gd.app.importer.task;

import gd.app.importer.model.Node;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.plot.SportTeam;
import gd.infrastructure.steriotype.GDTask;
import java.util.Collection;

@GDTask
public class ImportSportTeam extends ImportTask {

    // =========================================================================
    // BASKETBALL
    // =========================================================================
    public static final Node CHICAGO_BULLS = Node.withPredefinedName(SportTeam.class, "Chicago Bulls");

    // =========================================================================
    // SOCCER
    // =========================================================================
    public static final Node CRUZEIRO = Node.withPredefinedName(SportTeam.class, "Cruzeiro");
    public static final Node CORINTHIANS = Node.withPredefinedName(SportTeam.class, "Corinthians");
    public static final Node PALMEIRAS = Node.withPredefinedName(SportTeam.class, "Palmeiras");
    public static final Node REAL_MADRID = Node.withPredefinedName(SportTeam.class, "Real Madrid");

    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return SportTeam.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        return getPredefinedNodes();
    }

}
