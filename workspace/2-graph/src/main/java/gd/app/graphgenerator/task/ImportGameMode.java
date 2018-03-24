package gd.app.graphgenerator.task;

import gd.domain.entities.Node;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.gameplay.GameMode;
import gd.infrastructure.steriotype.GDTask;
import java.util.Collection;
import org.springframework.util.StringUtils;

@GDTask
public class ImportGameMode extends ImportTask {

    private static final String GAME_MODE = "Q1971694";

    public static final Node GAME_NEW_GAME_PLUS = Node.withPredefinedName(GameMode.class, "New Game Plus");

    public static final Node GAME_MODE_LOCAL_MULTIPLAYER = Node.withPredefinedName(GameMode.class, "Local Multiplayer");
    public static final Node GAME_MODE_ONLINE = Node.withPredefinedIdAndName(GameMode.class, "online", "Online", "");

    public static final Node GAME_MODE_SHARED_SCREEN = Node.withPredefinedName(GameMode.class, "Shared Screen");
    public static final Node GAME_MODE_SPLIT_SCREEN = Node.withPredefinedName(GameMode.class, "Split Screen");

    public static final Node GAME_MODE_PVP = Node.withPredefinedIdAndName(GameMode.class, "player-versus-player", "Player versus Player", "pvp").addAlias("PVP");
    public static final Node GAME_MODE_PVE = Node.withPredefinedIdAndName(GameMode.class, "player-versus-environment", "Player versus Environment", "pve").addAlias("PVE");

    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return GameMode.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        // wikidata
        Collection<Node> gameModes = jdbc.query()
                .attributesKeys(INSTANCE_OR_SUBCLASS)
                .attributesValues(GAME_MODE)
                .findWikidataEntities();

        // predefined
        gameModes.addAll(getPredefinedNodes());

        // fixes
        for (Node gameMode : gameModes) {
            // fix id
            gameMode.addAlternativeId(gameMode.getId());
            String newId = StringUtils.replace(gameMode.getId(), "_video_game", "");
            newId = StringUtils.replace(newId, "_game", "");
            gameMode.setId(newId);

            // fix name
            String name = gameMode.getName().replaceAll("Video Game", "").replaceAll("Game", "");
            gameMode.setName(name);
        }

        return gameModes;
    }

}
