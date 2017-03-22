package gd.app.graphgenerator.task;

import gd.domain.entities.Node;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.industry.Company;
import gd.domain.entities.entity.industry.Platform;
import gd.infrastructure.steriotype.GDTask;
import java.util.Collection;

@GDTask
public class ImportPlatform extends ImportTask {

    // =========================================================================
    // DATA (EXISTING)
    // =========================================================================
    public static final Node MAC = Node.withPredefinedIdAndName(Platform.class, "mac-os", "Mac OS");
    public static final Node WINDOWS = Node.withPredefinedIdAndName(Platform.class, "microsoft-windows", "Microsoft Windows");

    // =========================================================================
    // IDS
    // =========================================================================    
    private static final String CONSOLE_ID = "Q8076";
    private static final String HANDHELD_ID = "Q941818";
    private static final String MOBILE_ID = "Q920890";
    private static final String PORTABLE_PLAYER_ID = "Q10872571";
    private static final String OPERATING_SYSTEM_ID = "Q9135";
    private static final String VIRTUAL_MACHINE_ID = "Q192726";
    private static final String COMPUTER_ID = "Q68";
    private static final String COMPUTING_PLATFORM_ID = "Q241317";
    private static final String HOME_COMPUTER_ID = "Q473708";
    private static final String PERSONAL_COMPUTER_ID = "Q16338";

    private static final String MANUFACTURER = "P176";
    private static final String DEVELOPER = "P178";

    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return Platform.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        Collection<Node> platforms = jdbc.query()
                .attributesKeys(INSTANCE_OR_SUBCLASS)
                .attributesValues(CONSOLE_ID, HANDHELD_ID, MOBILE_ID, PORTABLE_PLAYER_ID, OPERATING_SYSTEM_ID, VIRTUAL_MACHINE_ID, COMPUTER_ID, HOME_COMPUTER_ID, PERSONAL_COMPUTER_ID, COMPUTING_PLATFORM_ID)
                .findWikidataEntities();

        // set alias
        for (Node platform : platforms) {
            setAliases(platform);
        }

        // remove
        platforms.removeIf(platform -> platform.getName().equals("Personal Computer"));

        return platforms;
    }

    private void setAliases(Node platform) {
        // windows
        if (platform.getName().contains("Windows") && !platform.getName().contains("Phone")) {
            platform.addAlias("PC");
            platform.addAlias("Personal Computer");
        }
        // android
        if (platform.getName().contains("Android") || platform.getName().contains("iOS") || platform.getName().contains("Phone")) {
            platform.addAlias("Mobile");
        }
        // nintendo
        if (platform.getName().equals("Nintendo 64")) {
            platform.addAlias("N64");
        }
        // playstation
        if (platform.getName().equals("PlayStation")) {
            platform.addAlias("PS1").addAlias("PSX");
        }
        if (platform.getName().equals("PlayStation 2")) {
            platform.addAlias("PS2");
        }
        if (platform.getName().equals("PlayStation 3")) {
            platform.addAlias("PS3");
        }
        if (platform.getName().equals("PlayStation 4")) {
            platform.addAlias("PS4");
        }
        // xbox
        if (platform.getName().equals("Xbox 360")) {
            platform.addAlias("X360");
        }
        if (platform.getName().equals("Xbox One")) {
            platform.addAlias("XONE");
        }
    }

    @Override
    public ConceptEntry getRelationshipsToCreate(Node node) {
        Platform platform = new Platform();
        platform.setUid(node.getGeneratedId());

        platform.setOwnerCompanies(cache.findEntriesByWikidataIds(Company.class, node.getWikidataAttributes(MANUFACTURER, DEVELOPER)));

        return platform;
    }
}
