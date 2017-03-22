package gd.app.graphgenerator.task;

import gd.domain.entities.Node;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.industry.Company;
import gd.infrastructure.steriotype.GDTask;
import java.util.Collection;

@GDTask
public class ImportCompany extends ImportTask {

    // =========================================================================
    // DATA (EXISTING)
    // =========================================================================
    public static final Node _2K = Node.withPredefinedId(Company.class, "2k-games");
    public static final Node ROCKSTAR = Node.withPredefinedId(Company.class, "rockstar-games");
    public static final Node UBISOFT = Node.withPredefinedId(Company.class, "ubisoft");

    // =========================================================================
    // DATA (IDS)
    // =========================================================================    
    private static final String PUBLISHER = "Q1137109";
    private static final String DEVELOPER = "Q210167";
    private static final String GAME_INDUSTRY = "Q941594";

    private static final String INDUSTRY = "P452";

    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return Company.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        return jdbc.query()
                .attributesKeys(INSTANCE_OF, SUBCLASS_OF, INDUSTRY)
                .attributesValues(PUBLISHER, DEVELOPER, GAME_INDUSTRY)
                .findWikidataEntities();
    }

}
