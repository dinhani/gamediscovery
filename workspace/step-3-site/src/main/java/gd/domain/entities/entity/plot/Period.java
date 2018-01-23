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

@ConceptTypeDescriptor(area = ConceptTypeArea.PLOT, icon = Icon.CLOCK, useAsFilter = true, useAsFilterActiveByDefault = false)
public class Period extends ConceptEntry {

    // =========================================================================
    // INCOMING
    // =========================================================================
    @RelationshipDescriptor(name = "Games", relevance = RelationshipRelevance.MEDIUM_HIGH)
    @Relationship(direction = Relationship.INCOMING, type = RelationshipType.IS_SET_IN)
    private SortedSet<Game> games = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Part of", relevance = RelationshipRelevance.MEDIUM)
    @Relationship(direction = Relationship.INCOMING, type = RelationshipType.CONTAINS)
    private SortedSet<Period> parentPeriods = TreeSetWithAttributes.create();

    // =========================================================================
    // OUTGOING
    // =========================================================================
    @RelationshipDescriptor(name = "Sub-periods", relevance = RelationshipRelevance.MEDIUM)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.CONTAINS)
    private SortedSet<Period> subPeriods = TreeSetWithAttributes.create();

    // =========================================================================
    // GETTERS AND SETTERS
    // =========================================================================
    public SortedSet<Game> getGames() {
        return games;
    }

    public void setGames(SortedSet<Game> games) {
        this.games = games;
    }

    public SortedSet<Period> getParentPeriods() {
        return parentPeriods;
    }

    public void setParentPeriods(SortedSet<Period> parentPeriods) {
        this.parentPeriods = parentPeriods;
    }

    public SortedSet<Period> getSubPeriods() {
        return subPeriods;
    }

    public void setSubPeriods(SortedSet<Period> subPeriods) {
        this.subPeriods = subPeriods;
    }

}
