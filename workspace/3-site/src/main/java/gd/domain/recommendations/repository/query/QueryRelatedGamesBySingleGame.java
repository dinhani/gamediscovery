package gd.domain.recommendations.repository.query;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.entity.gameplay.Duration;
import gd.domain.entities.entity.gameplay.Genre;
import gd.domain.entities.entity.gameplay.Graphics;
import gd.domain.entities.entity.gameplay.Mechanics;
import gd.domain.entities.entity.industry.Company;
import gd.domain.entities.entity.industry.ContentCharacteristic;
import gd.domain.entities.entity.industry.ContentClassification;
import gd.domain.entities.entity.industry.Person;
import gd.domain.entities.entity.industry.Platform;
import gd.domain.entities.entity.industry.ReleaseYear;
import gd.domain.entities.entity.plot.Atmosphere;
import gd.domain.entities.entity.plot.Location;
import gd.domain.entities.entity.plot.Organization;
import gd.domain.entities.entity.plot.Period;
import gd.domain.entities.entity.plot.Soundtrack;
import gd.domain.entities.entity.plot.Theme;
import gd.domain.entities.entity.plot.Vehicle;
import gd.domain.entities.entity.plot.Weapon;
import gd.domain.recommendations.entity.GraphSearch;
import gd.domain.recommendations.entity.GraphSearchResult;
import gd.domain.recommendations.repository.cache.GraphCache;
import gd.infrastructure.enviroment.Environment;
import gd.infrastructure.steriotype.GDQuery;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.stat.Frequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@GDQuery
@Scope(value = "prototype")
public class QueryRelatedGamesBySingleGame implements QueryRelatedGames {

    // =========================================================================
    // SERVICES
    // =========================================================================
    @Autowired
    private GraphCache graph;

    @Autowired
    private gd.infrastructure.math.Math math;

    @Autowired
    private Environment environment;

    // =========================================================================
    // DATA
    // =========================================================================
    private final Set<String> visitedNodes = Sets.newHashSet();
    private final Frequency gameCategoriesFrequency = new Frequency(); // categoryUid, total
    private final Map<String, Double> gameCharacteristicsWeight = Maps.newHashMap(); // characteristicUid, relationship weight    
    private final Map<String, Map<String, List<Double>>> relatedGamesRelationshipsWeight = Maps.newHashMap(); // gameUid, categoryUid, weight

    // =========================================================================
    // EXECUTION
    // =========================================================================
    @Override
    public Collection<GraphSearch> execute(String[] searchUids, String[] categoriesToUse) {

        // 1) get gameUid
        String gameUid = searchUids[0];

        // 2) initialize categories
        Set<String> categoriesToUseSet = getCategoriesToUSe(categoriesToUse);

        // 3) initialize games characteristics
        calculateGameCharacteristicsWeights(gameUid);

        // 4) start calculation
        calculateRelevanceForNode(gameUid, categoriesToUseSet, 1);

        // 5) generate graph search
        GraphSearch graphSearch = generateGameSearch(gameUid);

        // return
        return Lists.newArrayList(graphSearch);
    }

    // =========================================================================
    // STEP 2 - INITIALIZATION OF CATEGORIES
    // =========================================================================
    private Set<String> getCategoriesToUSe(String[] categoriesToUse) {
        if (categoriesToUse == null || categoriesToUse.length == 0) {
            return chooseCategoriesToUseInRecommendations();
        } else {
            return Sets.newHashSet(categoriesToUse);
        }
    }

    // =========================================================================
    // STEP 3 - INITIALIZATION OF CHARACTERISTICS WEIGHT
    // =========================================================================
    private void calculateGameCharacteristicsWeights(String gameUid) {
        Collection<String> neighbors = graph.getGraph().neighborsOf(gameUid);
        for (String neighborNode : neighbors) {
            double gameToCharacteristicWeight = 1 - graph.getGraph().getEdge(gameUid, neighborNode).getWeight();
            gameCharacteristicsWeight.put(neighborNode, gameToCharacteristicWeight);
        }
    }

    // =========================================================================
    // STEP 4 - RAW WEIGHTS SUM
    // =========================================================================
    private void calculateRelevanceForNode(String nodeToVisit, Set<String> categoriesToUse, int currentDepthLevel) {

        // 1) mark current node as visited
        visitedNodes.add(nodeToVisit);

        // 2) extract node category and increment total of categories of current type
        String category = StringUtils.split(nodeToVisit, "-", 2)[0];
        gameCategoriesFrequency.addValue(category);

        // 3) check if should use some category in the recommendations
        // "game" is always used
        if (!category.equals("game") && !categoriesToUse.contains(category)) {
            return;
        }

        // 4) calculate weight for current node and its neighbors
        Collection<String> neighbors = graph.getGraph().neighborsOf(nodeToVisit);
        for (String neighborNode : neighbors) {
            if (neighborNode.startsWith("game-")) {
                // calculate weight                
                double calculatedWeightForNeighbor = calculateRelevanceForRelationship(nodeToVisit, neighborNode, currentDepthLevel);

                // add weights to list of calculate weights
                if (!relatedGamesRelationshipsWeight.containsKey(neighborNode)) {
                    relatedGamesRelationshipsWeight.put(neighborNode, Maps.newHashMap());
                }
                if (!relatedGamesRelationshipsWeight.get(neighborNode).containsKey(category)) {
                    relatedGamesRelationshipsWeight.get(neighborNode).put(category, Lists.newArrayList());
                }
                relatedGamesRelationshipsWeight.get(neighborNode).get(category).add(calculatedWeightForNeighbor);
            }
        }

        // 5) checks if already reached the maximum distance to visit
        if (currentDepthLevel == 2) {
            return;
        }

        // 6) visit neighbors nodes
        for (String neighbor : neighbors) {
            if (visitedNodes.contains(neighbor)) {
                continue;
            }
            calculateRelevanceForNode(neighbor, categoriesToUse, currentDepthLevel + 1);
        }
    }

    private double calculateRelevanceForRelationship(String sourceNode, String neighborNode, int currentDepthLevel) {

        double relationshipWeight = 1 - graph.getGraph().getEdge(sourceNode, neighborNode).getWeight();
        double calculatedWeightForNeighbor = relationshipWeight / currentDepthLevel;
        calculatedWeightForNeighbor = calculatedWeightForNeighbor * (1 + gameCharacteristicsWeight.get(sourceNode));

        return calculatedWeightForNeighbor;
    }

    // =========================================================================
    // STEP 5 - TRANSFORMATION OF WEIGHTS
    // =========================================================================
    private GraphSearch generateGameSearch(String searchUid) {

        // transform
        GraphSearch searchByGame = new GraphSearch(searchUid);

        // 1) calculate average and total weights
        for (Map.Entry<String, Map<String, List<Double>>> relatedGameWithRelationships : relatedGamesRelationshipsWeight.entrySet()) {
            // iterate categories
            Map<String, Double> relatedGamePartialWeights = Maps.newHashMap();
            double totalWeight = 0;

            // transform partial weights into a single weight
            for (Map.Entry<String, List<Double>> categoryWithWeights : relatedGameWithRelationships.getValue().entrySet()) {
                String category = categoryWithWeights.getKey();

                // calculate intersection weight
                long categoryFrequencyInSourceGame = gameCategoriesFrequency.getCount(category);   // the number of times a category appear in the source game
                List<Double> categoryWeightsInRelationship = categoryWithWeights.getValue();   // all computed values between the source node and target node for the selected category
                double intersectionWeight = calculateIntersectionWeight(categoryFrequencyInSourceGame, categoryWeightsInRelationship);

                // update category weights
                relatedGamePartialWeights.put(category, intersectionWeight);
                totalWeight += intersectionWeight;
            }

            // add search result
            GraphSearchResult gameSearchResult = new GraphSearchResult(relatedGameWithRelationships.getKey(), totalWeight, relatedGamePartialWeights);
            searchByGame.addResult(gameSearchResult);
        }

        // 2) make searched game the most relevant
        searchByGame.addResult(searchUid, 100.0);

        // 3) order the results
        searchByGame.updateResultsOrder();

        // 4) log results for analysis
        if (environment.isDevelopment()) {
            logSimilarGames(searchByGame);
        }

        // 5) invert weights making the largest weight the smallest
        double maxWeight = searchByGame.getResults().last().getWeight();
        for (GraphSearchResult result : searchByGame.getResults()) {
            searchByGame.getResult(result.getGame()).setWeight(math.fitBetweenMinAndMax(result.getWeight(), maxWeight, 0));
        }

        // force ordering
        searchByGame.updateResultsOrder();

        return searchByGame;
    }

    private double calculateIntersectionWeight(long categoryFrequency, List<Double> categoryWeights) {
        double averageOfPartialWeights = categoryWeights.stream().mapToDouble(d -> d).average().getAsDouble();
        double intersectionProportion = categoryWeights.size() / (categoryFrequency * 1.0);
        return averageOfPartialWeights * intersectionProportion;
    }

    // =========================================================================
    // CATEGORIES CHOOSER
    // =========================================================================
    private Set<String> chooseCategoriesToUseInRecommendations() {
        Set<Class<? extends ConceptEntry>> categories = Sets.newHashSet(Genre.class,
                Theme.class,
                Atmosphere.class,
                Mechanics.class,
                Graphics.class,
                Duration.class,
                //
                ContentCharacteristic.class,
                ContentClassification.class,
                //
                gd.domain.entities.entity.plot.Character.class,
                Weapon.class,
                Vehicle.class,
                Organization.class,
                Location.class,
                Period.class,
                Soundtrack.class,
                //
                Company.class,
                Person.class,
                Platform.class,
                ReleaseYear.class
        );

        return categories.stream().map(c -> c.getSimpleName().toLowerCase()).collect(Collectors.toSet());
    }

    // =====================================================================
    // TRACING
    // =====================================================================    
    private void logSimilarGames(GraphSearch graphSearch) {
        // sort game results from the most to the least relevant
        Stream<GraphSearchResult> sortedResults = graphSearch.getResults().stream().sorted(Comparator.comparing(GraphSearchResult::getWeight).reversed()).limit(40);

        // print each relevant result for analysis
        System.out.print("\033[H\033[2J");
        System.out.flush();
        sortedResults.forEach(
                result -> {
                    System.out.println("=====================================");
                    System.out.format("%-60s | total                          = %.3f %n", result.getGame(), result.getWeight() * 100);

                    for (Map.Entry<String, Double> partialWeight : result.getPartialWeights().entrySet().stream().sorted((e1, e2) -> -1 * e1.getValue().compareTo(e2.getValue())).collect(Collectors.toList())) {
                        System.out.format("%-60s | %-30s = %.3f %n", result.getGame(), partialWeight.getKey(), partialWeight.getValue() * 100);
                    }
                }
        );
    }
}
