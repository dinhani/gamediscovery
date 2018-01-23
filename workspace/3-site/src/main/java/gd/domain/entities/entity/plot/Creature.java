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

@ConceptTypeDescriptor(area = ConceptTypeArea.PLOT, icon = Icon.PERSON, useAsFilter = false, useAsFilterActiveByDefault = false)
public class Creature extends ConceptEntry {

    // =========================================================================
    // INCOMING
    // =========================================================================
    @RelationshipDescriptor(name = "Characters", relevance = RelationshipRelevance.HIGH)
    @Relationship(direction = Relationship.INCOMING, type = RelationshipType.IS)
    private SortedSet<Character> characters = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Games", relevance = RelationshipRelevance.HIGH)
    @Relationship(direction = Relationship.INCOMING, type = RelationshipType.FEATURES)
    private SortedSet<Game> games = TreeSetWithAttributes.create();

    // =========================================================================
    // OUTGOING
    // =========================================================================
    // =========================================================================
    // GETTERS AND SETTERS
    // =========================================================================
    public SortedSet<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(SortedSet<Character> characters) {
        this.characters = characters;
    }

    public SortedSet<Game> getGames() {
        return games;
    }

    public void setGames(SortedSet<Game> games) {
        this.games = games;
    }

}
