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

@ConceptTypeDescriptor(area = ConceptTypeArea.INDUSTRY, icon = Icon.BUILDING, useAsFilter = true, useAsFilterActiveByDefault = false)
public class Company extends ConceptEntry {

    // =========================================================================
    // INCOMING
    // =========================================================================
    @RelationshipDescriptor(name = "Part of", relevance = RelationshipRelevance.MEDIUM)
    @Relationship(direction = Relationship.INCOMING, type = RelationshipType.OWNS)
    private SortedSet<Company> parentCompanies = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Employees", relevance = RelationshipRelevance.MEDIUM)
    @Relationship(direction = Relationship.INCOMING, type = RelationshipType.WORKS)
    private SortedSet<Person> employees = TreeSetWithAttributes.create();

    // =========================================================================
    // OUTGOING
    // =========================================================================
    @RelationshipDescriptor(name = "Games published", relevance = RelationshipRelevance.MEDIUM)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.PUBLISHES)
    private SortedSet<Game> gamesPublished = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Games developed", relevance = RelationshipRelevance.HIGH)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.DEVELOPS)
    private SortedSet<Game> gamesDeveloped = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Platforms owned", relevance = RelationshipRelevance.MEDIUM)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.OWNS)
    private SortedSet<Platform> platformsOwned = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Sub-companies", relevance = RelationshipRelevance.MEDIUM)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.OWNS)
    private SortedSet<Company> subCompanies = TreeSetWithAttributes.create();

    // =========================================================================
    // GETTERS AND SETTERS
    // =========================================================================
    public SortedSet<Company> getParentCompanies() {
        return parentCompanies;
    }

    public void setParentCompanies(SortedSet<Company> parentCompanies) {
        this.parentCompanies = parentCompanies;
    }

    public SortedSet<Person> getEmployees() {
        return employees;
    }

    public void setEmployees(SortedSet<Person> employees) {
        this.employees = employees;
    }

    public SortedSet<Game> getGamesPublished() {
        return gamesPublished;
    }

    public void setGamesPublished(SortedSet<Game> gamesPublished) {
        this.gamesPublished = gamesPublished;
    }

    public SortedSet<Game> getGamesDeveloped() {
        return gamesDeveloped;
    }

    public void setGamesDeveloped(SortedSet<Game> gamesDeveloped) {
        this.gamesDeveloped = gamesDeveloped;
    }

    public SortedSet<Platform> getPlatformsOwned() {
        return platformsOwned;
    }

    public void setPlatformsOwned(SortedSet<Platform> platformsOwned) {
        this.platformsOwned = platformsOwned;
    }

    public SortedSet<Company> getSubCompanies() {
        return subCompanies;
    }

    public void setSubCompanies(SortedSet<Company> subCompanies) {
        this.subCompanies = subCompanies;
    }

}
