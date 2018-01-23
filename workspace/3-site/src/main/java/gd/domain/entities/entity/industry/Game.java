package gd.domain.entities.entity.industry;

import gd.domain.entities.annotation.ConceptTypeDescriptor;
import gd.domain.entities.annotation.RelationshipDescriptor;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.ConceptTypeArea;
import gd.domain.entities.entity.RelationshipRelevance;
import gd.domain.entities.entity.RelationshipType;
import gd.domain.entities.entity.TreeSetWithAttributes;
import gd.domain.entities.entity.gameplay.Duration;
import gd.domain.entities.entity.gameplay.Engine;
import gd.domain.entities.entity.gameplay.GameMode;
import gd.domain.entities.entity.gameplay.Genre;
import gd.domain.entities.entity.gameplay.Graphics;
import gd.domain.entities.entity.gameplay.InputDevice;
import gd.domain.entities.entity.gameplay.Mechanics;
import gd.domain.entities.entity.gameplay.NumberOfPlayers;
import gd.domain.entities.entity.plot.Atmosphere;
import gd.domain.entities.entity.plot.Character;
import gd.domain.entities.entity.plot.Creature;
import gd.domain.entities.entity.plot.Location;
import gd.domain.entities.entity.plot.Organization;
import gd.domain.entities.entity.plot.Period;
import gd.domain.entities.entity.plot.Soundtrack;
import gd.domain.entities.entity.plot.SportTeam;
import gd.domain.entities.entity.plot.Theme;
import gd.domain.entities.entity.plot.Vehicle;
import gd.domain.entities.entity.plot.Weapon;
import gd.infrastructure.text.Text;
import gd.infrastructure.ui.Icon;
import java.util.SortedSet;
import org.neo4j.ogm.annotation.Relationship;

@ConceptTypeDescriptor(area = ConceptTypeArea.INDUSTRY, icon = Icon.GAME)
public class Game extends ConceptEntry {

    // =========================================================================
    // SERVICES
    // =========================================================================
    private transient Text text;

    // =========================================================================
    // INCOMING (INDUSTRY)
    // =========================================================================
    @RelationshipDescriptor(name = "Awards", relevance = RelationshipRelevance.LOW)
    @Relationship(direction = Relationship.INCOMING, type = RelationshipType.AWARDS)
    private SortedSet<Award> awards = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Classifications", relevance = RelationshipRelevance.MEDIUM)
    @Relationship(direction = Relationship.INCOMING, type = RelationshipType.CLASSIFIES)
    private SortedSet<ContentClassification> contentClassifications = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Classifications Characteristics", relevance = RelationshipRelevance.MEDIUM)
    @Relationship(direction = Relationship.INCOMING, type = RelationshipType.CLASSIFIES)
    private SortedSet<ContentCharacteristic> contentCharacteristics = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Development Team", relevance = RelationshipRelevance.MEDIUM_HIGH)
    @Relationship(direction = Relationship.INCOMING, type = RelationshipType.DEVELOPS)
    private SortedSet<Person> developers = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Developers", relevance = RelationshipRelevance.HIGH)
    @Relationship(direction = Relationship.INCOMING, type = RelationshipType.DEVELOPS)
    private SortedSet<Company> developerCompanies = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Medias", relevance = RelationshipRelevance.LOW)
    @Relationship(direction = Relationship.INCOMING, type = RelationshipType.PUBLISHES)
    private SortedSet<Media> medias = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Platforms", relevance = RelationshipRelevance.MEDIUM_HIGH)
    @Relationship(direction = Relationship.INCOMING, type = RelationshipType.PUBLISHES)
    private SortedSet<Platform> platforms = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Publishers", relevance = RelationshipRelevance.MEDIUM)
    @Relationship(direction = Relationship.INCOMING, type = RelationshipType.PUBLISHES)
    private SortedSet<Company> publisherCompanies = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Release Years", relevance = RelationshipRelevance.LOW)
    @Relationship(direction = Relationship.INCOMING, type = RelationshipType.PUBLISHES)
    private SortedSet<ReleaseYear> releaseYears = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Reviews", relevance = RelationshipRelevance.LOW)
    @Relationship(direction = Relationship.INCOMING, type = RelationshipType.CLASSIFIES)
    private SortedSet<Review> reviews = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Series", relevance = RelationshipRelevance.HIGHEST)
    @Relationship(direction = Relationship.INCOMING, type = RelationshipType.CONTAINS)
    private SortedSet<Series> series = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Sold in", relevance = RelationshipRelevance.LOW)
    @Relationship(direction = Relationship.INCOMING, type = RelationshipType.SELLS)
    private SortedSet<Store> stores = TreeSetWithAttributes.create();

    // =========================================================================
    // OUTGOING (PLOT)
    // =========================================================================
    @RelationshipDescriptor(name = "Atmospheres", relevance = RelationshipRelevance.MEDIUM_HIGH)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.IS)
    private SortedSet<Atmosphere> atmospheres = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Characters", relevance = RelationshipRelevance.HIGHEST)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.FEATURES)
    private SortedSet<gd.domain.entities.entity.plot.Character> characters = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Creatures", relevance = RelationshipRelevance.HIGH)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.FEATURES)
    private SortedSet<Creature> creatures = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Durations", relevance = RelationshipRelevance.MEDIUM)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.IS)
    private SortedSet<Duration> durations = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Game Mechanics", relevance = RelationshipRelevance.HIGH)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.CONTAINS)
    private SortedSet<Mechanics> mechanics = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Genres", relevance = RelationshipRelevance.HIGHEST)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.IS)
    private SortedSet<Genre> genres = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Locations", relevance = RelationshipRelevance.MEDIUM_HIGH)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.IS_SET_IN)
    private SortedSet<Location> locations = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Organizations", relevance = RelationshipRelevance.MEDIUM)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.CONTAINS)
    private SortedSet<Organization> organizations = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Periods", relevance = RelationshipRelevance.MEDIUM_HIGH)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.IS_SET_IN)
    private SortedSet<Period> periods = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Soundtracks", relevance = RelationshipRelevance.MEDIUM)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.FEATURES)
    private SortedSet<Soundtrack> soundtracks = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Teams (Sports)", relevance = RelationshipRelevance.MEDIUM)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.FEATURES)
    private SortedSet<SportTeam> sportTeams = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Themes", relevance = RelationshipRelevance.HIGH)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.IS)
    private SortedSet<Theme> themes = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Vehicles", relevance = RelationshipRelevance.MEDIUM)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.CONTAINS)
    private SortedSet<Vehicle> vehicles = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Weapons", relevance = RelationshipRelevance.MEDIUM)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.CONTAINS)
    private SortedSet<Weapon> weapons = TreeSetWithAttributes.create();

    // =========================================================================
    // OUTGOING (GAMEPLAY)
    // =========================================================================
    @RelationshipDescriptor(name = "Game Engines", relevance = RelationshipRelevance.MEDIUM)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.HAS_TECH)
    private SortedSet<Engine> engines = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Game Modes", relevance = RelationshipRelevance.MEDIUM)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.HAS_TECH)
    private SortedSet<GameMode> gameModes = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Graphics", relevance = RelationshipRelevance.HIGH)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.HAS_TECH)
    private SortedSet<Graphics> graphics = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Input Devices", relevance = RelationshipRelevance.LOW)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.HAS_TECH)
    private SortedSet<InputDevice> inputDevices = TreeSetWithAttributes.create();

    @RelationshipDescriptor(name = "Number of Players", relevance = RelationshipRelevance.MEDIUM)
    @Relationship(direction = Relationship.OUTGOING, type = RelationshipType.HAS_TECH)
    private SortedSet<NumberOfPlayers> numberOfPlayers = TreeSetWithAttributes.create();

    // =========================================================================
    // PROCESSED GETTERS
    // =========================================================================
    public double getAverageScore() {
        if (reviews.isEmpty()) {
            return 0;
        }
        return reviews.stream().mapToDouble(Review::getScore).average().getAsDouble();
    }

    // =========================================================================
    // GETTERS AND SETTERS
    // =========================================================================
    public SortedSet<Award> getAwards() {
        return awards;
    }

    public void setAwards(SortedSet<Award> awards) {
        this.awards = awards;
    }

    public SortedSet<ContentClassification> getContentClassifications() {
        return contentClassifications;
    }

    public void setContentClassifications(SortedSet<ContentClassification> contentClassifications) {
        this.contentClassifications = contentClassifications;
    }

    public SortedSet<ContentCharacteristic> getContentCharacteristics() {
        return contentCharacteristics;
    }

    public void setContentCharacteristics(SortedSet<ContentCharacteristic> contentCharacteristics) {
        this.contentCharacteristics = contentCharacteristics;
    }

    public SortedSet<Person> getDevelopers() {
        return developers;
    }

    public void setDevelopers(SortedSet<Person> developers) {
        this.developers = developers;
    }

    public SortedSet<Company> getDeveloperCompanies() {
        return developerCompanies;
    }

    public void setDeveloperCompanies(SortedSet<Company> developerCompanies) {
        this.developerCompanies = developerCompanies;
    }

    public SortedSet<Media> getMedias() {
        return medias;
    }

    public void setMedias(SortedSet<Media> medias) {
        this.medias = medias;
    }

    public SortedSet<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(SortedSet<Platform> platforms) {
        this.platforms = platforms;
    }

    public SortedSet<Company> getPublisherCompanies() {
        return publisherCompanies;
    }

    public void setPublisherCompanies(SortedSet<Company> publisherCompanies) {
        this.publisherCompanies = publisherCompanies;
    }

    public SortedSet<ReleaseYear> getReleaseYears() {
        return releaseYears;
    }

    public void setReleaseYears(SortedSet<ReleaseYear> releaseYears) {
        this.releaseYears = releaseYears;
    }

    public SortedSet<Review> getReviews() {
        return reviews;
    }

    public void setReviews(SortedSet<Review> reviews) {
        this.reviews = reviews;
    }

    public SortedSet<Series> getSeries() {
        return series;
    }

    public void setSeries(SortedSet<Series> series) {
        this.series = series;
    }

    public SortedSet<Store> getStores() {
        return stores;
    }

    public void setStores(SortedSet<Store> stores) {
        this.stores = stores;
    }

    public SortedSet<Atmosphere> getAtmospheres() {
        return atmospheres;
    }

    public void setAtmospheres(SortedSet<Atmosphere> atmospheres) {
        this.atmospheres = atmospheres;
    }

    public SortedSet<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(SortedSet<Character> characters) {
        this.characters = characters;
    }

    public SortedSet<Creature> getCreatures() {
        return creatures;
    }

    public void setCreatures(SortedSet<Creature> creatures) {
        this.creatures = creatures;
    }

    public SortedSet<Duration> getDurations() {
        return durations;
    }

    public void setDurations(SortedSet<Duration> durations) {
        this.durations = durations;
    }

    public SortedSet<Mechanics> getMechanics() {
        return mechanics;
    }

    public void setMechanics(SortedSet<Mechanics> mechanics) {
        this.mechanics = mechanics;
    }

    public SortedSet<Genre> getGenres() {
        return genres;
    }

    public void setGenres(SortedSet<Genre> genres) {
        this.genres = genres;
    }

    public SortedSet<Location> getLocations() {
        return locations;
    }

    public void setLocations(SortedSet<Location> locations) {
        this.locations = locations;
    }

    public SortedSet<Period> getPeriods() {
        return periods;
    }

    public void setPeriods(SortedSet<Period> periods) {
        this.periods = periods;
    }

    public SortedSet<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(SortedSet<Organization> organizations) {
        this.organizations = organizations;
    }

    public SortedSet<Soundtrack> getSoundtracks() {
        return soundtracks;
    }

    public void setSoundtracks(SortedSet<Soundtrack> soundtracks) {
        this.soundtracks = soundtracks;
    }

    public SortedSet<SportTeam> getSportTeams() {
        return sportTeams;
    }

    public void setSportTeams(SortedSet<SportTeam> sportTeams) {
        this.sportTeams = sportTeams;
    }

    public SortedSet<Theme> getThemes() {
        return themes;
    }

    public void setThemes(SortedSet<Theme> themes) {
        this.themes = themes;
    }

    public SortedSet<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(SortedSet<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public SortedSet<Weapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(SortedSet<Weapon> weapons) {
        this.weapons = weapons;
    }

    public SortedSet<Engine> getEngines() {
        return engines;
    }

    public void setEngines(SortedSet<Engine> engines) {
        this.engines = engines;
    }

    public SortedSet<GameMode> getGameModes() {
        return gameModes;
    }

    public void setGameModes(SortedSet<GameMode> gameModes) {
        this.gameModes = gameModes;
    }

    public SortedSet<Graphics> getGraphics() {
        return graphics;
    }

    public void setGraphics(SortedSet<Graphics> graphics) {
        this.graphics = graphics;
    }

    public SortedSet<InputDevice> getInputDevices() {
        return inputDevices;
    }

    public void setInputDevices(SortedSet<InputDevice> inputDevices) {
        this.inputDevices = inputDevices;
    }

    public SortedSet<NumberOfPlayers> getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(SortedSet<NumberOfPlayers> numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

}
