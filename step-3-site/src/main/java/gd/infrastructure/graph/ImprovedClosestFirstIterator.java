package gd.infrastructure.graph;

import com.google.common.collect.Maps;
import gd.domain.shared.GD;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.jgrapht.Graph;
import org.jgrapht.traverse.ClosestFirstIterator;

public class ImprovedClosestFirstIterator<V, E> extends ClosestFirstIterator<V, E> {

    private V startVertex;

    public ImprovedClosestFirstIterator(Graph<V, E> g) {
        this(g, null);
        initSeenMap();
    }

    public ImprovedClosestFirstIterator(Graph<V, E> g, V startVertex) {
        super(g, startVertex);
        this.startVertex = startVertex;
        initSeenMap();
    }

    // =========================================================================
    // GETTERS
    // =========================================================================
    public V getStartVertex() {
        return startVertex;
    }

    // =========================================================================
    // PERFORMANCE
    // =========================================================================
    private void initSeenMap() {
        try {
            FieldUtils.writeField(this, "seen", Maps.newHashMapWithExpectedSize(GD.GRAPH_NODES_SIZE), true);
        } catch (IllegalAccessException e) {
        }
    }

}
