package gd.app.graphgenerator.task;

import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.industry.Store;
import gd.domain.entities.Node;
import gd.infrastructure.steriotype.GDTask;
import java.util.Collection;
import java.util.Set;

@GDTask
public class ImportStore extends ImportTask {

    private static final String DIGITAL_DISTRIBUTION_PLATFORM = "Q19307174";
    private static final String ONLINE_RETAILERS = "online-only_retailers_of_video_games";

    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return Store.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        Set<Node> stores = jdbc.query()
                .attributesKeys(INSTANCE_OR_SUBCLASS)
                .attributesValues(DIGITAL_DISTRIBUTION_PLATFORM)
                .findWikidataEntities();

        stores.addAll(jdbc.query()
                .categories(ONLINE_RETAILERS)
                .findDBPediaArticles());

        return stores;
    }

}
