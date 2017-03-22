package gd.app.importer.task;

import gd.app.importer.model.Node;
import static gd.app.importer.task.ImportTask.INSTANCE_OR_SUBCLASS;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.industry.Game;
import gd.infrastructure.steriotype.GDTask;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@GDTask
public class ImportCharacter extends ImportTask {

    // =========================================================================
    // IDS
    // =========================================================================
    // wikidata
    private static final String GAME_CHARACTER_ID = "Q1569167";
    private static final String FICTIONAL_CHARACTER_ID = "Q95074";
    private static final String FICTIONAL_HUMAN_ID = "Q15632617";

    private static final String PRESENT_IN_WORK = "P1441";

    // igdb
    private static final String GAMES = "games";

    // =========================================================================
    // DATA (DISNEY)
    // =========================================================================
    public static final Node DONALD_DUCK = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Donald Duck");
    public static final Node MICKEY = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Mickey");
    public static final Node PINOCCHIO = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Pinocchio");

    // =========================================================================
    // DATA (HEROS)
    // =========================================================================
    public static final Node BATMAN = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Batman").withMoreOcurrences().checkName();
    public static final Node CAPTAIN_AMERICA = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Captain America");
    public static final Node CATWOMAN = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Catwoman");
    public static final Node DEADPOOL = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Deadpool");
    public static final Node GOKU = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Goku");
    public static final Node IRON_MAN = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Iron Man");
    public static final Node JOKER = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Joker");
    public static final Node ROBIN = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Robin");
    public static final Node SPIDER_MAN = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Spider-Man").checkName();
    public static final Node SUPER_MAN = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Superman").checkName();
    public static final Node VENOM = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Venom");
    public static final Node WOLVERINE = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Wolverine").checkName();

    // =========================================================================
    // DATA (GENERAL)
    // =========================================================================
    public static final Node GORDON_FREEMAN = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Gordon Freeman");
    public static final Node KRATOS = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Kratos");
    public static final Node LARA_CROFT = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Lara Croft");
    public static final Node MASTER_CHIEF = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Master Chief");
    public static final Node PAC_MAN = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Pac-Man").withMoreOcurrences();
    public static final Node SONIC = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Sonic").withMoreOcurrences();
    public static final Node YOSHI = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Yoshi");

    // =========================================================================
    // DATA (HISTORICAL)
    // =========================================================================
    public static final Node ALBERT_EINSTEIN = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Albert Einstein", "einstein");
    public static final Node ARCHIMEDES = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Archimedes");
    public static final Node DANTE = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Dante Alighieri", "dante alighieri", "alighieri");
    public static final Node HITLER = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Hitler");
    public static final Node JULIUS_CAESAR = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Julius Caesar");
    public static final Node LEONIDAS = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Leonidas");
    public static final Node SOCRATES = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Socrates");
    public static final Node TESLA = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Tesla").withMoreOcurrences();

    // =========================================================================
    // DATA (GODS)
    // =========================================================================
    public static final Node ANUBIS = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Anubis");
    public static final Node APHRODITE = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Aphrodite");
    public static final Node APOLLO = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Apollo");
    public static final Node ARES = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Ares");
    public static final Node ARTEMIS = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Artemis");
    public static final Node ATHENA = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Athena");
    public static final Node CRONOS = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Cronos");
    public static final Node HADES = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Hades");
    public static final Node HELIOS = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Helios");
    public static final Node HERCULES = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Hercules");
    public static final Node ODIN = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Odin");
    public static final Node OSIRIS = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Osiris");
    public static final Node POSEIDON = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Poseidon");
    public static final Node THOR = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Thor");
    public static final Node ZEUS = Node.withPredefinedName(gd.domain.entities.entity.plot.Character.class, "Zeus");

    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return gd.domain.entities.entity.plot.Character.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        // wikidata
        Set<Node> characters = jdbc.query()
                .attributesKeys(INSTANCE_OR_SUBCLASS)
                .attributesValues(GAME_CHARACTER_ID, FICTIONAL_CHARACTER_ID, FICTIONAL_HUMAN_ID)
                .findWikidataEntities();

        // igdb
        //characters.addAll(jdbc.executeQuery("SELECT id, name, attributes::text as igdbAttributes FROM igdb_characters"));
        // predefined
        characters.addAll(getPredefinedNodes());

        // remove some
        characters = characters.stream()
                .filter(character -> !character.getName().contains("Player"))
                .filter(character -> !character.getName().contains("Pedestrian"))
                .filter(character -> !character.getName().contains("Narrator"))
                .filter(character -> !character.getName().contains("Announcer"))
                .filter(character -> !character.getName().contains("Police"))
                .collect(Collectors.toSet());

        return characters;
    }

    @Override
    public ConceptEntry getRelationshipsToCreate(Node node) {
        gd.domain.entities.entity.plot.Character character = new gd.domain.entities.entity.plot.Character();
        character.setUid(node.getGeneratedId());

        // games
        character.setGames(cache.findEntriesByWikidataIds(Game.class, node.getWikidataAttributes(PRESENT_IN_WORK)));

        // races
        importNodes(character.getRaces(), ImportCreature.class, node);

        return character;
    }

}
