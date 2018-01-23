package gd.domain.recommendations.entity;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GraphSearchResult implements Comparable<GraphSearchResult> {

    private final String game;
    private double weight;
    private final Map<String, Double> partialWeights;

    // used to update the average dinamically
    private transient final List<Double> samples;

    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================
    public GraphSearchResult(String game, double weight) {
        this(game, weight, Collections.EMPTY_MAP);
    }

    public GraphSearchResult(String game, double weight, Map<String, Double> partialWeights) {
        this.game = game;
        this.weight = weight;
        this.partialWeights = partialWeights;
        samples = Lists.newArrayList(weight);
    }

    @Override
    public boolean equals(Object obj) {
        try {
            GraphSearchResult other = (GraphSearchResult) obj;
            return game.equals(other.game);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(game);
    }

    // =========================================================================
    // OBJECT METHODS
    // =========================================================================
    @Override
    public int compareTo(GraphSearchResult o) {
        return ComparisonChain.start()
                .compare(weight, o.weight)
                .compare(game, o.game)
                .result();
    }

    // =========================================================================
    // PROCESSED ADDERS
    // =========================================================================
    public void updateWeightSumming(double weight) {
        this.weight += weight;
    }

    public void updateWeightAveraging(double weight) {
        samples.add(weight);

        double sum = 0;
        for (double sample : samples) {
            sum += sample;
        }
        this.weight = sum / samples.size();
    }

    // =========================================================================
    // GETTERS AND SETTERS
    // =========================================================================
    public String getGame() {
        return game;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Map<String, Double> getPartialWeights() {
        return partialWeights;
    }

}
