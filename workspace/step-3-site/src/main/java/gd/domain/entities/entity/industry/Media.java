package gd.domain.entities.entity.industry;

import gd.domain.entities.entity.ConceptTypeArea;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.annotation.ConceptTypeDescriptor;
import gd.domain.entities.annotation.RelationshipDescriptor;
import gd.domain.entities.entity.RelationshipRelevance;
import gd.domain.entities.entity.RelationshipType;
import gd.domain.entities.entity.TreeSetWithAttributes;
import gd.infrastructure.ui.Icon;
import java.util.SortedSet;
import org.neo4j.ogm.annotation.Relationship;

@ConceptTypeDescriptor(area = ConceptTypeArea.INDUSTRY, icon = Icon.CLOUD, useAsFilter = false, useAsFilterActiveByDefault = false)
public class Media extends ConceptEntry {

    // =========================================================================
    // INCOMING
    // =========================================================================
    // =========================================================================
    // OUTGOING
    // =========================================================================
    @RelationshipDescriptor(name = "Games", relevance = RelationshipRelevance.LOW)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.PUBLISHES)
    private SortedSet<Game> games = TreeSetWithAttributes.create();

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
