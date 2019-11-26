package model;
/**
 * Abstract class for representing a graph
 * composed of AbstractEdges.
 * @author lung
 *
 */
public abstract class AbstractGraph {
    protected AbstractEdge[] edges;

    /**
     * @param edges
     */
    public AbstractGraph(AbstractEdge[] edges) {
	this.edges = edges;
    }

    /**
     * @return the edges
     */
    public AbstractEdge[] getEdges() {
        return edges;
    }

    /**
     * @param edges the edges to set
     */
    public void setEdges(Edge[] edges) {
        this.edges = edges;
    }
}
