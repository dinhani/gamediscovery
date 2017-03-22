package gd.app.importer.task;

import gd.app.importer.model.Node;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.plot.Atmosphere;
import gd.infrastructure.steriotype.GDTask;
import java.util.Collection;

@GDTask
public class ImportAtmosphere extends ImportTask {

    public static final Node BEAUTIFUL = Node.withPredefinedName(Atmosphere.class, "Beautiful");
    public static final Node BIZARRE = Node.withPredefinedName(Atmosphere.class, "Bizarre");
    public static final Node DISGUSTING = Node.withPredefinedName(Atmosphere.class, "Disgusting");
    public static final Node DISTURBING = Node.withPredefinedName(Atmosphere.class, "Disturbing");
    public static final Node EPIC = Node.withPredefinedName(Atmosphere.class, "Epic");
    public static final Node GOTHIC = Node.withPredefinedName(Atmosphere.class, "Gothic");
    public static final Node MELANCHOLIC = Node.withPredefinedName(Atmosphere.class, "Melancholic");
    public static final Node MESMERIZING = Node.withPredefinedName(Atmosphere.class, "Mesmerizing");
    public static final Node NOIR = Node.withPredefinedName(Atmosphere.class, "Noir");
    public static final Node SCARY = Node.withPredefinedName(Atmosphere.class, "Scary", "scary", "fear");
    public static final Node STYLISH = Node.withPredefinedName(Atmosphere.class, "Stylish");
    public static final Node TENSE = Node.withPredefinedName(Atmosphere.class, "Tense");
    public static final Node THRILLING = Node.withPredefinedName(Atmosphere.class, "Thrilling", "thrilling", "thrill");
    public static final Node VIOLENT = Node.withPredefinedName(Atmosphere.class, "Violent", "violence");

    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return Atmosphere.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        return getPredefinedNodes();
    }

}
