package gd.app.graphgenerator.task;

import gd.domain.entities.Node;
import static gd.app.graphgenerator.task.ImportTask.INSTANCE_OR_SUBCLASS;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.industry.Media;
import gd.infrastructure.steriotype.GDTask;
import java.util.Collection;
import java.util.Set;

@GDTask
public class ImportMedia extends ImportTask {

    private static final String OPTICAL_DISC = "Q234870";
    private static final String COMPACT_DISC = "Q34467";

    private static final String VIDEO_GAME_DISTRIBUTION = "\"video_game_distribution\"";

    // =========================================================================
    // DATA
    // =========================================================================
    public static final Node CARTDRIGE = Node.withPredefinedName(Media.class, "Cartdrige");
    public static final Node CD = Node.withPredefinedName(Media.class, "CD");
    public static final Node DVD = Node.withPredefinedName(Media.class, "DVD");
    public static final Node UMD = Node.withPredefinedName(Media.class, "UMD");
    public static final Node DIGITAL = Node.withPredefinedName(Media.class, "Digital Distribution");

    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return Media.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        // wikidata
        Set<Node> medias = jdbc.query()
                .attributesKeys(INSTANCE_OR_SUBCLASS)
                .attributesValues(OPTICAL_DISC, COMPACT_DISC)
                .findWikidataEntities();

        // dbpedia
        medias.addAll(jdbc.query()
                .categories(VIDEO_GAME_DISTRIBUTION)
                .findDBPediaArticles());

        // predefined
        medias.addAll(getPredefinedNodes());

        return medias;
    }

}
