package model;
/**
 * Abstract class representing the edge between 2 Nodes.
 * 
 * @author louis
 */

public abstract class AbstractEdge {
    protected Node nodeOrigin;
    protected Node nodeDest;
    protected Float distance;
    protected Edge[] edges;
    
    /**
     * @param nodeOrigin
     * @param nodeDest
     * @param edges
     */
    public AbstractEdge(Node nodeOrigin, Node nodeDest, Edge[] edges) {
	this.nodeOrigin = nodeOrigin;
	this.nodeDest = nodeDest;
	this.edges = edges;
	this.distance = nodeOrigin.calculateDistance(nodeDest);
    }

    /**
     * @param nodeOrigin
     * @param nodeDest
     * @param distance
     */
    public AbstractEdge(Node nodeOrigin, Node nodeDest) {
	this.nodeOrigin = nodeOrigin;
	this.nodeDest = nodeDest;
	this.distance = nodeOrigin.calculateDistance(nodeDest);
    }

    /**
     * @return the nodeOrigin
     */
    public Node getNodeOrigin() {
        return this.nodeOrigin;
    }
    /**
     * @param nodeOrigin the nodeOrigin to set
     */
    public void setNodeOrigin(Node nodeOrigin) {
        this.nodeOrigin = nodeOrigin;
    }
    /**
     * @return the nodeDest
     */
    public Node getNodeDest() {
        return this.nodeDest;
    }
    /**
     * @param nodeDest the nodeDest to set
     */
    public void setNodeDest(Node nodeDest) {
        this.nodeDest = nodeDest;
    }
    /**
     * @return the distance
     */
    public Float getDistance() {
        return this.distance;
    }
    /**
     * @param distance the distance to set
     */
    public void setDistance(Float distance) {
        this.distance = distance;
    }

    /**
     * @return the edges
     */
    public Edge[] getEdges() {
        return edges;
    }

    /**
     * @param edges the edges to set
     */
    public void setEdges(Edge[] edges) {
        this.edges = edges;
    }
    
    
}
