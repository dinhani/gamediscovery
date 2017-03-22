package gd.app.graphgenerator.task;

import gd.domain.entities.Node;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.plot.Theme;
import gd.infrastructure.steriotype.GDTask;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@GDTask
public class ImportTheme extends ImportTask {

    private static final String THEMES = "video_games_by_theme";

    // PREDEFINED THEMES
    // =========================================================================
    // DATA (GENERAL)
    // =========================================================================
    public static final Node ABDUCTION = Node.withPredefinedName(Theme.class, "Abduction");
    public static final Node ADULTERY = Node.withPredefinedName(Theme.class, "Adultery");
    public static final Node ALIEN_INVASION = Node.withPredefinedName(Theme.class, "Alien Invasion");
    public static final Node ALTERNATE_REALITY = Node.withPredefinedName(Theme.class, "Alternate Reality");
    public static final Node ANTIHERO = Node.withPredefinedName(Theme.class, "Antihero");
    public static final Node ASSASSINATION = Node.withPredefinedName(Theme.class, "Assassination");
    public static final Node CIRCUIT_RACE = Node.withPredefinedName(Theme.class, "Circuit Race");
    public static final Node CONSPIRACY = Node.withPredefinedName(Theme.class, "Conspiracy");
    public static final Node COLD_WAR = Node.withPredefinedName(Theme.class, "Cold War");
    public static final Node CRIME = Node.withPredefinedName(Theme.class, "Crime");
    public static final Node CYBERPUNK = Node.withPredefinedName(Theme.class, "Cyberpunk");
    public static final Node DISASTER = Node.withPredefinedName(Theme.class, "Disaster");
    public static final Node DRUGS = Node.withPredefinedName(Theme.class, "Drugs", "drug");
    public static final Node FRIENDSHIP = Node.withPredefinedName(Theme.class, "Friendship");
    public static final Node GANGS = Node.withPredefinedName(Theme.class, "Gangs", "gangs", "gang");
    public static final Node GANGSTER = Node.withPredefinedName(Theme.class, "Gangster");
    public static final Node GUERRILLA = Node.withPredefinedName(Theme.class, "Guerrilla");
    public static final Node HIPPIE = Node.withPredefinedName(Theme.class, "Hippie");
    public static final Node INVASION = Node.withPredefinedName(Theme.class, "Invasion");
    public static final Node INVESTIGATION = Node.withPredefinedName(Theme.class, "Investigation");
    public static final Node INSURRECTION = Node.withPredefinedName(Theme.class, "Insurrection");
    public static final Node KIDNAPPING = Node.withPredefinedName(Theme.class, "Kidnapping");
    public static final Node MAFIA = Node.withPredefinedName(Theme.class, "Mafia");
    public static final Node MEDIEVAL = Node.withPredefinedName(Theme.class, "Medieval");
    public static final Node MYSTERY = Node.withPredefinedName(Theme.class, "Mystery");
    public static final Node RACISM = Node.withPredefinedName(Theme.class, "Racism");
    public static final Node REVENGE = Node.withPredefinedName(Theme.class, "Revenge", "revenge", "vengeance");
    public static final Node RESCUE = Node.withPredefinedName(Theme.class, "Rescue");
    public static final Node SACRIFICE = Node.withPredefinedName(Theme.class, "Sacrifice");
    public static final Node SNIPER = Node.withPredefinedName(Theme.class, "Sniper").checkName().withMoreOcurrences();
    public static final Node SUPERHERO = Node.withPredefinedName(Theme.class, "Superhero");
    public static final Node STEAMPUNK = Node.withPredefinedName(Theme.class, "Steampunk");
    public static final Node STREET_RACE = Node.withPredefinedName(Theme.class, "Street Race");
    public static final Node STUDENTS = Node.withPredefinedName(Theme.class, "Students", "student");
    public static final Node TERRORISM = Node.withPredefinedName(Theme.class, "Terrorism", "terrorism", "terrorist");
    public static final Node TREASURE = Node.withPredefinedName(Theme.class, "Treasure");
    public static final Node TREASON = Node.withPredefinedName(Theme.class, "Treason");
    public static final Node TYRANNY = Node.withPredefinedName(Theme.class, "Tyranny");
    public static final Node WAR = Node.withPredefinedName(Theme.class, "War");
    public static final Node WORLD_WAR_I = Node.withPredefinedName(Theme.class, "World War I");
    public static final Node WORLD_WAR_II = Node.withPredefinedName(Theme.class, "World War II");
    public static final Node ZOO = Node.withPredefinedName(Theme.class, "Zoo").checkName().noKeywords(); // is a theme only if in the name, else it is just a location

    // =========================================================================
    // DATA (SPORTS)
    // =========================================================================
    public static final Node BASEBALL = Node.withPredefinedName(Theme.class, "Baseball", "").checkName(); // do not search because of "baseball bat"
    public static final Node BASKETBALL = Node.withPredefinedName(Theme.class, "Basketball", "basketball", "nba").checkName();
    public static final Node BMX = Node.withPredefinedName(Theme.class, "BMX").checkName();
    public static final Node BOXING = Node.withPredefinedName(Theme.class, "Boxing", "boxing", "boxe").checkName();
    public static final Node BOWLING = Node.withPredefinedName(Theme.class, "Bowling").checkName();
    public static final Node CHESS = Node.withPredefinedName(Theme.class, "Chess").checkName();
    public static final Node CRICKET = Node.withPredefinedName(Theme.class, "Cricket").checkName();
    public static final Node FISHING = Node.withPredefinedName(Theme.class, "Fishing").checkName();
    public static final Node FOOTBALL = Node.withPredefinedName(Theme.class, "Football", "football", "nfl");
    public static final Node GOLF = Node.withPredefinedName(Theme.class, "Golf").checkName();
    public static final Node HOCKEY = Node.withPredefinedName(Theme.class, "Hockey", "hockey", "nhl").checkName();
    public static final Node HUNTING = Node.withPredefinedName(Theme.class, "Hunting");
    public static final Node OFF_ROAD = Node.withPredefinedName(Theme.class, "Off-Road", "off-road", "offroad");
    public static final Node PINBALL = Node.withPredefinedName(Theme.class, "Pinball").checkName();
    public static final Node POKER = Node.withPredefinedName(Theme.class, "Poker").checkName();
    public static final Node RALLY = Node.withPredefinedName(Theme.class, "Rally").checkName();
    public static final Node RUGBY = Node.withPredefinedName(Theme.class, "Rugby");
    public static final Node SAILING = Node.withPredefinedName(Theme.class, "Sailing / Boating");
    public static final Node SKATE = Node.withPredefinedName(Theme.class, "Skateboarding");
    public static final Node SOCCER = Node.withPredefinedName(Theme.class, "Soccer").checkName();
    public static final Node SNOWBOARDING = Node.withPredefinedName(Theme.class, "Snowboarding", "snowboarding", "snowboard");
    public static final Node SURFING = Node.withPredefinedName(Theme.class, "Surfing", "surfing", "surg");
    public static final Node TENNIS = Node.withPredefinedName(Theme.class, "Tennis").checkName();
    public static final Node VOLLEY = Node.withPredefinedName(Theme.class, "Volleyball").checkName();
    public static final Node WAKEBOARD = Node.withPredefinedName(Theme.class, "Wakeboarding", "wakeboarding", "wakeboard");
    public static final Node WRESTLING = Node.withPredefinedName(Theme.class, "Wrestling / Fighting", "wrestling", "wwe");

    // =========================================================================
    // DATA (SPORTS)
    // =========================================================================
    public static final Node NBA = Node.withPredefinedName(Theme.class, "NBA").checkName();
    public static final Node NFL = Node.withPredefinedName(Theme.class, "NFL").checkName();
    public static final Node NHL = Node.withPredefinedName(Theme.class, "NHL").checkName();
    public static final Node PGA = Node.withPredefinedName(Theme.class, "PGA").checkName();

    // =========================================================================
    // DATA (SPORTS EVENTS)
    // =========================================================================
    public static final Node OLYMPIAD = Node.withPredefinedName(Theme.class, "Olympics", "olympiad", "olympics").checkName();
    public static final Node WORLD_CUP = Node.withPredefinedName(Theme.class, "World Cup", "world cup").checkName();

    // =========================================================================
    // DATA (POPULAR FRANCHISES)
    // =========================================================================
    public static final Node DC_COMICS = Node.withPredefinedName(Theme.class, "DC Comics");
    public static final Node DIGIMON = Node.withPredefinedName(Theme.class, "Digimon").checkName();
    public static final Node DISNEY = Node.withPredefinedName(Theme.class, "Disney").checkName();
    public static final Node DRAGON_BALL = Node.withPredefinedName(Theme.class, "Dragon Ball").checkName();
    public static final Node HARRY_POTTER = Node.withPredefinedName(Theme.class, "Harry Potter").checkName();
    public static final Node LEGO = Node.withPredefinedName(Theme.class, "Lego").checkName();
    public static final Node LOTR = Node.withPredefinedName(Theme.class, "The Lord of the Rings").checkName();
    public static final Node MARVEL = Node.withPredefinedName(Theme.class, "Marvel").checkName();
    public static final Node POKEMON = Node.withPredefinedName(Theme.class, "Pokémon").checkName();
    public static final Node STAR_TREK = Node.withPredefinedName(Theme.class, "Star Trek").checkName();
    public static final Node STAR_WARS = Node.withPredefinedName(Theme.class, "Star War").checkName();
    public static final Node X_MEN = Node.withPredefinedName(Theme.class, "X-Men").checkName();

    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return Theme.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        // dbpedia
        Set<Node> themes = jdbc.query()
                .categories(THEMES)
                .findDBPediaCategories();

        // discard "featuring" and "by" themes
        themes = themes.stream()
                .filter(loc -> !loc.getId().contains("featuring"))
                .filter(loc -> !loc.getId().contains("_by_"))
                .collect(Collectors.toSet());

        // predefined
        themes.addAll(getPredefinedNodes());

        // ids
        fixIds(themes, "video_games_about_", "_video_games", "_games");

        return themes;
    }

}
