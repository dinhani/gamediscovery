package gd.domain.recommendations.dto;

import gd.domain.entities.entity.industry.Game;

public class GameDiscoverySearchResult {

    private final Game game;
    private final double relevance;

    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================
    public GameDiscoverySearchResult(Game game, double relevance) {
        this.game = game;
        this.relevance = relevance;
    }

    // =========================================================================
    // GETTERS AND SETTERS
    // =========================================================================
    public Game getGame() {
        return game;
    }

    public double getRelevance() {
        return relevance;
    }

}
