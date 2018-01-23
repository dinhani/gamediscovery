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

@ConceptTypeDescriptor(area = ConceptTypeArea.INDUSTRY, icon = Icon.PLATFORM, useAsFilter = true, useAsFilterActiveByDefault = false)
public class Platform extends ConceptEntry {

    // =========================================================================
    // INCOMING
    // =========================================================================
    @RelationshipDescriptor(name = "Owned by", relevance = RelationshipRelevance.MEDIUM)
    @Relationship(direction = Relationship.INCOMING, type = RelationshipType.OWNS)
    private SortedSet<Company> ownerCompanies = TreeSetWithAttributes.create();

    // =========================================================================
    // OUTGOING
    // =========================================================================
    @RelationshipDescriptor(name = "Games", relevance = RelationshipRelevance.MEDIUM_HIGH)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.PUBLISHES)
    private SortedSet<Game> gamesPublished = TreeSetWithAttributes.create();

    // =========================================================================
    // GETTERS AND SETTERS
    // =========================================================================
    public SortedSet<Company> getOwnerCompanies() {
        return ownerCompanies;
    }

    public void setOwnerCompanies(SortedSet<Company> ownerCompanies) {
        this.ownerCompanies = ownerCompanies;
    }

    public SortedSet<Game> getGamesPublished() {
        return gamesPublished;
    }

    public void setGamesPublished(SortedSet<Game> gamesPublished) {
        this.gamesPublished = gamesPublished;
    }

}
