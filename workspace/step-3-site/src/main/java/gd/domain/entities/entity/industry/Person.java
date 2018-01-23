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

@ConceptTypeDescriptor(area = ConceptTypeArea.INDUSTRY, icon = Icon.PERSON, useAsFilter = false, useAsFilterActiveByDefault = false)
public class Person extends ConceptEntry {

    // =========================================================================
    // INCOMING
    // =========================================================================
    // =========================================================================
    // OUTGOING
    // =========================================================================
    @RelationshipDescriptor(name = "Works(ed) for", relevance = RelationshipRelevance.MEDIUM)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.WORKS)
    private SortedSet<Company> companiesWorkedFor = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Games developed", relevance = RelationshipRelevance.MEDIUM_HIGH)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.DEVELOPS)
    private SortedSet<Game> gamesDeveloped = TreeSetWithAttributes.create();

    // =========================================================================
    // GETTERS AND SETTERS
    // =========================================================================
    public SortedSet<Company> getCompaniesWorkedFor() {
        return companiesWorkedFor;
    }

    public void setCompaniesWorkedFor(SortedSet<Company> companiesWorkedFor) {
        this.companiesWorkedFor = companiesWorkedFor;
    }

    public SortedSet<Game> getGamesDeveloped() {
        return gamesDeveloped;
    }

    public void setGamesDeveloped(SortedSet<Game> gamesDeveloped) {
        this.gamesDeveloped = gamesDeveloped;
    }

}
