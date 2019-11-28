package model;
/**
 * Abstract class representing the edge between 2 Nodes 
 * belonging to the list of deliveries.
 * 
 * @author louis
 */
public class BestPath extends AbstractEdge {
    private Edge[] edges;

    /**
     * @param nodeOrigin
     * @param nodeDest
     * @param edges
     */
    public BestPath(Node nodeOrigin, Node nodeDest, Edge[] edges, Double totalDistance) {
	super(nodeOrigin, nodeDest, totalDistance);
	this.edges = edges;
    }
    
    public Edge[] getEdges() {
	return this.edges;
    }
    
    public void setEdges(Edge[] edges) {
	this.edges = edges;
    }
}
