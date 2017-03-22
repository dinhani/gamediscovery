package gd.infrastructure.graph;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import gd.infrastructure.log.LogProducer;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.jgrapht.graph.WeightedPseudograph;
import org.slf4j.Logger;

public class ImprovedWeightedGraph extends WeightedPseudograph<String, ImprovedWeightedEdge> {

    private Map<String, Object> vertexMap = Maps.newHashMap();
    private Map<String, Map<String, ImprovedWeightedEdge>> edgeMap = Maps.newHashMap();
    private Set<ImprovedWeightedEdge> allEdges = Sets.newHashSet();

    private static final Logger LOGGER = LogProducer.getLogger(ImprovedWeightedGraph.class);

    public ImprovedWeightedGraph(Class<? extends ImprovedWeightedEdge> edgeClass) {
        super(edgeClass);
        try {
            UndirectedSpecifics specifics = (UndirectedSpecifics) FieldUtils.readField(this, "specifics", true);
            FieldUtils.writeField(specifics, "vertexMapUndirected", vertexMap, true);
        } catch (IllegalAccessException e) {
            LOGGER.error("Error building in-memory graph | message={}", e.getMessage(), e);
            System.exit(1);
        }
    }

    // =========================================================================
    // DOMAIN
    // =========================================================================
    public Collection<String> getGames() {
        return vertexSet().stream().filter(v -> v.startsWith("game-")).collect(Collectors.toList());
    }

    public Collection<String> getCharacteristics() {
        return vertexSet().stream().filter(v -> !v.startsWith("game-")).collect(Collectors.toList());
    }

    public Collection<String> neighborsOf(String vertex) {
        Map<String, ImprovedWeightedEdge> neighbors = edgeMap.get(vertex);

        // check null
        if (neighbors == null) {
            return Collections.EMPTY_LIST;
        }
        // get neighbors
        return neighbors.values().stream().map(edge -> getOppositeVertex(edge, vertex)).collect(Collectors.toList());
    }

    // =========================================================================
    // VERTEX
    // =========================================================================
    @Override
    public boolean containsVertex(String v) {
        return vertexMap.containsKey(v);
    }

    @Override
    protected boolean assertVertexExist(String v) {
        return true;
    }

    // =========================================================================
    // EDGE
    // =========================================================================
    @Override
    public ImprovedWeightedEdge addEdge(String sourceVertex, String targetVertex) {
        // improved implementation
        ImprovedWeightedEdge e = super.getEdgeFactory().createEdge(sourceVertex, targetVertex);
        e.setSource(sourceVertex);
        e.setTarget(targetVertex);

        // source -> target
        if (!edgeMap.containsKey(sourceVertex)) {
            edgeMap.put(sourceVertex, Maps.newHashMap());
        }
        edgeMap.get(sourceVertex).put(targetVertex, e);

        // target -> source
        if (!edgeMap.containsKey(targetVertex)) {
            edgeMap.put(targetVertex, Maps.newHashMap());
        }
        edgeMap.get(targetVertex).put(sourceVertex, e);

        // all edges
        allEdges.add(e);

        return e;
    }

    @Override
    public ImprovedWeightedEdge getEdge(String sourceVertex, String targetVertex) {
        return edgeMap.get(sourceVertex).get(targetVertex);
    }

    @Override
    public Set<ImprovedWeightedEdge> edgesOf(String vertex) {
        return new EdgesSetWrapper(edgeMap.get(vertex).values());
    }

    @Override
    public Set<ImprovedWeightedEdge> edgeSet() {
        return allEdges;
    }

    @Override
    public String getEdgeSource(ImprovedWeightedEdge e) {
        return (String) ((ImprovedWeightedEdge) e).getSource();
    }

    @Override
    public String getEdgeTarget(ImprovedWeightedEdge e) {
        return (String) ((ImprovedWeightedEdge) e).getTarget();
    }

    // =========================================================================
    // HELPEr
    // =========================================================================
    private String getOppositeVertex(ImprovedWeightedEdge edge, String vertex) {
        String source = edge.getSource();
        String target = edge.getTarget();
        if (vertex.equals(source)) {
            return target;
        } else {
            return source;
        }
    }

}
