package gd.domain.entities.dto;

import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.RelationshipInfo;

public class ConceptEntryWithRelationshipInfo {

    private final RelationshipInfo relationship;
    private final ConceptEntry targetEntry;
    private final double relevanceModifier;

    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================
    public ConceptEntryWithRelationshipInfo(RelationshipInfo relationship, ConceptEntry targetEntry, double relevanceModifier) {
        this.relationship = relationship;
        this.targetEntry = targetEntry;
        this.relevanceModifier = relevanceModifier;
    }

    // =========================================================================
    // PROCESSED GETTERS
    // =========================================================================
    public double getRelevance() {
        return relationship.getRelevance().getWeight() * relevanceModifier;
    }

    // =========================================================================
    // GETTERS AND SETTERS
    // =========================================================================
    public ConceptEntry getTargetEntry() {
        return targetEntry;
    }

    public RelationshipInfo getRelationship() {
        return relationship;
    }

    public double getRelevanceModifier() {
        return relevanceModifier;
    }

}
