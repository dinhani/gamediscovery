package gd.infrastructure.graph;

import org.jgrapht.graph.DefaultWeightedEdge;

public class ImprovedWeightedEdge extends DefaultWeightedEdge {

    private String source;
    private String target;

    @Override
    public double getWeight() {
        return super.getWeight();
    }

    // =========================================================================
    // GETTERS AND SETTERS
    // =========================================================================
    @Override
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

}
