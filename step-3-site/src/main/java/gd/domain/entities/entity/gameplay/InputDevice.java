package gd.domain.entities.entity.gameplay;

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

@ConceptTypeDescriptor(area = ConceptTypeArea.GAMEPLAY, icon = Icon.VIDEOCAMERA, useAsFilter = false, useAsFilterActiveByDefault = false)
public class InputDevice extends ConceptEntry {

    // =========================================================================
    // INCOMING
    // =========================================================================
    @RelationshipDescriptor(name = "Games", relevance = RelationshipRelevance.LOW)
    @Relationship(direction = Relationship.INCOMING, type = RelationshipType.HAS_TECH)
    private SortedSet<Game> games = TreeSetWithAttributes.create();

    // =========================================================================
    // OUTGOING
    // =========================================================================
    // =========================================================================
    // GETTERS AND SETTERS
    // =========================================================================
    public SortedSet<Game> getGames() {
        return games;
    }

    public void setGames(SortedSet<Game> games) {
        this.games = games;
    }

}
