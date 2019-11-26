package model;
/**
 * Abstract class for representing a graph
 * composed of BestPathes.
 * @author lung
 *
 */
public class BestPathGraph extends AbstractGraph {
    /**
     * @param edges
     */
    public BestPathGraph(BestPath[] edges, Node[] nodes) {
	super(edges, nodes);
    }
    
}
