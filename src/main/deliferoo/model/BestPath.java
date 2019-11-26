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
    public BestPath(Node nodeOrigin, Node nodeDest, Edge[] edges) {
	super(nodeOrigin, nodeDest, edges);
    }
}
