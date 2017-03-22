package gd.domain.entities.entity.plot;

import gd.domain.entities.entity.ConceptTypeArea;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.annotation.ConceptTypeDescriptor;
import gd.domain.entities.annotation.RelationshipDescriptor;
import gd.domain.entities.entity.RelationshipRelevance;
import gd.domain.entities.entity.RelationshipType;
import gd.domain.entities.entity.TreeSetWithAttributes;
import gd.domain.entities.entity.industry.Game;
import gd.infrastructure.ui.Icon;
import java.util.SortedSet;
import org.neo4j.ogm.annotation.Relationship;

@ConceptTypeDescriptor(area = ConceptTypeArea.PLOT, icon = Icon.TREE, useAsFilter = true, useAsFilterActiveByDefault = false)
public class Location extends ConceptEntry {

    // =========================================================================
    // INCOMING
    // =========================================================================
    @RelationshipDescriptor(name = "Games", relevance = RelationshipRelevance.MEDIUM_HIGH)
    @Relationship(direction = Relationship.INCOMING, type = RelationshipType.IS_SET_IN)
    private SortedSet<Game> games = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Part of", relevance = RelationshipRelevance.MEDIUM)
    @Relationship(direction = Relationship.INCOMING, type = RelationshipType.CONTAINS)
    private SortedSet<Location> parentLocations = TreeSetWithAttributes.create();

    // =========================================================================
    // OUTGOING
    // =========================================================================
    @RelationshipDescriptor(name = "Sub-locations", relevance = RelationshipRelevance.MEDIUM)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.CONTAINS)
    private SortedSet<Location> subLocations = TreeSetWithAttributes.create();

    // =========================================================================
    // GETTERS AND SETTERS
    // =========================================================================
    public SortedSet<Game> getGames() {
        return games;
    }

    public void setGames(SortedSet<Game> games) {
        this.games = games;
    }

    public SortedSet<Location> getParentLocations() {
        return parentLocations;
    }

    public void setParentLocations(SortedSet<Location> parentLocations) {
        this.parentLocations = parentLocations;
    }

    public SortedSet<Location> getSubLocations() {
        return subLocations;
    }

    public void setSubLocations(SortedSet<Location> subLocations) {
        this.subLocations = subLocations;
    }

}
