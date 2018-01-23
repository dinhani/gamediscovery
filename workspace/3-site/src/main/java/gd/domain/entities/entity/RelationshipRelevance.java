package gd.domain.entities.entity;

/**
 * The relevance a relationship between two concept entries have.
 *
 * @author Renato
 */
public enum RelationshipRelevance {

    LOW(1), MEDIUM(2), MEDIUM_HIGH(3), HIGH(4), HIGHEST(5);

    private final int weight;

    private RelationshipRelevance(int weihgt) {
        this.weight = weihgt;
    }

    public int getWeight() {
        return weight;
    }

}
