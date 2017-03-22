package gd.domain.recommendations.repository.cache;

import gd.domain.shared.GD;
import gd.infrastructure.graph.ImprovedWeightedEdge;
import gd.infrastructure.graph.ImprovedWeightedGraph;
import gd.infrastructure.database.AbstractDatabaseCommand;
import gd.infrastructure.log.LogMarker;
import gd.infrastructure.log.LogProducer;
import gd.infrastructure.memory.Memory;
import gd.infrastructure.web.DummyRequestAttributes;
import gd.infrastructure.steriotype.GDCache;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import org.neo4j.ogm.model.Result;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;

@GDCache
public class GraphCache extends AbstractDatabaseCommand {

    // =========================================================================
    // GRAPHS
    // =========================================================================        
    // JGRAPHT
    private ImprovedWeightedGraph graph;

    // =========================================================================
    // OTHERS
    // =========================================================================
    @Autowired
    private Memory memory;

    @Autowired
    private gd.infrastructure.math.Math math;

    private static final Logger LOGGER = LogProducer.getLogger(GraphCache.class);

    // =========================================================================
    // INITIALIZATION
    // =========================================================================
    public GraphCache() {
        RequestContextHolder.setRequestAttributes(new DummyRequestAttributes());
    }

    @PostConstruct
    public void init() {
        graph = buildGraph();
        memory.gc();
    }

    // =========================================================================
    // GRAPH INITIALIZATION
    // =========================================================================
    private ImprovedWeightedGraph buildGraph() {
        LOGGER.info(LogMarker.DB, "Initializing graph cache");

        // ---------------------------------------------------------------------
        // 1) query min and max weights for weight reversing and fitting
        String weightCypher = "MATCH (n)-[r]->(m) RETURN MIN(r.weight) AS min, max(r.weight) AS max";
        Result weightRes = neo4jSession.query(weightCypher, Collections.EMPTY_MAP);

        // 1.1) if not weights, there is no data, so just create an empty graph
        Map<String, Object> weightRow = weightRes.iterator().next();
        if (weightRow.get("min") == null || weightRow.get("max") == null) {
            return new ImprovedWeightedGraph(ImprovedWeightedEdge.class);
        }

        // 1.2) parse results        
        double maxWeight = ((Number) weightRow.get("max")).doubleValue();

        // ---------------------------------------------------------------------
        // 2) query nodes and relationships
        // 2.1) query total of nodes
        String countCypher = "MATCH (n) RETURN count(n) as count";
        Result countRes = neo4jSession.query(countCypher, Collections.EMPTY_MAP);
        Map<String, Object> countRow = countRes.iterator().next();
        int numberOfNodes = ((Number) countRow.get("count")).intValue();

        // 2.2) iterate retrieving nodes
        ImprovedWeightedGraph graph = new ImprovedWeightedGraph(ImprovedWeightedEdge.class);

        int skip = 0;
        while (skip < numberOfNodes) {
            LOGGER.info(LogMarker.DB, "Retrieving nodes from Neo4J | skip={}", skip);

            String dataCypher = "MATCH (n) WITH n as s SKIP " + skip + " LIMIT 3000 "
                    + "MATCH (s)-[r]->(m) RETURN s.uid AS source, m.uid as target, SUM(r.weight) AS `weight`";
            Result dataRes = neo4jSession.query(dataCypher, Collections.EMPTY_MAP);

            // process results                        
            dataRes.iterator().forEachRemaining(dataRow -> {
                // get data
                String source = (String) dataRow.get("source");
                String target = (String) dataRow.get("target");

                // node
                graph.addVertex(source);
                graph.addVertex(target);

                // edges
                ImprovedWeightedEdge edge = graph.addEdge(source, target);
                graph.setEdgeWeight(edge, math.fitBetweenMinAndMax(((Number) dataRow.get("weight")).doubleValue(), maxWeight, 0));
            });

            // increase skip for next step
            skip += 3000;
        }

        LOGGER.info(LogMarker.DB, "Initialized graph cache | nodes={}, edges={}", graph.vertexSet().size(), graph.edgeSet().size());
        GD.GRAPH_NODES_SIZE = graph.vertexSet().size();
        GD.GRAPH_EDGES_SIZE = graph.edgeSet().size();

        return graph;
    }

    // =========================================================================
    // PROCESSED GETTERS
    // =========================================================================
    public Collection<String> getGamesCollection() {
        return getGamesStream().collect(Collectors.toList());
    }

    public Stream<String> getGamesStream() {
        return graph.vertexSet().stream().filter(v -> v.startsWith("game-"));
    }

    public Collection<String> getCharacteristicsCollection() {
        return getCharacteristicsStream().collect(Collectors.toList());
    }

    public Stream<String> getCharacteristicsStream() {
        return graph.vertexSet().stream().filter(v -> !v.startsWith("game-"));
    }

    // =========================================================================
    // GETTERS
    // =========================================================================
    public ImprovedWeightedGraph getGraph() {
        return graph;
    }
}
