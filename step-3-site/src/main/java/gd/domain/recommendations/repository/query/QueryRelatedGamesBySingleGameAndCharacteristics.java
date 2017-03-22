package gd.domain.recommendations.repository.query;

import com.google.common.collect.Lists;
import gd.domain.recommendations.entity.GraphSearch;
import gd.domain.recommendations.entity.GraphSearchResult;
import gd.infrastructure.steriotype.GDQuery;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@GDQuery
@Scope(value = "prototype")
public class QueryRelatedGamesBySingleGameAndCharacteristics implements QueryRelatedGames {

    // =====================================================================
    // SERVICES
    // =====================================================================
    @Autowired
    private QueryRelatedGamesByCharacteristics byCharacteristics;

    @Autowired
    private QueryRelatedGamesBySingleGame bySingleGame;

    // =====================================================================
    // EXECUTION
    // =====================================================================
    @Override
    public Collection<GraphSearch> execute(String[] searchUids, String[] categoriesToUse) {

        // 1) get games and characteristics to query
        String gameUid = Arrays.stream(searchUids).filter(searchUid -> StringUtils.startsWith(searchUid, "game-")).findFirst().get();
        List<String> characteristicsUids = Arrays.stream(searchUids).filter(searchUid -> !StringUtils.startsWith(searchUid, "game-")).collect(Collectors.toList());

        // 2) query by single game
        GraphSearch searchByGame = bySingleGame.execute(new String[]{gameUid}, categoriesToUse).iterator().next();
        GraphSearchResult firstGame = searchByGame.getResults().first();

        // 3) query by characteristics        
        Collection<GraphSearch> searchesByCharacteristics = byCharacteristics.execute(characteristicsUids.toArray(new String[characteristicsUids.size()]), categoriesToUse);

        // 4) keep only games that are in the characteristics search
        Collection<String> gamesInTheCharacteristicsSearches = searchesByCharacteristics.stream().flatMap(search -> search.getResults().stream()).map(result -> result.getGame()).distinct().collect(Collectors.toList());
        searchByGame.getResults().forEach(gameInSearchByGame -> {
            if (!gamesInTheCharacteristicsSearches.contains(gameInSearchByGame.getGame())) {
                searchByGame.removeResult(gameInSearchByGame);
            }
        });

        // 5) re-add the first game again because it can be removed in the previous step
        searchByGame.addResult(firstGame);

        // 6) sort results
        searchByGame.updateResultsOrder();

        //
        return Lists.newArrayList(searchByGame);
    }
}
