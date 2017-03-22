package gd.app.importer.task;

import gd.app.importer.model.Node;
import static gd.app.importer.task.ImportTask.INSTANCE_OR_SUBCLASS;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.plot.Location;
import gd.infrastructure.steriotype.GDTask;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.math.NumberUtils;

@GDTask
public class ImportLocation extends ImportTask {

    // =========================================================================
    // IDS
    // =========================================================================
    private static final String COUNTRY_ID = "Q6256";
    private static final String CONTINENT_ID = "Q5107";
    private static final String GEOGRAPHIC_REGION_ID = "Q82794";
    private static final String FICTIONAL_ID = "Q3895768";

    private static final String COUNTRY = "P17";
    private static final String CONTINENT = "P30";
    private static final String PART_OF = "P361";

    private static final String LOCATIONS = "video_games_set_in_(?!the_\\d+).*";

    // =========================================================================
    // DATA (HISTORICAL / REAL LOCATIONS - CITIES)
    // =========================================================================
    public static final Node ALEXANDRIA = Node.withPredefinedName(Location.class, "Alexandria");
    public static final Node ATHENS = Node.withPredefinedName(Location.class, "Athens");
    public static final Node PARIS = Node.withPredefinedName(Location.class, "Paris");
    public static final Node ROME = Node.withPredefinedName(Location.class, "Rome").checkName();
    public static final Node SPARTA = Node.withPredefinedName(Location.class, "Sparta");
    public static final Node VENICE = Node.withPredefinedName(Location.class, "Venice");

    // =========================================================================
    // DATA (HISTORICAL / REAL LOCATIONS - COUNTRIES)
    // =========================================================================
    public static final Node GREECE = Node.withPredefinedName(Location.class, "Greece");
    public static final Node ITALY = Node.withPredefinedName(Location.class, "Italy");

    // =========================================================================
    // DATA (MYTHOLOGICAL)
    // =========================================================================
    public static final Node ASGARD = Node.withPredefinedName(Location.class, "Asgard");
    public static final Node ATLANTIS = Node.withPredefinedName(Location.class, "Atlantis");
    public static final Node CAMELOT = Node.withPredefinedName(Location.class, "Camelot");
    public static final Node EL_DORADO = Node.withPredefinedName(Location.class, "El Dorado");
    public static final Node ELYSIAN_FIELDS = Node.withPredefinedName(Location.class, "Elysian Fields");
    public static final Node LEMURIA = Node.withPredefinedName(Location.class, "Lemuria");
    public static final Node SHAMBALA = Node.withPredefinedName(Location.class, "Shambala");
    public static final Node VALHALLA = Node.withPredefinedName(Location.class, "Valhalla");

    // =========================================================================
    // DATA (SIGHTSEEING)
    // =========================================================================
    public static final Node ALCATRAZ = Node.withPredefinedName(Location.class, "Alcatraz");
    public static final Node COLOSSEUM = Node.withPredefinedName(Location.class, "Colosseum");
    public static final Node EIFFEL_TOWER = Node.withPredefinedName(Location.class, "Eiffel Tower");
    public static final Node STONEHENGE = Node.withPredefinedName(Location.class, "Stonehenge");

    // =========================================================================
    // DATA (GENERIC LOCATIONS)
    // =========================================================================
    public static final Node ABANDONED_CITY = Node.withPredefinedName(Location.class, "Abandoned City");
    public static final Node AIRPORT = Node.withPredefinedName(Location.class, "Airport");
    public static final Node AMUSEMENT_PARK = Node.withPredefinedName(Location.class, "Amusement Park");
    public static final Node ASYLUM = Node.withPredefinedName(Location.class, "Asylum");
    public static final Node BEACH = Node.withPredefinedName(Location.class, "Beach");
    public static final Node BRIDGE = Node.withPredefinedName(Location.class, "Bridge");
    public static final Node CASINO = Node.withPredefinedName(Location.class, "Casino");
    public static final Node CASTLE = Node.withPredefinedName(Location.class, "Castle");
    public static final Node CAVERN = Node.withPredefinedName(Location.class, "Cavern", "cavern", "cave");
    public static final Node CHURCH = Node.withPredefinedName(Location.class, "Church");
    public static final Node CIRCUIT = Node.withPredefinedName(Location.class, "Circuit");
    public static final Node CITADEL = Node.withPredefinedName(Location.class, "Citadel");
    public static final Node CITY = Node.withPredefinedName(Location.class, "City");
    public static final Node COLLEGE = Node.withPredefinedName(Location.class, "College / School", "college", "school", "academy");
    public static final Node COAST = Node.withPredefinedName(Location.class, "Coast", "coastline", "seashore");
    public static final Node CONSTRUCTION_SITE = Node.withPredefinedName(Location.class, "Construction Site");
    public static final Node COURTYARD = Node.withPredefinedName(Location.class, "Courtyard");
    public static final Node DESERT = Node.withPredefinedName(Location.class, "Desert");
    public static final Node DUNGEON = Node.withPredefinedName(Location.class, "Dungeon");
    public static final Node EMPIRE = Node.withPredefinedName(Location.class, "Empire");
    public static final Node FACTORY = Node.withPredefinedName(Location.class, "Factory");
    public static final Node FARM = Node.withPredefinedName(Location.class, "Farm");
    public static final Node FOREST = Node.withPredefinedName(Location.class, "Forest");
    public static final Node FORTRESS = Node.withPredefinedName(Location.class, "Fortress");
    public static final Node GHOST_TOWN = Node.withPredefinedName(Location.class, "Ghost Town");
    public static final Node HARBOR = Node.withPredefinedName(Location.class, "Harbor");
    public static final Node HEAVEN = Node.withPredefinedName(Location.class, "Heaven");
    public static final Node HIGH_SCHOOL = Node.withPredefinedName(Location.class, "High School");
    public static final Node HIGHWAY = Node.withPredefinedName(Location.class, "Highway");
    public static final Node HOSPITAL = Node.withPredefinedName(Location.class, "Hospital");
    public static final Node HOTEL = Node.withPredefinedName(Location.class, "Hotel");
    public static final Node ISLAND = Node.withPredefinedName(Location.class, "Island");
    public static final Node JUNGLE = Node.withPredefinedName(Location.class, "Jungle");
    public static final Node JUNKYARD = Node.withPredefinedName(Location.class, "Junkyard");
    public static final Node LABORATORY = Node.withPredefinedName(Location.class, "Laboratory", "laboratory", "lab");
    public static final Node KINGDOM = Node.withPredefinedName(Location.class, "Kingdom");
    public static final Node MANSION = Node.withPredefinedName(Location.class, "Mansion");
    public static final Node MARS = Node.withPredefinedName(Location.class, "Mars").boostRelevance();
    public static final Node MAZE = Node.withPredefinedName(Location.class, "Maze");
    public static final Node MILKY_WAY = Node.withPredefinedName(Location.class, "Milky Way");
    public static final Node MINE = Node.withPredefinedName(Location.class, "Mine");
    public static final Node MOON = Node.withPredefinedName(Location.class, "Moon");
    public static final Node MOUNTAIN = Node.withPredefinedName(Location.class, "Mountain", "mountain", "mount");
    public static final Node NIGHTCLUB = Node.withPredefinedName(Location.class, "Nightclub");
    public static final Node OCEAN = Node.withPredefinedName(Location.class, "Ocean", "ocean", "sea");
    public static final Node OTHER_PLANET = Node.withPredefinedName(Location.class, "Other Planet / Distant World", "planet", "other planet", "distant world").boostRelevance();
    public static final Node OUTERSPACE = Node.withPredefinedName(Location.class, "Outer Space");
    public static final Node PLAINS = Node.withPredefinedName(Location.class, "Plains");
    public static final Node PARALLEL_UNIVERSE = Node.withPredefinedName(Location.class, "Parallel Universe");
    public static final Node POLICE_STATION = Node.withPredefinedName(Location.class, "Police Station");
    public static final Node PURGATORY = Node.withPredefinedName(Location.class, "Purgatory");
    public static final Node PRISON = Node.withPredefinedName(Location.class, "Prison");
    public static final Node PYRAMID = Node.withPredefinedName(Location.class, "Pyramid");
    public static final Node RAILWAY = Node.withPredefinedName(Location.class, "Railway");
    public static final Node ROAD = Node.withPredefinedName(Location.class, "Road");
    public static final Node ROOFTOP = Node.withPredefinedName(Location.class, "Rooftop");
    public static final Node RIVER = Node.withPredefinedName(Location.class, "River");
    public static final Node RUINS = Node.withPredefinedName(Location.class, "Ruins");
    public static final Node SEWER = Node.withPredefinedName(Location.class, "Sewer");
    public static final Node SLUM = Node.withPredefinedName(Location.class, "Slum / Favela", "slum", "favela");
    public static final Node SNOW = Node.withPredefinedName(Location.class, "Snow");
    public static final Node SPACE_STATION = Node.withPredefinedName(Location.class, "Space Station");
    public static final Node STADIUM = Node.withPredefinedName(Location.class, "Stadium");
    public static final Node SUBWAY = Node.withPredefinedName(Location.class, "Subway");
    public static final Node TEMPLE = Node.withPredefinedName(Location.class, "Temple", "temple", "chapel", "cathedral", "shrine");
    public static final Node TOMB = Node.withPredefinedName(Location.class, "Tomb");
    public static final Node TOWER = Node.withPredefinedName(Location.class, "Tower");
    public static final Node UNDERWATER = Node.withPredefinedName(Location.class, "Underwater");
    public static final Node VALLEY = Node.withPredefinedName(Location.class, "Valley");
    public static final Node VENUS = Node.withPredefinedName(Location.class, "Venus");
    public static final Node VILLAGE = Node.withPredefinedName(Location.class, "Village / Town", "village", "town");
    public static final Node WASTELAND = Node.withPredefinedName(Location.class, "Wasteland");
    public static final Node ZOO = Node.withPredefinedName(Location.class, "Zoo").checkName();

    // =========================================================================
    // INTERFACE
    // =========================================================================
    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return Location.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        // wikidata
        Set<Node> locations = jdbc.query()
                .attributesKeys(INSTANCE_OR_SUBCLASS)
                .attributesValues(COUNTRY_ID, CONTINENT_ID, GEOGRAPHIC_REGION_ID, FICTIONAL_ID)
                .findWikidataEntities();

        // dbpedia
        locations.addAll(jdbc.query()
                .regex(LOCATIONS)
                .findDBPediaCategories());
        locations = locations.stream().filter(location -> !location.getId().contains("_by_")).collect(Collectors.toSet());

        // predefined
        locations.addAll(getPredefinedNodes());

        // ids
        fixIds(locations, "video_games_set_in_");

        // remove years
        locations.removeIf(node -> NumberUtils.isNumber(node.getName()));

        return locations;
    }

    @Override
    public ConceptEntry getRelationshipsToCreate(Node node) {
        // basic data
        Location location = new Location();
        location.setUid(node.getGeneratedId());

        // parent locations
        location.setParentLocations(cache.findEntriesByWikidataIds(Location.class, node.getWikidataAttributes(COUNTRY, CONTINENT, PART_OF)));

        return location;
    }

}
