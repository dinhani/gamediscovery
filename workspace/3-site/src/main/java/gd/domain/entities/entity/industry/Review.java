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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.neo4j.ogm.annotation.Relationship;

@ConceptTypeDescriptor(area = ConceptTypeArea.INDUSTRY, icon = Icon.TAG, useAsFilter = false, useAsFilterActiveByDefault = false)
public class Review extends ConceptEntry {

    // =========================================================================
    // SPECIFIC DATA
    // =========================================================================    
    private Float score;

    // =========================================================================
    // INCOMING
    // =========================================================================    
    // =========================================================================
    // OUTGOING
    // =========================================================================
    @RelationshipDescriptor(name = "Games", relevance = RelationshipRelevance.LOW)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.CLASSIFIES)
    private SortedSet<Game> games = TreeSetWithAttributes.create();

    // =========================================================================
    // PROCESSED GETTERS
    // =========================================================================
    public float getScore() {
        if (score == null) {
            String[] nameParts = StringUtils.split(getName(), '-');
            String scorePart = nameParts[nameParts.length - 1];
            score = NumberUtils.toFloat(scorePart, 0);
        }
        return score;
    }

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
