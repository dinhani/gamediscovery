package gd.domain.entities;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.RelationshipRelevanceBoost;
import gd.domain.entities.factory.ConceptEntryFactory;
import gd.infrastructure.di.DIService;
import gd.infrastructure.serialization.Json;
import gd.infrastructure.uid.UID;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

public class Node implements Serializable {

    // SERVICES
    private static Json JSON = DIService.getBean(Json.class);
    private static UID UID = DIService.getBean(UID.class);

    // PREDEFINED NODES DATA
    private ConceptEntry conceptEntry;
    private String[] keywords;
    private int minOcurrencesForSearchTerm = 3;
    private boolean shouldCheckName = false;
    private boolean shouldBoostRelevance = false;

    // BASIC DATA
    private String generatedId = "";                              // the id that will be persisted in Neo4J    
    private String id = "";                                       // the id generated from wikipedia link or generated from the name
    private String idWikipedia = "";                              // the Wikipedia ID (it is in the URL)
    private List<String> alternativeIds = Collections.EMPTY_LIST; // alternative ids generated from the id
    private String image = "";                                    // the image filename (it is using the same value of idWikipedia, but without the case normalization)
    private String name = "";                                     // the name from wikidata
    private final Set<String> aliases = Sets.newHashSet();        // name aliases    

    // DBPEDIA
    private Map<String, List<String>> dbpediaAttributes = Collections.EMPTY_MAP;
    private List<String> dbpediaCategories = Collections.EMPTY_LIST;

    // ESRB
    private List<String> esrbCategories = Collections.EMPTY_LIST;
    private String esrbClassification;

    // HLTB    
    private Map<String, Object> hltbAttributes = Collections.EMPTY_MAP;

    // IGDB
    private Map<String, List<String>> igdbAttributes = Collections.EMPTY_MAP;

    // IGN
    private String ignScore;

    // GAMESPOT
    private String gamespotScore;

    // GIANTBOMB
    private Map<String, Integer> giantbombWordCount = Collections.EMPTY_MAP;

    // LAUNCHBOX
    private Map<String, Object> launchboxAttributes = Collections.EMPTY_MAP;
    private String launchboxSummary;
    private String launchboxVideo;

    // MOBYGAMES
    private Map<String, List<String>> mobygamesAttributes = Collections.EMPTY_MAP;

    // PSN TROPHIES
    private List<String> psnTrophies = Collections.EMPTY_LIST;

    // THE GAMES DB
    private Map<String, List<String>> thegamesdbAttributes = Collections.EMPTY_MAP;

    private List<String> xboxTrophies = Collections.EMPTY_LIST;

    // WIKIDATA
    private String wikidataId = "";
    private Map<String, List<String>> wikidataAttributes = Collections.EMPTY_MAP;

    // WIKIPEDIA
    private Map<String, List<String>> wikipediaAttributes = Collections.EMPTY_MAP;
    private Map<String, List<String>> wikipediaCooperativeAttributes = Collections.EMPTY_MAP;
    private Map<String, Integer> wikipediaEnWordCount = Collections.EMPTY_MAP;
    private Map<String, Integer> wikipediaPtWordCount = Collections.EMPTY_MAP;
    private boolean wikipediaGoty;

    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================
    // USED BY SPRING QUERY
    public Node() {
    }

    // USED BY DOMAIN
    public Node(String id, String name) {
        setId(id);
        setName(name);
    }

    // USED BY FACTORY METHOD
    public Node(ConceptEntry conceptEntry, String[] keywords) {
        String parsedId = StringUtils.split(conceptEntry.getUid(), "-", 2)[1];

        // basic data
        this.id = parsedId;
        this.name = conceptEntry.getName();

        // used for search
        this.conceptEntry = conceptEntry;
        this.keywords = keywords;
    }

    // =========================================================================
    // OBJECT METHODS
    // =========================================================================
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node) {
            return this.id.equals(((Node) obj).id);
        }
        if (obj instanceof String) {
            return this.id.equals(obj);
        }

        return false;
    }

    // =========================================================================
    // FACTORY
    // =========================================================================
    public static Node withPredefinedId(Class<? extends ConceptEntry> type, String id, String... searchTerms) {
        return withPredefinedIdAndName(type, id, "", searchTerms);
    }

    public static Node withPredefinedName(Class<? extends ConceptEntry> type, String name, String... searchTerms) {
        return withPredefinedIdAndName(type, "", name, searchTerms);
    }

    public static Node withPredefinedIdAndName(Class<? extends ConceptEntry> type, String id, String name, String... searchTerms) {

        // createFromName concept entry
        ConceptEntry conceptEntry = null;
        // id
        if (StringUtils.isNotBlank(id) && StringUtils.isBlank(name)) {
            conceptEntry = ConceptEntryFactory.createFromId(type, id);
        }
        // name
        if (StringUtils.isBlank(id) && StringUtils.isNotBlank(name)) {
            conceptEntry = ConceptEntryFactory.createFromName(type, name);
        }
        // id and name
        if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(name)) {
            conceptEntry = ConceptEntryFactory.createFromIdAndName(type, id, name);
        }

        // validate search terms
        if (searchTerms.length == 0) {
            searchTerms = new String[]{conceptEntry.getName().toLowerCase()};
        }

        // createFromName node
        Node node = new Node(conceptEntry, searchTerms);

        return node;
    }

    public Node withMoreOcurrences() {
        this.minOcurrencesForSearchTerm = 5;
        return this;
    }

    public Node noKeywords() {
        this.keywords = new String[]{""};
        return this;
    }

    public Node checkName() {
        this.shouldCheckName = true;
        return this;
    }

    public Node boostRelevance() {
        this.shouldBoostRelevance = true;
        return this;
    }

    // =========================================================================
    // PROCESSED GETTERS
    // =========================================================================
    // BASIC INFO
    public String getAlias() {
        return StringUtils.join(aliases, ", ");
    }

    public String getSummary() {
        return launchboxSummary;
    }

    public String getVideo() {
        return launchboxVideo;
    }

    // ATTRIBUTES
    public Collection<String> getDBPediaAttributes(String... attributeKeys) {
        return toAttributes(dbpediaAttributes, attributeKeys);
    }

    public Collection<String> getIgdbAttributes(String... attributeKeys) {
        return toAttributes(igdbAttributes, attributeKeys);
    }

    public Collection<String> getMobygamesAttributes(String... attributeKeys) {
        return toAttributes(mobygamesAttributes, attributeKeys);
    }

    public Collection<String> getWikidataAttributes(String... attributeKeys) {
        return toAttributes(wikidataAttributes, attributeKeys);
    }

    public Collection<String> getWikipediaAttributes(String... attributeKeys) {
        return toAttributes(wikipediaAttributes, attributeKeys);
    }

    public Collection<String> getWikipediaCooperativeAttributes(String... attributeKeys) {
        return toAttributes(wikipediaCooperativeAttributes, attributeKeys);
    }

    //
    public <T extends ConceptEntry> T getConceptEntry(Class<T> returnType) {
        return returnType.cast(conceptEntry);
    }

    // =========================================================================
    // CHECKERS
    // =========================================================================
    public double relevanceForWords(String... words) {
        int wordsFound = 0;
        for (String word : words) {
            wordsFound += MapUtils.getIntValue(wikipediaEnWordCount, word, 0);
            wordsFound += MapUtils.getIntValue(giantbombWordCount, word, 0);
        }

        if (wordsFound >= minOcurrencesForSearchTerm) {
            double relevanceModifier = RelationshipRelevanceBoost.NORMAL;
            if (shouldBoostRelevance) {
                relevanceModifier = RelationshipRelevanceBoost.HIGH;
            }
            relevanceModifier = relevanceModifier + ((wordsFound - minOcurrencesForSearchTerm) * RelationshipRelevanceBoost.INCREMENT);
            return relevanceModifier;
        } else {
            return 0;
        }
    }

    // =========================================================================
    // PROCESSED ADDERS
    // =========================================================================
    public Node addAlternativeId(String id) {
        if (alternativeIds == Collections.EMPTY_LIST) {
            alternativeIds = Lists.newArrayList();
        }
        alternativeIds.add(id);
        return this;
    }

    public Node addAlternativeId(Class<? extends ConceptEntry> type, String alternativeId) {
        if (alternativeIds == Collections.EMPTY_LIST) {
            alternativeIds = Lists.newArrayList();
        }
        alternativeIds.add(UID.generateUid(type, alternativeId));
        return this;
    }

    public Node addAliases(Collection<String> aliases) {
        this.aliases.addAll(aliases);
        return this;
    }

    public Node addAlias(String alias) {
        aliases.add(alias);
        return this;
    }

    // =========================================================================
    // PROCESSED SETTERS
    // =========================================================================
    public void setName(String name) {
        // generate relevanceForWords name if name is equals the id or if the name is in lowercase
        if (name.equals(id) || name.equals(name.toLowerCase())) {
            name = name.replace("video_games", "");
            name = name.replace("_about_", "");
            name = name.replace("_set_in_", "");
            name = name.replace("_", " ");
            name = WordUtils.capitalize(name);
        }

        String trimmedName = name.trim();
        this.name = trimmedName;
    }

    public void setIdWikipedia(String idWikipedia) {
        this.idWikipedia = idWikipedia;
    }

    public void setImage(String image) {
        this.image = image.toLowerCase();
    }

    public void setDbpediaAttributes(String attributes) {
        this.dbpediaAttributes = parseJsonMap(attributes);
    }

    public void setDbpediaCategories(String categories) {
        this.dbpediaCategories = parseArray(categories);
    }

    public void setEsrbCategories(String categories) {
        this.esrbCategories = parseArray(categories);
    }

    public void setGiantbombAliases(String aliases) {
        addAliases(parseArray(aliases));
    }

    public void setGiantbombWordCount(String wordCount) {
        this.giantbombWordCount = parseJsonMap(wordCount);
    }

    public void setHltbAttributes(String attributes) {
        this.hltbAttributes = parseJsonMap(attributes);
    }

    public void setIgdbAttributes(String attributes) {
        this.igdbAttributes = parseJsonMap(attributes);
    }

    public void setLaunchboxAttributes(String attributes) {
        this.launchboxAttributes = parseJsonMap(attributes);
    }

    public void setMobygamesAttributes(String attributes) {
        this.mobygamesAttributes = parseJsonMap(attributes);
    }

    public void setPsnTrophies(String trophies) {
        this.psnTrophies = parseJsonList(trophies);
    }

    public void setThegamesdbAttributes(String attributes) {
        this.thegamesdbAttributes = parseJsonMap(attributes);
        if (thegamesdbAttributes.containsKey("aliases")) {
            addAliases(thegamesdbAttributes.get("aliases"));
        }
    }

    public void setXboxTrophies(String trophies) {
        this.xboxTrophies = parseJsonList(trophies);
    }

    public void setWikidataAttributes(String attributes) {
        this.wikidataAttributes = parseJsonMap(attributes);
    }

    public void setWikipediaAttributes(String attributes) {
        this.wikipediaAttributes = parseJsonMap(attributes);
    }

    public void setWikipediaCooperativeAttributes(String cooperativeAttributes) {
        this.wikipediaCooperativeAttributes = parseJsonMap(cooperativeAttributes);
    }

    public void setWikipediaEnWordCount(String wordCount) {
        this.wikipediaEnWordCount = parseJsonMap(wordCount);
    }

    public void setWikipediaPtWordCount(String wordCount) {
        this.wikipediaPtWordCount = parseJsonMap(wordCount);
    }

    // =========================================================================
    // HELPERS
    // =========================================================================
    private List<String> parseArray(String input) {
        if (input != null) {
            input = input.substring(1, input.length() - 1);
            input = input.replaceAll("\"", "");
            return (Splitter.on(',').trimResults().omitEmptyStrings().splitToList(input));
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    private List parseJsonList(String input) {
        if (input != null) {
            return JSON.toClass(input, List.class);
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    private Map parseJsonMap(String input) {
        if (input != null) {
            return JSON.toClass(input, Map.class);
        } else {
            return Collections.EMPTY_MAP;
        }
    }

    private Set<String> toAttributes(Map<String, List<String>> attributes, String... attributeKeys) {
        Set<String> attributesToReturn = Sets.newHashSet();

        // iterate keys
        for (String attributeKey : attributeKeys) {
            if (attributes.containsKey(attributeKey)) {
                attributesToReturn.addAll(attributes.get(attributeKey));
            }
        }

        return attributesToReturn;
    }

    // =========================================================================
    // CHECKS
    // =========================================================================
    public boolean shouldCheckName() {
        return shouldCheckName;
    }

    public boolean shouldBoostRelevance() {
        return shouldBoostRelevance;
    }

    // =========================================================================
    // GETTERS
    // =========================================================================
    public ConceptEntry getConceptEntry() {
        return conceptEntry;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public String getId() {
        return id;
    }

    public List<String> getAlternativeIds() {
        return alternativeIds;
    }

    public String getGeneratedId() {
        return generatedId;
    }

    public String getWikidataId() {
        return wikidataId;
    }

    public String getName() {
        return name;
    }

    public String getIdWikipedia() {
        return idWikipedia;
    }

    public String getImage() {
        return image;
    }

    public Map<String, List<String>> getDbpediaAttributes() {
        return dbpediaAttributes;
    }

    public List<String> getDbpediaCategories() {
        return dbpediaCategories;
    }

    public List<String> getEsrbCategories() {
        return esrbCategories;
    }

    public String getEsrbClassification() {
        return esrbClassification;
    }

    public String getGamespotScore() {
        return gamespotScore;
    }

    public String getIgnScore() {
        return ignScore;
    }

    public Map<String, Object> getHltbAttributes() {
        return hltbAttributes;
    }

    public Map<String, List<String>> getIgdbAttributes() {
        return igdbAttributes;
    }

    public Map<String, Object> getLaunchboxAttributes() {
        return launchboxAttributes;
    }

    public String getLaunchboxSummary() {
        return launchboxSummary;
    }

    public String getLaunchboxVideo() {
        return launchboxVideo;
    }

    public Map<String, List<String>> getMobygamesAttributes() {
        return mobygamesAttributes;
    }

    public List<String> getPsnTrophies() {
        return psnTrophies;
    }

    public Map<String, List<String>> getThegamesdbAttributes() {
        return thegamesdbAttributes;

    }

    public List<String> getXboxTrophies() {
        return xboxTrophies;
    }

    public Map<String, List<String>> getWikidataAttributes() {
        return wikidataAttributes;
    }

    public Map<String, List<String>> getWikipediaCooperativeAttributes() {
        return wikipediaCooperativeAttributes;
    }

    public boolean isWikipediaGoty() {
        return wikipediaGoty;
    }

    // =========================================================================
    // SETTERS
    // =========================================================================
    public void setId(String id) {
        this.id = id;
    }

    public void setGeneratedId(String generatedId) {
        this.generatedId = generatedId;
    }

    public void setEsrbClassification(String esrbClassification) {
        this.esrbClassification = esrbClassification;
    }

    public void setGamespotScore(String gamespotScore) {
        this.gamespotScore = gamespotScore;
    }

    public void setLaunchboxSummary(String launchboxSummary) {
        this.launchboxSummary = launchboxSummary;
    }

    public void setLaunchboxVideo(String launchboxVideo) {
        this.launchboxVideo = launchboxVideo;
    }

    public void setIgnScore(String ignScore) {
        this.ignScore = ignScore;
    }

    public Node setWikidataId(String wikidataId) {
        this.wikidataId = wikidataId;
        return this;
    }

    public void setWikipediaGoty(boolean wikipediaGoty) {
        this.wikipediaGoty = wikipediaGoty;
    }

}
