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

@ConceptTypeDescriptor(area = ConceptTypeArea.PLOT, icon = Icon.WEAPON, useAsFilter = false, useAsFilterActiveByDefault = false)
public class Weapon extends ConceptEntry {

    // =========================================================================
    // INCOMING
    // =========================================================================
    @RelationshipDescriptor(name = "Games", relevance = RelationshipRelevance.MEDIUM)
    @Relationship(direction = Relationship.INCOMING, type = RelationshipType.CONTAINS)
    private SortedSet<Game> games = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Part of", relevance = RelationshipRelevance.MEDIUM)
    @Relationship(direction = Relationship.INCOMING, type = RelationshipType.CONTAINS)
    private SortedSet<Weapon> parentWeapons = TreeSetWithAttributes.create();

    // =========================================================================
    // OUTGOING
    // =========================================================================    
    @RelationshipDescriptor(name = "Sub-weapons", relevance = RelationshipRelevance.MEDIUM)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.CONTAINS)
    private SortedSet<Weapon> subWeapons = TreeSetWithAttributes.create();

    // =========================================================================
    // GETTERS AND SETTERS
    // =========================================================================
    public SortedSet<Game> getGames() {
        return games;
    }

    public void setGames(SortedSet<Game> games) {
        this.games = games;
    }

    public SortedSet<Weapon> getParentWeapons() {
        return parentWeapons;
    }

    public void setParentWeapons(SortedSet<Weapon> parentWeapons) {
        this.parentWeapons = parentWeapons;
    }

    public SortedSet<Weapon> getSubWeapons() {
        return subWeapons;
    }

    public void setSubWeapons(SortedSet<Weapon> subWeapons) {
        this.subWeapons = subWeapons;
    }

}
