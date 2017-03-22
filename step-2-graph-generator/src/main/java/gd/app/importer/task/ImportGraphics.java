package gd.app.importer.task;

import gd.app.importer.model.Node;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.gameplay.Graphics;
import gd.infrastructure.steriotype.GDTask;
import java.util.Collection;
import java.util.Set;

@GDTask
public class ImportGraphics extends ImportTask {

    private static final String GRAPHICS = ".*with_.+_graphics.*";

    public static final Node ANIME = Node.withPredefinedName(Graphics.class, "Anime", "anime").boostRelevance();
    public static final Node CEL_SHADED = Node.withPredefinedName(Graphics.class, "Cel-Shaded", "cel-shading", "cel shading").boostRelevance();
    public static final Node FIRST_PERSON = Node.withPredefinedName(Graphics.class, "Third Person", "third-person");
    public static final Node ISOMETRIC = Node.withPredefinedName(Graphics.class, "Isometric").boostRelevance();
    public static final Node THIRD_PERSON = Node.withPredefinedName(Graphics.class, "First Person", "first-person");
    public static final Node SIDE_VIEW = Node.withPredefinedName(Graphics.class, "Side View", "").boostRelevance();
    public static final Node TOP_DOWN = Node.withPredefinedName(Graphics.class, "Top Down", "").boostRelevance();

    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return Graphics.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        // dbpedia
        Set<Node> nodes = jdbc.query()
                .regex(GRAPHICS)
                .findDBPediaCategories();

        // predfined
        nodes.addAll(getPredefinedNodes());

        // fix ids
        fixIds(nodes, "video_games_with", "_graphics");

        // process names
        for (Node node : nodes) {
            node.setName(node.getName().replaceAll("With", "").replaceAll("Graphics", "").replaceAll("3d", "3D"));
        }

        // remove 2D and 3D
        nodes.removeIf(node -> node.getId().contains("2d") || node.getId().contains("3d"));

        return nodes;
    }

}
