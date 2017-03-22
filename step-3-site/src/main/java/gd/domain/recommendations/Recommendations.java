package gd.domain.recommendations;

import com.google.common.base.Preconditions;
import gd.domain.recommendations.dto.GameDiscoverySearch;
import gd.domain.recommendations.dto.GameLinks;
import gd.domain.recommendations.repository.query.QueryGameLinks;
import gd.domain.recommendations.repository.query.QueryRelatedGamesExecutor;
import gd.infrastructure.database.QueryPagination;
import gd.infrastructure.error.ErrorMessages;
import gd.infrastructure.log.LogMarker;
import gd.infrastructure.log.LogProducer;
import gd.infrastructure.steriotype.GDRepository;
import java.util.Arrays;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

@GDRepository
public class Recommendations {

    // LOGGER
    private static final Logger LOGGER = LogProducer.getLogger(Recommendations.class);

    // =========================================================================
    // DATABASE ACTIONS (QUERIES)
    // =========================================================================
    @Autowired
    private QueryRelatedGamesExecutor queryRelatedGames;

    @Autowired
    private QueryGameLinks queryGameLinks;

    // =========================================================================
    // GAMES QUERIES
    // =========================================================================
    public GameDiscoverySearch getRelatedGames(String[] conceptEntryUids, QueryPagination pagination) {
        return getRelatedGames(conceptEntryUids, null, pagination);
    }

    public GameDiscoverySearch getRelatedGames(String[] conceptEntryUids, String[] categoryUids, QueryPagination pagination) {
        Preconditions.checkArgument(conceptEntryUids != null, ErrorMessages.NOT_NULL, "conceptEntryUids");
        LOGGER.debug(LogMarker.DOMAIN, "Retrieving related games | {}", Arrays.toString(conceptEntryUids));
        return queryRelatedGames.execute(conceptEntryUids, categoryUids, pagination);
    }

    public GameLinks getGameLinks(String gameUid) {
        LOGGER.debug(LogMarker.DOMAIN, "Retrieving game links | gameUid={}", gameUid);
        return queryGameLinks.execute(gameUid);
    }
}
