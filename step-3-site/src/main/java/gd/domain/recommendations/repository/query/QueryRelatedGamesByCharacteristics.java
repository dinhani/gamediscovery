package gd.domain.recommendations.repository.query;

import com.google.common.collect.Lists;
import gd.domain.recommendations.entity.GraphSearch;
import gd.domain.recommendations.repository.cache.GraphCache;
import gd.infrastructure.log.LogProducer;
import gd.infrastructure.steriotype.GDQuery;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import org.apache.commons.math3.stat.Frequency;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@GDQuery
@Scope(value = "prototype")
public class QueryRelatedGamesByCharacteristics implements QueryRelatedGames {

    // =========================================================================
    // SERVICES
    // =========================================================================
    @Autowired
    private GraphCache graph;

    // =========================================================================
    // DATA
    // =========================================================================
    private final Frequency gameAppearances = new Frequency();

    private static final Logger LOGGER = LogProducer.getLogger(QueryRelatedGamesByCharacteristics.class);

    // =========================================================================
    // EXECUTION
    // =========================================================================
    @Override
    public Collection<GraphSearch> execute(String[] searchUids, String[] categoriesToUse) {

        // 1) generate graph searches for the searched uids
        Collection<GraphSearch> graphSearches = generateCompleteGraphSearches(searchUids);

        // 2) remove games viewed less than the total of search uids
        removeNotSharedResults(graphSearches);

        // 3/ update results order
        for (GraphSearch search : graphSearches) {
            search.updateResultsOrder();
        }

        return graphSearches;
    }

    // =========================================================================
    // STEP 1 (GENERATE GRAPH SEARCHES WITH ALL NEIGHBORS)
    // =========================================================================
    private Collection<GraphSearch> generateCompleteGraphSearches(String[] searchUids) {
        return Arrays.stream(searchUids)
                .map(searchUid -> {
                    return generetaGraphSearch(searchUid);
                })
                .collect(Collectors.toList());
    }

    private GraphSearch generetaGraphSearch(String searchUid) {
        GraphSearch graphSearch = new GraphSearch(searchUid);

        // iterate neighbor games couting how many times they were viewed
        Collection<String> neighbors = graph.getGraph().neighborsOf(searchUid);
        for (String neighborUid : neighbors) {
            // ignore characteristics nodes
            if (!neighborUid.startsWith("game-")) {
                continue;
            }

            // mark as viewed
            gameAppearances.addValue(neighborUid);

            // add graph search result
            double relationshipWeight = graph.getGraph().getEdge(searchUid, neighborUid).getWeight();
            graphSearch.addResult(neighborUid, relationshipWeight);
        }

        return graphSearch;
    }

    // =========================================================================
    // STEP 2 (REMOVE NOT SHARED RESULTS)
    // =========================================================================
    private void removeNotSharedResults(Collection<GraphSearch> graphSearches) {
        for (GraphSearch search : graphSearches) {
            gameAppearances.valuesIterator().forEachRemaining(gameUid -> {
                long timesGameAppear = gameAppearances.getCount(gameUid);
                if (timesGameAppear < graphSearches.size()) {
                    search.removeResult((String) gameUid);
                }
            });
        }
    }
}
