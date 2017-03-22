package gd.app.graphgenerator.task;

import gd.domain.entities.Node;
import static gd.app.graphgenerator.task.ImportTask.INSTANCE_OR_SUBCLASS;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.gameplay.Genre;
import gd.infrastructure.steriotype.GDTask;
import java.util.Collection;

@GDTask
public class ImportGenre extends ImportTask {

    // =========================================================================
    // DATA (EXISTING)
    // =========================================================================
    public static final Node ACTION_RPG = Node.withPredefinedId(Genre.class, "action-role-playing");
    public static final Node CARD = Node.withPredefinedId(Genre.class, "card");
    public static final Node MUSIC = Node.withPredefinedId(Genre.class, "music");
    public static final Node PUZZLE = Node.withPredefinedId(Genre.class, "puzzle");
    public static final Node RACING = Node.withPredefinedId(Genre.class, "racing").checkName();
    public static final Node REAL_TIME_STRATEGY = Node.withPredefinedId(Genre.class, "real-time-strategy");
    public static final Node RPG = Node.withPredefinedId(Genre.class, "role-playing");
    public static final Node STRATEGY = Node.withPredefinedId(Genre.class, "strategy");
    public static final Node SURVIVAL_HORROR = Node.withPredefinedId(Genre.class, "survival-horror");
    public static final Node SIMULATION = Node.withPredefinedId(Genre.class, "simulation");

    // =========================================================================
    // DATA (NEW)
    // =========================================================================
    public static final Node FLIGHT = Node.withPredefinedName(Genre.class, "Flight");
    public static final Node PHYSICS = Node.withPredefinedName(Genre.class, "Physics").setWikidataId("Q413");

    // =========================================================================
    // WIKIDATA IDS
    // =========================================================================    
    private static final String GAME_GENRE_ID = "Q659563";
    private static final String FILM_GENRE_ID = "Q201658";
    private static final String LITERARY_GENRE_ID = "Q223393";

    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return Genre.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        // wikidata
        Collection<Node> genres = jdbc.query()
                .attributesKeys(INSTANCE_OR_SUBCLASS)
                .attributesValues(GAME_GENRE_ID, FILM_GENRE_ID, LITERARY_GENRE_ID)
                .findWikidataEntities();

        // predefined
        genres.add(FLIGHT);
        genres.add(PHYSICS);

        // fixes
        fixIds(genres, "_video_game", "_game");

        for (Node genre : genres) {
            // fix name
            String name = genre.getName().replaceAll("Video Game", "").replaceAll("Game", "");
            genre.setName(name);

            // set alias
            if (genre.getName().contains("Role")) {
                genre.addAlias("RPG");
            }
            if (genre.getName().contains("First")) {
                genre.addAlias("FPS");
            }
        }

        // remove unnecessary genres        
        genres.removeIf(genre -> genre.getName().equals("Arcade"));

        // return genres
        return genres;
    }

}
