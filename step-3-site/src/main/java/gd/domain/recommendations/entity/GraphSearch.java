package gd.domain.recommendations.entity;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import gd.domain.shared.GD;
import java.util.Map;
import java.util.SortedSet;

public class GraphSearch {

    // data
    private final String search;
    private SortedSet<GraphSearchResult> results;

    // helper collections    
    private final Map<String, GraphSearchResult> resultsMap = Maps.newHashMapWithExpectedSize(GD.GRAPH_NODES_SIZE);

    // state
    private boolean updatedOrder;

    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================
    public GraphSearch(String search) {
        this.search = search;
    }

    // =========================================================================
    // PROCESSED ADDERS
    // =========================================================================
    public void addResult(String game, double weight) {
        addResult(new GraphSearchResult(game, weight));
    }

    public void addResult(GraphSearchResult result) {
        resultsMap.put(result.getGame(), result);
    }

    public void removeResult(GraphSearchResult result) {
        removeResult(result.getGame());
    }

    public void removeResult(String game) {
        resultsMap.remove(game);
    }

    public void updateResultsOrder() {
        results = Sets.newTreeSet(resultsMap.values());
        updatedOrder = true;
    }

    // =========================================================================
    // PROCESSED GETTERS
    // =========================================================================
    public boolean containResult(String game) {
        return resultsMap.containsKey(game);
    }

    public GraphSearchResult getResult(String game) {
        return resultsMap.get(game);
    }

    public SortedSet<GraphSearchResult> getResults() {
        if (!updatedOrder) {
            throw new IllegalStateException("Call updateOrder() before trying to get the results");
        }
        return results;
    }

    // =========================================================================
    // GETTERS
    // =========================================================================
    public String getSearch() {
        return search;
    }
}
