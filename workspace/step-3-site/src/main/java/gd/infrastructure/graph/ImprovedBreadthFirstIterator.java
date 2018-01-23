package gd.infrastructure.graph;

import com.google.common.collect.Maps;
import gd.domain.shared.GD;
import java.util.Map;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.traverse.BreadthFirstIterator;

public class ImprovedBreadthFirstIterator<V, E> extends BreadthFirstIterator<V, E> {

    private V startVertex;
    private final Map<V, Integer> hops = Maps.newHashMap();

    public ImprovedBreadthFirstIterator(Graph<V, E> g) {
        this(g, null);
        initSeenMap();
    }

    public ImprovedBreadthFirstIterator(Graph<V, E> g, V startVertex) {
        super(g, startVertex);
        this.startVertex = startVertex;
        initSeenMap();
    }

    private void initSeenMap() {
        try {
            FieldUtils.writeField(this, "seen", Maps.newHashMapWithExpectedSize(GD.GRAPH_NODES_SIZE), true);
        } catch (IllegalAccessException e) {
        }
    }

    // =========================================================================
    // GETTERS
    // =========================================================================
    public V getStartVertex() {
        return startVertex;
    }

    // =========================================================================
    // ADDITIONAL HOPS METRICS
    // =========================================================================
    @Override
    protected void encounterVertex(V vertex, E edge) {
        // calculate hops
        calculateHops(vertex, edge);

        // call parent iterator
        super.encounterVertex(vertex, edge);
    }

    private void calculateHops(V vertex, E edge) {
        int numberOfHops = 0;
        if (edge != null) {
            V opposite = Graphs.getOppositeVertex(getGraph(), edge, vertex);
            if (hops.containsKey(opposite)) {
                numberOfHops = hops.get(opposite) + 1;
            }
        }
        hops.put(vertex, numberOfHops);
    }

    public Integer getHops(V vertex) {
        return hops.get(vertex);
    }

    // =========================================================================
    // PERFORMANCE
    // =========================================================================
}
