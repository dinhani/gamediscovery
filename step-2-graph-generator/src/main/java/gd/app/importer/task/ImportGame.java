package gd.app.importer.task;

import com.google.common.primitives.Ints;
import gd.app.importer.model.Node;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.RelationshipRelevanceBoost;
import gd.domain.entities.entity.TreeSetWithAttributes;
import gd.domain.entities.entity.gameplay.Duration;
import gd.domain.entities.entity.gameplay.Engine;
import gd.domain.entities.entity.gameplay.GameMode;
import gd.domain.entities.entity.gameplay.Genre;
import gd.domain.entities.entity.gameplay.Graphics;
import gd.domain.entities.entity.gameplay.InputDevice;
import gd.domain.entities.entity.gameplay.NumberOfPlayers;
import gd.domain.entities.entity.industry.Award;
import gd.domain.entities.entity.industry.Company;
import gd.domain.entities.entity.industry.ContentCharacteristic;
import gd.domain.entities.entity.industry.ContentClassification;
import gd.domain.entities.entity.industry.Game;
import gd.domain.entities.entity.industry.Media;
import gd.domain.entities.entity.industry.Person;
import gd.domain.entities.entity.industry.Platform;
import gd.domain.entities.entity.industry.ReleaseYear;
import gd.domain.entities.entity.industry.Review;
import gd.domain.entities.entity.industry.Series;
import gd.domain.entities.entity.plot.Creature;
import gd.domain.entities.entity.plot.Location;
import gd.domain.entities.entity.plot.Period;
import gd.domain.entities.entity.plot.Theme;
import gd.domain.entities.entity.plot.Vehicle;
import gd.infrastructure.steriotype.GDTask;
import gd.infrastructure.uid.UID;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

@GDTask
public class ImportGame extends ImportTask {

    @Autowired
    private UID uid;

    // node
    public static final String GAME_ID = "Q7889";

    // wikidata
    private static final String CHARACTERS_ID = "P674";
    private static final String COMPOSER_ID = "P86";
    private static final String DEVELOPER_COMPANY_ID = "P178";
    private static final String DISTRIBUTION_ID = "P437";
    private static final String DIRECTOR_ID = "P57";
    private static final String ENGINE_ID = "P408";
    private static final String GAME_MODE_ID = "P404";
    private static final String GENRE_ID = "P136";
    private static final String INPUT_DEVICE_ID = "P479";
    private static final String LOCATION_ID = "P840";
    private static final String PLATFORM_ID = "P400";
    private static final String PRODUCER_ID = "P162";
    private static final String PUBLICATION_ID = "P577";
    private static final String PUBLISHER_COMPANY_ID = "P123";
    private static final String RATING_ESRB_ID = "P852";
    private static final String RATING_PEGI_ID = "P908";
    private static final String SERIES_ID = "P179";

    // wikdiata_specific
    private static final String GRAPHIC_ADVENTURE_ID = "Q2628513";
    private static final String INTERACTIVE_MOVIE_ID = "Q1635956";
    private static final String PERSONAL_COMPUTER_ID = "Q16338";
    private static final String WINDOWS_ID = "Q1406";

    // dbpedia / mobygames
    private static final String ARTIST = "artist";
    private static final String COMPOSER = "composer";
    private static final String DIRECTOR = "director";
    private static final String PRODUCER = "producer";
    private static final String PROGRAMMER = "programmer";
    private static final String WRITER = "writer";
    //
    private static final String GAMESPOT = "gspot";
    private static final String IGN = "ign";
    //
    // mobygames
    private static final String CREATURE = "creature";
    private static final String DEVELOPER = "developer";
    private static final String DURATION = "duration";
    private static final String ENGINE = "engine";
    private static final String GRAPHICS = "graphics";
    private static final String GENRE = "genre";
    private static final String LOCATION = "location";
    private static final String MODE = "mode";
    private static final String MEDIA = "media";
    private static final String PACING = "pacing";
    private static final String PLATFORM = "platforms";
    private static final String PUBLISHER = "publisher";
    private static final String RELEASE_YEAR = "release_year";
    private static final String SERIES = "series";
    private static final String THEME = "theme";
    private static final String VEHICLE = "vehicle";

    // wikipedia
    private static final String NUMBER_OF_PLAYERS = "number_of_players";
    private static final String SCREEN_TYPE = "screen_type";

    @Override
    public Class<? extends ConceptEntry> getTargetClass() {
        return Game.class;
    }

    @Override
    public Collection<Node> getNodesToCreate() {
        // wikidata
        Collection<Node> games = jdbc.query()
                .forGame()
                .attributesKeys(INSTANCE_OF)
                .attributesValues(GAME_ID)
                .findWikidataEntities();

        // set aliases
        for (Node game : games) {
            setAliases(game);
        }

        // return games
        return games;
    }

    // =========================================================================
    // ALIAS SETTERS
    // =========================================================================
    private void setAliases(Node node) {
        boolean processedRoman = false;

        // roman numerals variations
        if (!processedRoman && node.getName().contains("IV")) {
            processedRoman = true;
            node.addAlias(node.getName().replace("IV", "4"));
        }
        if (!processedRoman && node.getName().contains("III")) {
            processedRoman = true;
            node.addAlias(node.getName().replace("III", "3"));
        }
        if (!processedRoman && node.getName().contains("II")) {
            processedRoman = true;
            node.addAlias(node.getName().replace("II", "2"));
        }
    }

    @Override
    public ConceptEntry getRelationshipsToCreate(Node node) {

        Game game = new Game();
        game.setUid(node.getGeneratedId());

        // relationships
        setAtmospheres(node, game);
        setAwards(node, game);
        setCharacters(node, game);
        setCreatures(node, game);
        setContentCharacteristics(node, game);
        setContentClassifications(node, game);
        setCompanies(node, game);
        setDuration(node, game);
        setDevelopers(node, game);
        setEngines(node, game);
        setGameModes(node, game);
        setGenres(node, game);
        setInputDevices(node, game);
        setLocations(node, game);
        setMechanics(node, game);
        setNumberOfPlayers(node, game);
        setPlatforms(node, game);
        setPlatformExclusives(node, game);
        setPeriods(node, game);
        setOrganizations(node, game);
        setReleaseDates(node, game);
        setSeries(node, game);
        setSoundtracks(node, game);
        setReviews(node, game);
        setSportTeams(node, game);
        setThemes(node, game);
        setVehicles(node, game);
        setWeapons(node, game);

        // dependent of platforms
        setMedias(node, game);

        // dependent of genres
        setGraphics(node, game);

        return game;
    }

    // =========================================================================
    // RELATIONSHIP SETTERS
    // =========================================================================
    private void setAtmospheres(Node node, Game game) {
        importNodes(game.getAtmospheres(), ImportAtmosphere.class, node);
    }

    private void setAwards(Node node, Game game) {
        if (node.isWikipediaGoty()) {
            game.getAwards().add(ImportAward.GAME_OF_THE_YEAR.getConceptEntry(Award.class));
        }
    }

    private void setCreatures(Node node, Game game) {
        // mobygames
        game.getCreatures().addAll(cache.findEntriesByNames(Creature.class, node.getMobygamesAttributes(CREATURE)));

        // predefined
        importNodes(game.getCreatures(), ImportCreature.class, node);
    }

    private void setContentCharacteristics(Node node, Game game) {
        // esrb
        game.setContentCharacteristics(cache.findEntriesByNames(ContentCharacteristic.class, node.getEsrbCategories()));
    }

    private void setContentClassifications(Node node, Game game) {
        // wikidata
        game.setContentClassifications(cache.findEntriesByWikidataIds(ContentClassification.class, node.getWikidataAttributes(RATING_ESRB_ID, RATING_PEGI_ID)));

        // esrb
        if (node.getEsrbClassification() != null) {
            switch (node.getEsrbClassification()) {
                case "EC":
                    game.getContentClassifications().add((ContentClassification) ImportContentClassification.EARLY_CHILDHOOD.getConceptEntry());
                    break;
                case "E":
                    game.getContentClassifications().add((ContentClassification) ImportContentClassification.EVERYONE.getConceptEntry());
                    break;
                case "E10plus":
                    game.getContentClassifications().add((ContentClassification) ImportContentClassification.EVERYONE.getConceptEntry());
                    break;
                case "T":
                    game.getContentClassifications().add((ContentClassification) ImportContentClassification.TEEN.getConceptEntry());
                    break;
                case "M":
                    game.getContentClassifications().add((ContentClassification) ImportContentClassification.MATURE.getConceptEntry());
                    break;
                case "AO":
                    game.getContentClassifications().add((ContentClassification) ImportContentClassification.ADULTS_ONLY.getConceptEntry());
                    break;
            }
        }
    }

    private void setCharacters(Node node, Game game) {
        // wikidata
        game.setCharacters(cache.findEntriesByWikidataIds(gd.domain.entities.entity.plot.Character.class, node.getWikidataAttributes(CHARACTERS_ID)));

        // predefined
        importNodes(game.getCharacters(), ImportCharacter.class, node);
    }

    private void setCompanies(Node node, Game game) {
        // developer
        game.setDeveloperCompanies(cache.findEntriesByWikidataIds(Company.class, node.getWikidataAttributes(DEVELOPER_COMPANY_ID)));
        game.getDeveloperCompanies().addAll(cache.findEntriesByNames(Company.class, node.getMobygamesAttributes(DEVELOPER)));
        game.getDeveloperCompanies().addAll(cache.findEntriesByIds(Company.class, node.getDBPediaAttributes(DEVELOPER)));
        if (game.getDeveloperCompanies().isEmpty()) {
            game.getDeveloperCompanies().addAll(cache.findEntriesByName(Company.class, (String) node.getLaunchboxAttributes().get(DEVELOPER)));
        }

        // publisher
        game.setPublisherCompanies(cache.findEntriesByWikidataIds(Company.class, node.getWikidataAttributes(PUBLISHER_COMPANY_ID)));
        game.getPublisherCompanies().addAll(cache.findEntriesByNames(Company.class, node.getMobygamesAttributes(PUBLISHER)));
        game.getPublisherCompanies().addAll(cache.findEntriesByIds(Company.class, node.getDBPediaAttributes(PUBLISHER)));
        if (game.getPublisherCompanies().isEmpty()) {
            game.getPublisherCompanies().addAll(cache.findEntriesByName(Company.class, (String) node.getLaunchboxAttributes().get(PUBLISHER)));
        }
        if (game.getDeveloperCompanies().isEmpty()) {
            game.setDeveloperCompanies(game.getPublisherCompanies());
        }

        // handle special cases (developer)
        if (containsRegex(game.getDeveloperCompanies(), ".*2k-.*")) {
            importNodes(game.getDeveloperCompanies(), ImportCompany._2K);
        }
        if (containsRegex(game.getDeveloperCompanies(), ".*rockstar-.*")) {
            importNodes(game.getDeveloperCompanies(), ImportCompany.ROCKSTAR);
        }
        if (containsRegex(game.getDeveloperCompanies(), ".*ubisoft-.*")) {
            importNodes(game.getDeveloperCompanies(), ImportCompany.UBISOFT);
        }

        // handle special cases (publisher)
        if (containsRegex(game.getPublisherCompanies(), ".*2k-.*")) {
            importNodes(game.getPublisherCompanies(), ImportCompany._2K);
        }
        if (containsRegex(game.getPublisherCompanies(), ".*rockstar-.*")) {
            importNodes(game.getPublisherCompanies(), ImportCompany.ROCKSTAR);
        }
        if (containsRegex(game.getPublisherCompanies(), ".*ubisoft-.*")) {
            importNodes(game.getPublisherCompanies(), ImportCompany.UBISOFT);
        }
    }

    private void setDuration(Node node, Game game) {
        String durationAsStr = (String) node.getHltbAttributes().get(DURATION);

        // no value checks
        if (StringUtils.isBlank(durationAsStr)) {
            return;
        }
        if (durationAsStr.equals("--")) {
            return;
        }
        // parse
        double duration;
        if (durationAsStr.contains("Mins")) {
            duration = 1.0;
        } else {
            duration = NumberUtils.toDouble(durationAsStr, 0);
        }
        // checks
        if (duration == 0) {
            return;
        }
        // set
        if (duration <= 4) {
            game.getDurations().add(ImportDuration.DURATION_QUICK.getConceptEntry(Duration.class));
        } else if (duration <= 10) {
            game.getDurations().add(ImportDuration.DURATION_SHORT.getConceptEntry(Duration.class));
        } else if (duration <= 20) {
            game.getDurations().add(ImportDuration.DURATION_AVERAGE.getConceptEntry(Duration.class));
        } else {
            game.getDurations().add(ImportDuration.DURATION_LONG.getConceptEntry(Duration.class));
        }

    }

    private void setDevelopers(Node node, Game game) {
        // wikidata
        game.setDevelopers(cache.findEntriesByWikidataIds(Person.class, node.getWikidataAttributes(COMPOSER_ID, DIRECTOR_ID, PRODUCER_ID)));

        // dbpedia
        game.getDevelopers().addAll(cache.findEntriesByIds(Person.class, node.getDBPediaAttributes(ARTIST, COMPOSER, DIRECTOR, PRODUCER, PROGRAMMER, WRITER)));
    }

    private void setEngines(Node node, Game game) {
        // wikidata
        game.setEngines(cache.findEntriesByWikidataIds(Engine.class, node.getWikidataAttributes(ENGINE_ID)));

        // mobygames
        game.getEngines().addAll(cache.findEntriesByNames(Engine.class, node.getMobygamesAttributes(ENGINE)));

        // dbpedia
        game.getEngines().addAll(cache.findEntriesByIds(Engine.class, node.getDBPediaAttributes(ENGINE)));
        if (node.getDbpediaCategories().contains("unreal_engine_games")) {
            game.getEngines().add(ImportEngine.UNREAL.getConceptEntry(Engine.class));
        }
        if (node.getDbpediaCategories().contains("unity_(game_engine)_games")) {
            game.getEngines().add(ImportEngine.UNITY.getConceptEntry(Engine.class));
        }

    }

    private void setGameModes(Node node, Game game) {
        // wikidata
        game.setGameModes(cache.findEntriesByWikidataIds(GameMode.class, node.getWikidataAttributes(GAME_MODE_ID)));

        // dbpedia
        game.getGameModes().addAll(cache.findEntriesByIds(GameMode.class, node.getDBPediaAttributes(MODE)));
        if (node.getDbpediaCategories().contains("multiplayer_online_games") || node.getDbpediaCategories().contains("assively_multiplayer_online_games")) {
            game.getGameModes().add(ImportGameMode.GAME_MODE_ONLINE.getConceptEntry(GameMode.class));
        }

        // static
        importNodes(game.getGameModes(), ImportGameMode.class, node);

        // wikipedia
        if (node.getWikipediaCooperativeAttributes(SCREEN_TYPE).contains("shared")) {
            game.getGameModes().add(ImportGameMode.GAME_MODE_SHARED_SCREEN.getConceptEntry(GameMode.class));
            game.getGameModes().add(ImportGameMode.GAME_MODE_LOCAL_MULTIPLAYER.getConceptEntry(GameMode.class));
        }
        if (node.getWikipediaCooperativeAttributes(SCREEN_TYPE).contains("split")) {
            game.getGameModes().add(ImportGameMode.GAME_MODE_SPLIT_SCREEN.getConceptEntry(GameMode.class));
            game.getGameModes().add(ImportGameMode.GAME_MODE_LOCAL_MULTIPLAYER.getConceptEntry(GameMode.class));
        }
    }

    private void setGenres(Node node, Game game) {
        // 1) WIKIDATA
        Collection<String> wikidataGenres = node.getWikidataAttributes(GENRE_ID);
        if (wikidataGenres.contains(INTERACTIVE_MOVIE_ID)) {
            wikidataGenres.add(GRAPHIC_ADVENTURE_ID);
        }
        game.setGenres(cache.findEntriesByWikidataIds(Genre.class, wikidataGenres));

        // 2) DBPEDIA
        game.getGenres().addAll(cache.findEntriesByIds(Genre.class, node.getDBPediaAttributes(GENRE)));

        // 3) MOBYGAMES
        Collection<String> mobygamesGenres = node.getMobygamesAttributes(GENRE);
        game.getGenres().addAll(cache.findEntriesByNames(Genre.class, mobygamesGenres));

        if (mobygamesGenres.contains("Cards / Tiles")) {
            importNodes(game.getGenres(), ImportGenre.CARD);
        }
        if (mobygamesGenres.contains("Puzzle-Solving")) {
            importNodes(game.getGenres(), ImportGenre.PUZZLE);
        }
        if (mobygamesGenres.contains("Racing / Driving")) {
            importNodes(game.getGenres(), ImportGenre.RACING);
        }
        if (mobygamesGenres.contains("Rhythm / Music")) {
            importNodes(game.getGenres(), ImportGenre.MUSIC);
        }
        if (mobygamesGenres.contains("Role-Playing (RPG)")) {
            importNodes(game.getGenres(), ImportGenre.RPG);
            if (mobygamesGenres.contains("Action")) {
                importNodes(game.getGenres(), ImportGenre.ACTION_RPG);
            }
        }
        if (mobygamesGenres.contains("Strategy/Tactics")) {
            importNodes(game.getGenres(), ImportGenre.STRATEGY);
        }

        // =====================================================================
        // 4) SPECIAL CASES
        // flight simulators
        if (contains(game.getGenres(), "genre-amateur-flight-simulation") || contains(game.getGenres(), "genre-combat-flight-simulator")) {
            importNodes(game.getGenres(), ImportGenre.FLIGHT);
        }
        // puzzle
        if (contains(game.getGenres(), "genre-puzzle-game")) {
            importNodes(game.getGenres(), ImportGenre.PUZZLE);
            remove(game.getGenres(), "genre-puzzle-game");
        }
        // racing
        if (contains(game.getGenres(), "genre-sim-racing")) {
            importNodes(game.getGenres(), ImportGenre.RACING, ImportGenre.SIMULATION);
            remove(game.getGenres(), "genre-sim-racing");
        }
        // rpg
        if (contains(game.getGenres(), "genre-real-time-strategy")) {
            importNodes(game.getGenres(), ImportGenre.RPG);
        }
        // strategy
        if (contains(game.getGenres(), "genre-real-time-tactics")) {
            importNodes(game.getGenres(), ImportGenre.REAL_TIME_STRATEGY);
            remove(game.getGenres(), "genre-real-time-tactics");
        }
        if (contains(game.getGenres(), "genre-real-time-strategy")) {
            importNodes(game.getGenres(), ImportGenre.STRATEGY);
        }
        // survival horror
        if (contains(game.getGenres(), "genre-psychological-horror")) {
            importNodes(game.getGenres(), ImportGenre.SURVIVAL_HORROR);
            remove(game.getGenres(), "genre-psychological-horror");
        }

        // =====================================================================
        // 5) REMOVE OVERUSED GENRES
        if (contains(game.getGenres(), "genre-first-person-shooter", "genre-third-person-shooter", "genre-tactical-shooter")) {
            remove(game.getGenres(), "genre-shooter");
            remove(game.getGenres(), "genre-action");
        }
        if (contains(game.getGenres(), "genre-action-adventure", "genre-action-role-playing", "genre-fighting", "genre-beat-em-up")) {
            remove(game.getGenres(), "genre-action");
        }
    }

    private void setGraphics(Node node, Game game) {
        // dbpedia
        game.setGraphics(cache.findEntriesByIds(Graphics.class, node.getDbpediaCategories()));
        if (node.getDbpediaCategories().contains("video_games_with_cel-shaded_animation")) {
            importNodes(game.getGraphics(), ImportGraphics.CEL_SHADED);
        }

        // mobygames
        Collection<String> mobygamesGraphics = node.getMobygamesAttributes(GRAPHICS);
        game.getGraphics().addAll(cache.findEntriesByNames(Graphics.class, mobygamesGraphics));

        // mobygames special cases
        if (mobygamesGraphics.contains("1st-person")) {
            importNodes(game.getGraphics(), ImportGraphics.FIRST_PERSON);
        }
        if (mobygamesGraphics.contains("3rd-person") || mobygamesGraphics.contains("3rd-person [DEPRECATED]")) {
            importNodes(game.getGraphics(), ImportGraphics.THIRD_PERSON);
        }
        if (mobygamesGraphics.contains("Top-down")) {
            importNodes(game.getGraphics(), ImportGraphics.TOP_DOWN);
        }
        if (mobygamesGraphics.contains("Side view")) {
            importNodes(game.getGraphics(), ImportGraphics.SIDE_VIEW);
        }
        if (mobygamesGraphics.contains("Animé / Manga")) {
            importNodes(game.getGraphics(), ImportGraphics.ANIME);
        }
        if (mobygamesGraphics.contains("Cel shaded")) {
            importNodes(game.getGraphics(), ImportGraphics.CEL_SHADED);
        }

        // predefined
        importNodes(game.getGraphics(), ImportGraphics.class, node);
    }

    private void setInputDevices(Node node, Game game) {
        // wikidata
        game.setInputDevices(cache.findEntriesByWikidataIds(InputDevice.class, node.getWikidataAttributes(INPUT_DEVICE_ID)));

        // dbpedia
        if (node.getDbpediaCategories().contains("kinect_games")) {
            importNodes(game.getInputDevices(), ImportInputDevice.KINECT);
        }
        if (node.getDBPediaAttributes().contains("eyetoy_games")) {
            importNodes(game.getInputDevices(), ImportInputDevice.EYETOY);
        }
        if (node.getDBPediaAttributes().contains("microphone-controlled_computer_games")) {
            importNodes(game.getInputDevices(), ImportInputDevice.MICROPHONE);
        }

        // predefined
        importNodes(game.getInputDevices(), ImportInputDevice.class, node);

    }

    private void setLocations(Node node, Game game) {
        // wikidata
        game.setLocations(cache.findEntriesByWikidataIds(Location.class, node.getWikidataAttributes(LOCATION_ID)));

        // dbpedia
        game.getLocations().addAll(cache.findEntriesByIds(Location.class, node.getDbpediaCategories()));

        // mobygames
        game.getLocations().addAll(cache.findEntriesByNames(Location.class, node.getMobygamesAttributes(LOCATION)));

        //predefined
        importNodes(game.getLocations(), ImportLocation.class, node);
    }

    private void setMechanics(Node node, Game game) {
        // predefined
        importNodes(game.getMechanics(), ImportMechanics.class, node);

        // mobygames
        if (node.getMobygamesAttributes(PACING).contains("Turn-based")) {
            importNodes(game.getMechanics(), ImportMechanics.TURN_BASED);
        }
    }

    private void setMedias(Node node, Game game) {
        // wikidata
        game.setMedias(cache.findEntriesByWikidataIds(Media.class, node.getWikidataAttributes(DISTRIBUTION_ID)));
        // dbpedia
        game
                .getMedias().addAll(cache.findEntriesByIds(Media.class, node.getDBPediaAttributes(MEDIA)));

        // predefined
        // cartdrige
        if (contains(game.getPlatforms(),
                "platform-super-nintendo-entertainment-system",
                "platform-sega-genesis",
                "platform-nintendo-64")) {
            game.getMedias().add(ImportMedia.CARTDRIGE.getConceptEntry(Media.class
            ));

        }

        // cd
        if (contains(game.getPlatforms(), "platform-playstation")) {
            game.getMedias().add(ImportMedia.CD.getConceptEntry(Media.class
            ));

        }

        // dvd
        if (contains(game.getPlatforms(), "platform-playstation-2", "platform-xbox")) {
            game.getMedias().add(ImportMedia.DVD.getConceptEntry(Media.class
            ));

        }

        // digital
        if (contains(game.getPlatforms(), "platform-android-operating-system", "platform-ios")) {
            game.getMedias().add(ImportMedia.DIGITAL.getConceptEntry(Media.class
            ));
        }

    }

    private void setNumberOfPlayers(Node node, Game game) {
        int numberOfPlayersFromTheGamesDB = node.getThegamesdbAttributes().containsKey(NUMBER_OF_PLAYERS) ? NumberUtils.toInt(node.getThegamesdbAttributes().get(NUMBER_OF_PLAYERS).get(0), 0) : 0;
        int numberOfPlayersFromWikipedia = node.getWikipediaCooperativeAttributes().containsKey(NUMBER_OF_PLAYERS) ? NumberUtils.toInt(node.getWikipediaCooperativeAttributes().get(NUMBER_OF_PLAYERS).get(0).replaceAll("[^0-9]", ""), 0) : 0;
        int numberOfPlayers = Ints.max(numberOfPlayersFromTheGamesDB, numberOfPlayersFromWikipedia);

        for (int playerId = 1; playerId <= numberOfPlayers; playerId++) {
            game.getNumberOfPlayers().addAll(cache.findEntriesById(NumberOfPlayers.class, playerId + ""));

        }
    }

    private void setPlatforms(Node node, Game game) {
        // 1) WIKIDATA
        Collection<String> wikidataIds = node.getWikidataAttributes(PLATFORM_ID);
        if (wikidataIds.contains(PERSONAL_COMPUTER_ID)) {
            wikidataIds.add(WINDOWS_ID);
        }
        game.setPlatforms(cache.findEntriesByWikidataIds(Platform.class, node.getWikidataAttributes(PLATFORM_ID)));

        // 2) MOBYGAMES
        game.getPlatforms().addAll(cache.findEntriesByNames(Platform.class, node.getMobygamesAttributes(PLATFORM)));

        // 3) DBPEDIA
        game.getPlatforms().addAll(cache.findEntriesByIds(Platform.class, node.getDBPediaAttributes(PLATFORM)));

        // 4) JOIN PLATFORMS
        // WINDOWS
        boolean removedWindows = game.getPlatforms().removeIf(platform -> platform.getUid().contains("windows"));
        if (removedWindows) {
            importNodes(game.getPlatforms(), ImportPlatform.WINDOWS);
        }
        // MAC
        if (contains(game.getPlatforms(), "platform-os-x")) {
            importNodes(game.getPlatforms(), ImportPlatform.MAC);
            remove(game.getPlatforms(), "platform-os-x");
        }
    }

    private void setPlatformExclusives(Node node, Game game) {
        Collection<Platform> p = game.getPlatforms();

        // single platform
        boolean exclusive = game.getPlatforms().size() == 1;

        // playstation
        exclusive = exclusive || p.stream().allMatch(platform -> platform.getUid().contains("playstation"));

        // xbox / windows
        exclusive = exclusive || p.stream().allMatch(platform -> platform.getUid().contains("xbox") || platform.getUid().contains("windows"));

        // nintendo
        exclusive = exclusive || p.stream().allMatch(platform -> platform.getUid().contains("nintendo") || platform.getUid().contains("nes") || platform.getUid().contains("wii"));

        // sega
        exclusive = exclusive || p.stream().allMatch(platform -> platform.getUid().contains("sega"));

        // set as exclusive
        if (exclusive) {
            for (Platform platform : game.getPlatforms()) {
                ((TreeSetWithAttributes) game.getPlatforms()).setRelevanceModifier(platform, RelationshipRelevanceBoost.HIGH);

            }
        }
    }

    private void setPeriods(Node node, Game game) {
        // dbpedia
        game.setPeriods(cache.findEntriesByIds(Period.class, node.getDbpediaCategories()));

        // mobygames
        if (node.getMobygamesAttributes(THEME).contains("Sci-Fi / Futuristic")) {
            game.getPeriods().add((Period) ImportPeriod.FUTURE.getConceptEntry());
        }

        // predefined
        importNodes(game.getPeriods(), ImportPeriod.class, node);
    }

    private void setOrganizations(Node node, Game game) {
        // static
        importNodes(game.getOrganizations(), ImportOrganization.class, node);
    }

    private void setReleaseDates(Node node, Game game) {
        // wikidata
        if (node.getWikidataAttributes().containsKey(PUBLICATION_ID)) {
            List<String> years = node.getWikidataAttributes().get(PUBLICATION_ID).stream()
                    .map(p -> {
                        String year = p.split("-")[0];
                        return year.length() > 0 ? year.substring(1) : "";
                    }).collect(Collectors.toList());
            game
                    .setReleaseYears(cache.findEntriesByIds(ReleaseYear.class, years));
        }

        // dbpedia
        if (node.getDbpediaCategories().size() > 0) {
            String possibleYear = node.getDbpediaCategories().get(0); // year is usually the first category
            if (possibleYear.length() >= 4) {
                possibleYear = possibleYear.substring(0, 4);
                game
                        .getReleaseYears().addAll(cache.findEntriesById(ReleaseYear.class, possibleYear));

            }
        }

        // launchbox
        game.getReleaseYears().addAll(cache.findEntriesByName(ReleaseYear.class, (String) node.getLaunchboxAttributes().get(RELEASE_YEAR)));

        // cancelled
        if (game.getReleaseYears().isEmpty()) {
            if (containsRegexStr(node.getDbpediaCategories(), "cancelled.*games")) {
                game.getReleaseYears().add((ReleaseYear) ImportReleaseYear.CANCELLED.getConceptEntry());
            }
        }
    }

    private void setReviews(Node node, Game game) {
        SortedSet<Review> scores = new TreeSetWithAttributes<>();
        // gamespot
        if (node.getGamespotScore() != null) {
            String gamespotScoreId = "gamespot_" + node.getGamespotScore();
            scores
                    .addAll(cache.findEntriesById(Review.class, gamespotScoreId));

        } else if (node.getDbpediaAttributes().containsKey(GAMESPOT)) {
            double score = node.getDbpediaAttributes().get(GAMESPOT).stream().mapToDouble(d -> NumberUtils.toDouble(d, 0)).max().getAsDouble();
            if (score > 0) {
                String gamespotScoreId = "gamespot_" + score;
                scores
                        .addAll(cache.findEntriesById(Review.class, gamespotScoreId));
            }
        }
        // ign
        if (node.getIgnScore() != null) {
            String gamespotScoreId = "ign_" + node.getIgnScore();
            scores
                    .addAll(cache.findEntriesById(Review.class, gamespotScoreId));
        } else if (node.getDbpediaAttributes().containsKey(IGN)) {
            double score = node.getDbpediaAttributes().get(IGN).stream().mapToDouble(d -> NumberUtils.toDouble(d, 0)).max().getAsDouble();
            if (score > 0) {
                String ignScoreId = "ign_" + score;
                scores
                        .addAll(cache.findEntriesById(Review.class, ignScoreId));
            }
        }
        game.setReviews(scores);

    }

    private void setSportTeams(Node node, Game game) {
        // predefined
        importNodes(game.getSportTeams(), ImportSportTeam.class, node);

    }

    private void setSeries(Node node, Game game) {
        // wikidata
        game.setSeries(cache.findEntriesByWikidataIds(Series.class, node.getWikidataAttributes(SERIES_ID)));

        // dbpedia
        Collection<String> series = node.getDBPediaAttributes(SERIES);
        game.getSeries().addAll(cache.findEntriesByIds(Series.class, series));
        game.getSeries().addAll(cache.findEntriesByNames(Series.class, series));

        // mobygames
        game.getSeries().addAll(cache.findEntriesByNames(Series.class, node.getMobygamesAttributes(SERIES)));

        // predefined
        importNodes(game.getSeries(), ImportSeries.class, node);
    }

    private void setSoundtracks(Node node, Game game) {
        importNodes(game.getSoundtracks(), ImportSoundtrack.class, node);

    }

    private void setThemes(Node node, Game game) {
        // dbpedia
        game.setThemes(cache.findEntriesByIds(Theme.class, node.getDbpediaCategories()));

        // mobygames
        Collection<String> themes = node.getMobygamesAttributes(THEME);
        game.getThemes().addAll(cache.findEntriesByNames(Theme.class, themes));

        // mobygames special cases
        if (themes.contains("Snowboarding / Skiing")) {
            game.getThemes().add(ImportTheme.SNOWBOARDING.getConceptEntry(Theme.class));
        }
        if (themes.contains("Wrestling")) {
            game.getThemes().add(ImportTheme.WRESTLING.getConceptEntry(Theme.class));
        }
        if (themes.contains("Olympiad / Mixed Sports")) {
            game.getThemes().add(ImportTheme.OLYMPIAD.getConceptEntry(Theme.class));
        }

        // predefined
        importNodes(game.getThemes(), ImportTheme.class, node);
    }

    private void setVehicles(Node node, Game game) {
        // predefined
        importNodes(game.getVehicles(), ImportVehicle.class, node);

        // mobygames
        game.getVehicles().addAll(cache.findEntriesByNames(Vehicle.class, node.getMobygamesAttributes(VEHICLE)));
    }

    private void setWeapons(Node node, Game game) {
        importNodes(game.getWeapons(), ImportWeapon.class, node);
    }
}
