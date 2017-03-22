package gd.app.importer.task;

import gd.app.importer.model.Node;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.industry.Game;
import gd.domain.entities.entity.industry.Series;
import gd.infrastructure.steriotype.GDTask;
import java.util.Collection;

@GDTask
public class ImportSeries extends ImportTask {

    // WIKIDATA
    private static final String SERIES_ID = "Q7058673";
    private static final String MEDIA_FRANCHISE_ID = "Q196600";
    private static final String HAS_PART_ID = "P527";

    // DBPEDIA
    private static final String VG_FRANCHISES = "video_game_franchises";

    // =========================================================================
    // DATA (GENERAL)
    // =========================================================================
    public static final Node _18_WHEELS = Node.withPredefinedName(Series.class, "18 Wheels").checkName().noKeywords();
    public static final Node ACE_COMBAT = Node.withPredefinedName(Series.class, "Ace Combat").checkName().noKeywords();
    public static final Node ASSASSINS_CREED = Node.withPredefinedName(Series.class, "Assassin's Creed").checkName().noKeywords();
    public static final Node CALL_OF_DUTY = Node.withPredefinedName(Series.class, "Call of Duty").checkName().noKeywords();
    public static final Node FINAL_FANTASY = Node.withPredefinedName(Series.class, "Final Fantasy").checkName().noKeywords();
    public static final Node GRAN_TURISMO = Node.withPredefinedName(Series.class, "Gran Turismo").checkName().noKeywords();
    public static final Node GUITAR_HERO = Node.withPredefinedName(Series.class, "Guitar Hero").checkName().noKeywords();
    public static final Node KIRBY = Node.withPredefinedName(Series.class, "Kirby").checkName().noKeywords();
    public static final Node MASS_EFFECT = Node.withPredefinedName(Series.class, "Mass Effect").checkName().noKeywords();
    public static final Node MEDAL_OF_HONOR = Node.withPredefinedName(Series.class, "Medal of Honor").checkName().noKeywords();
    public static final Node MEGA_MAN = Node.withPredefinedName(Series.class, "Mega Man").checkName().noKeywords();
    public static final Node NEED_FOR_SPEED = Node.withPredefinedName(Series.class, "Need for Speed").checkName().noKeywords();
    public static final Node RESIDENT_EVIL = Node.withPredefinedName(Series.class, "Resident Evil").checkName().noKeywords();
    public static final Node SILENT_HILL = Node.withPredefinedName(Series.class, "Silent Hill").checkName().noKeywords();
    public static final Node STREET_FIGHTER = Node.withPredefinedName(Series.class, "Street Fighter").checkName().noKeywords();
    public static final Node SUPER_MARIO = Node.withPredefinedName(Series.class, "Super Mario").checkName().noKeywords();
    public static final Node TOTAL_WAR = Node.withPredefinedName(Series.class, "Total War").checkName().noKeywords();
    public static final Node TYCOON = Node.withPredefinedName(Series.class, "Tycoon").checkName().noKeywords();
    public static final Node WORMS = Node.withPredefinedName(Series.class, "Worms").checkName().noKeywords();

    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return Series.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        // wikidata (series)
        Collection<Node> series = jdbc.query()
                .attributesKeys(INSTANCE_OR_SUBCLASS)
                .attributesValues(SERIES_ID, MEDIA_FRANCHISE_ID)
                .findWikidataEntities();

        // wikidata (games as series)
        series.addAll(jdbc.query()
                .attributesKeys(INSTANCE_OR_SUBCLASS)
                .attributesValues(ImportGame.GAME_ID)
                .findWikidataEntities());

        // dbpedia
        series.addAll(jdbc.query()
                .categories(VG_FRANCHISES)
                .findDBPediaCategories());

        // predefined
        series.addAll(getPredefinedNodes());

        // alternative id
        fixIds(series, "_series");

        return series;
    }

    @Override
    public ConceptEntry getRelationshipsToCreate(Node node) {
        Series series = new Series();
        series.setUid(node.getGeneratedId());

        // games
        series.setGames(cache.findEntriesByWikidataIds(Game.class, node.getWikidataAttributes(HAS_PART_ID)));

        return series;
    }

}
