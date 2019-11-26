package model;
/**
 * Abstract class representing the edge between 2 Nodes 
 * belonging to the list of deliveries.
 * 
 * @author louis
 */
public class BestPath extends AbstractEdge {

    /**
     * @param nodes
     * @param nodeOrigin
     * @param nodeDest
     */
    public BestPath(Node[] nodes, Node nodeOrigin, Node nodeDest) {
	super(nodes, nodeOrigin, nodeDest);
    }

}
