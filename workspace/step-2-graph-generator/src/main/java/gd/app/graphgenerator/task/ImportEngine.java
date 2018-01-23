package gd.app.graphgenerator.task;

import gd.domain.entities.Node;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.gameplay.Engine;
import gd.infrastructure.steriotype.GDTask;
import java.util.Collection;

@GDTask
public class ImportEngine extends ImportTask {

    // =========================================================================
    // DATA
    // =========================================================================
    public static final Node UNREAL = Node.withPredefinedName(Engine.class, "Unreal Engine", "");
    public static final Node UNITY = Node.withPredefinedName(Engine.class, "Unity", "");

    // =========================================================================
    // IDS
    // =========================================================================
    private static final String GAME_ENGINE = "Q193564";

    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return Engine.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        return jdbc.query()
                .attributesKeys(INSTANCE_OR_SUBCLASS)
                .attributesValues(GAME_ENGINE)
                .findWikidataEntities();
    }

}
