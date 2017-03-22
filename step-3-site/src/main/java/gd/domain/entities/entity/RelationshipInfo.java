package gd.domain.entities.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;

public class RelationshipInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    // =========================================================================
    // DATA
    // =========================================================================
    private String uid;
    private String name;
    private String type;
    private String direction;
    private RelationshipRelevance relevance;
    private ConceptType targetType;

    // =========================================================================
    // OBJECT METHODS
    // =========================================================================
    @Override
    public String toString() {
        return String.format("relUid=%s, relType=%s, relDirection=%s", uid, type, direction);
    }

    // =========================================================================
    // CHECKS
    // =========================================================================
    @JsonIgnore
    public boolean isIncoming() {
        return getDirection().equals("INCOMING");
    }

    @JsonIgnore
    public boolean isOutgoing() {
        return getDirection().equals("OUTGOING");
    }

    @JsonIgnore
    public boolean isUndirected() {
        return getDirection().equals("UNDIRECTED");
    }

    // =========================================================================
    // PROCESSED GETTERS
    // =========================================================================
    public String getIcon() {
        return getTargetType().getIcon().getName();
    }

    // =========================================================================
    // GETTERS AND SETTERS
    // =========================================================================
    @JsonIgnore
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonIgnore
    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @JsonIgnore
    public RelationshipRelevance getRelevance() {
        return relevance;
    }

    public void setRelevance(RelationshipRelevance relevance) {
        this.relevance = relevance;
    }

    @JsonIgnore
    public ConceptType getTargetType() {
        return targetType;
    }

    public void setTargetType(ConceptType targetType) {
        this.targetType = targetType;
    }

}
