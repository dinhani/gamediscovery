package gd.app.importer.task;

import gd.app.importer.model.Node;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.plot.Soundtrack;
import gd.infrastructure.steriotype.GDTask;
import java.util.Collection;

@GDTask
public class ImportSoundtrack extends ImportTask {

    // categories
    public static final Node HEAVY_METAL = Node.withPredefinedName(Soundtrack.class, "Heavy Metal");
    public static final Node HIPHOP = Node.withPredefinedName(Soundtrack.class, "Rap / Hip-hop", "rap", "rapper", "hip-hop");
    public static final Node ROCK = Node.withPredefinedName(Soundtrack.class, "Rock and Roll", "rock music", "hard rock");
    public static final Node ORCHESTRAL = Node.withPredefinedName(Soundtrack.class, "Orchestral", "orchestral", "orchestra");

    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return Soundtrack.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        return getPredefinedNodes();
    }

}
