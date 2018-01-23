package gd.infrastructure.graph;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;

public class EdgesSetWrapper extends AbstractSet<ImprovedWeightedEdge> {

    private final Collection<ImprovedWeightedEdge> edges;

    public EdgesSetWrapper(Collection<ImprovedWeightedEdge> edges) {
        this.edges = edges;
    }

    @Override
    public boolean contains(Object o) {
        return edges.contains(o);
    }

    @Override
    public Iterator<ImprovedWeightedEdge> iterator() {
        return edges.iterator();
    }

    @Override
    public int size() {
        return edges.size();
    }

}
