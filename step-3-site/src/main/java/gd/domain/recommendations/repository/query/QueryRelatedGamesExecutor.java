package gd.domain.recommendations.repository.query;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import gd.domain.entities.entity.RelationshipType;
import gd.domain.entities.entity.industry.Game;
import gd.domain.entities.entity.industry.ReleaseYear;
import gd.domain.entities.entity.industry.Review;
import gd.domain.entities.repository.query.QueryConceptEntry;
import gd.domain.recommendations.dto.GameDiscoverySearch;
import gd.domain.recommendations.dto.GameDiscoverySearchResult;
import gd.domain.recommendations.entity.GraphSearch;
import gd.domain.recommendations.entity.GraphSearchResult;
import gd.infrastructure.database.AbstractDatabaseCommand;
import gd.infrastructure.database.QueryPagination;
import gd.infrastructure.di.DIService;
import gd.infrastructure.steriotype.GDQuery;
import gd.infrastructure.log.LogMarker;
import gd.infrastructure.log.LogProducer;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

@GDQuery
public class QueryRelatedGamesExecutor extends AbstractDatabaseCommand {

    // QUERIES
    private static final String QUERY_FOR_ADDITIONAL_DETAILS = String.format(""
            // games
            + "MATCH (game:%s) WHERE game.uid IN {uids} "
            + "WITH game "
            // relationships (review and release year)                                                
            + "OPTIONAL MATCH (game)<-[rel:%s]-(relatedEntry) WHERE relatedEntry:%s "
            + "RETURN game, rel, relatedEntry",
            Game.class.getSimpleName(),
            RelationshipType.PUBLISHES, ReleaseYear.class.getSimpleName());

    // SERVICES
    @Autowired
    private QueryConceptEntry queryConceptEntry;

    private static final Logger LOGGER = LogProducer.getLogger(QueryRelatedGamesExecutor.class);

    // =========================================================================
    // EXECUTION
    // =========================================================================
    public GameDiscoverySearch execute(String[] searchUids, QueryPagination pagination) {
        return execute(searchUids, null, pagination);
    }

    public GameDiscoverySearch execute(String[] searchUids, String[] categoriesToUse, QueryPagination pagination) {
        // check params
        if (categoriesToUse == null) {
            categoriesToUse = new String[]{};
        }

        // 1) choose query
        QueryRelatedGames queryRelatedGames = chooseGraphQuery(searchUids);

        // 2) execute query
        LOGGER.trace(LogMarker.DB, "Retrieving related games | query={}, searchUids={}", queryRelatedGames.getClass().getSimpleName(), Arrays.toString(searchUids));
        Collection<GraphSearch> graphSearches = queryRelatedGames.execute(searchUids, categoriesToUse);

        // 3) filter results from multiple results
        Map<String, Double> filteredGameUidsWithRelevance = filterGraphQueryResults(searchUids, graphSearches, pagination);

        // 4) load additional information for all games
        Collection<Game> games = loadAdditionalInfoForAllGames(searchUids, filteredGameUidsWithRelevance);

        // 5) load complete information for first game
        if (queryRelatedGames instanceof QueryRelatedGamesBySingleGame || queryRelatedGames instanceof QueryRelatedGamesBySingleGameAndCharacteristics) {
            loadCompleteInformationForFirstGame(searchUids, filteredGameUidsWithRelevance, games);
        }

        // 6) generate game query
        GameDiscoverySearch gameQuery = transformIntoGameDiscoverySearch(searchUids, graphSearches, filteredGameUidsWithRelevance, games);

        // return
        return gameQuery;
    }

    // =========================================================================
    // STEP 1 - QUERY SELECTION
    // =========================================================================
    private QueryRelatedGames chooseGraphQuery(String[] searchUids) {
        long numberOfGames = Arrays.stream(searchUids).filter(s -> StringUtils.startsWith(s, "game-")).count();
        long numberOfCharacteristics = searchUids.length - numberOfGames;

        // no games
        if (numberOfGames == 0) {
            return DIService.getBean(QueryRelatedGamesByCharacteristics.class);
        }
        if (numberOfGames == 1 && numberOfCharacteristics == 0) {
            return DIService.getBean(QueryRelatedGamesBySingleGame.class);
        }
        if (numberOfGames == 1 && numberOfCharacteristics > 0) {
            return DIService.getBean(QueryRelatedGamesBySingleGameAndCharacteristics.class);
        }
        if (numberOfGames > 1) {
            return DIService.getBean(QueryRelatedGamesBySharedCharacteristics.class);
        }

        return null;
    }

    // =========================================================================
    // STEP 3 - RESULTS FILTERING
    // =========================================================================
    private Map<String, Double> filterGraphQueryResults(String[] searchUids, Collection<GraphSearch> searches, QueryPagination pagination) {
        LOGGER.trace(LogMarker.DB, "Filtering search results | searchUids={}", Arrays.toString(searchUids));

        LinkedHashMap<String, Double> results = searches.stream()
                // get all search results
                .flatMap(gs -> gs.getResults().stream())
                // sum the search results
                .collect(Collectors.groupingBy(GraphSearchResult::getGame, Collectors.summingDouble(GraphSearchResult::getWeight)))
                // sort results by value using a LinkedHashMap
                .entrySet().stream().sorted(Entry.comparingByValue())
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new));

        // return first N elements
        return results.entrySet().stream()
                .skip(pagination.getSkip())
                .limit(pagination.getLimit())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    // =========================================================================
    // STEP 4 - LOAD ADDITIONAL INFORMATION FOR ALL GaMES
    // =========================================================================
    private Collection<Game> loadAdditionalInfoForAllGames(String[] searchUids, Map<String, Double> gamesToLoad) {
        LOGGER.trace(LogMarker.DB, "Querying Neo4J for additional game information for all games | searchUids={}, resultUuids={}", Arrays.toString(searchUids), gamesToLoad);

        // prepare query and parameters
        Collection<String> gamesUidsToLoad = Lists.newArrayList(gamesToLoad.keySet());
        Map<String, Object> queryParameters = Maps.newHashMap();
        queryParameters.put("uids", gamesUidsToLoad);

        // execute query
        return (Collection<Game>) neo4jSession.query(Game.class, QUERY_FOR_ADDITIONAL_DETAILS, queryParameters);
    }

    // =========================================================================
    // STEP 5 - LOAD ADDITIONAL INFORMATION FOR FIRST GAME
    // =========================================================================
    private void loadCompleteInformationForFirstGame(String[] searchUids, Map<String, Double> gamesToLoad, Collection<Game> games) {
        // get first game uid
        String firstGameUid = Collections.min(gamesToLoad.entrySet(), Comparator.comparing(entry -> entry.getValue())).getKey();

        // load information
        LOGGER.trace(LogMarker.DB, "Querying Neo4J for complete information for first game | searchUids={}, firstGameUid={}", Arrays.toString(searchUids), firstGameUid);
        Game firstGame = (Game) queryConceptEntry.execute(Game.class, firstGameUid, true).get();

        // replace it in the collection
        games.remove(firstGame);
        games.add(firstGame);
    }

    // =========================================================================
    // STEP 6 - TRANSFORM RESULTS OBJECT
    // =========================================================================
    private GameDiscoverySearch transformIntoGameDiscoverySearch(String[] searchUids, Collection<GraphSearch> graphSearches, Map<String, Double> gamesToLoad, Collection<Game> games) {
        LOGGER.trace(LogMarker.DB, "Transforming games into a Game Discoveru search | searchUids={}, {}", Arrays.toString(searchUids), gamesToLoad);

        // generate total of results
        int totalOfResults = graphSearches.stream().map(gameSearch -> gameSearch.getResults().size()).mapToInt(size -> size).max().getAsInt();

        // generate game query results
        List<GameDiscoverySearchResult> queryResults = games
                .stream()
                .map(game -> {
                    double relevance = gamesToLoad.get(game.getUid());
                    return new GameDiscoverySearchResult(game, relevance);
                })
                .collect(Collectors.toList());
        // reorder because cypher query loses the order
        queryResults.sort((g1, g2) -> Double.compare(g1.getRelevance(), g2.getRelevance()));

        // generate game query
        GameDiscoverySearch gameQuery = new GameDiscoverySearch(searchUids, queryResults, totalOfResults);

        // return games
        return gameQuery;
    }
}
