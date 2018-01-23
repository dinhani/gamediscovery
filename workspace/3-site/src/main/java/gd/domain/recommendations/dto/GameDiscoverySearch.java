package gd.domain.recommendations.dto;

import java.util.Collection;

public class GameDiscoverySearch {

    private final String[] searchUids;
    private final Collection<GameDiscoverySearchResult> results;
    private final int totalOfResults;

    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================
    public GameDiscoverySearch(String[] searchUids, Collection<GameDiscoverySearchResult> results, int totalOfResults) {
        this.searchUids = searchUids;
        this.results = results;
        this.totalOfResults = totalOfResults;
    }

    // =========================================================================
    // GETTERS AND SETTERS
    // =========================================================================
    public String[] getSearchUids() {
        return searchUids;
    }

    public Collection<GameDiscoverySearchResult> getResults() {
        return results;
    }

    public int getTotalOfResults() {
        return totalOfResults;
    }

}
