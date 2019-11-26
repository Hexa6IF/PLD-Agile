package model;
/**
 * Abstract class for representing a graph
 * composed of AbstractEdges.
 * @author lung
 *
 */
public abstract class AbstractGraph {
    protected AbstractEdge[] edges;
    protected Node[] nodes;

    /**
     * @param edges
     */
    public AbstractGraph(AbstractEdge[] edges, Node[] nodes) {
	this.edges = edges;
	this.nodes = nodes;
    }
    
    public Node getNodeByID(Long nodeID) {
	for (int i=0; i<nodes.length; i++) {
	    if (nodes[i].getIdNode() == nodeID) {
		return nodes[i];
	    }
	}
	return null;
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

    /**
     * @return the nodes
     */
    public Node[] getNodes() {
        return nodes;
    }

    /**
     * @param nodes the nodes to set
     */
    public void setNodes(Node[] nodes) {
        this.nodes = nodes;
    }
    
}
