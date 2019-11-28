package model;
/**
 * Abstract class representing the edge between 2 Nodes.
 * 
 * @author louis
 */

public abstract class AbstractEdge {
    protected Node nodeOrigin;
    protected Node nodeDest;
    protected Double distance;
    
    /**
     * @param nodeOrigin
     * @param nodeDest
     * @param edges
     */
    public AbstractEdge(Node nodeOrigin, Node nodeDest, Double distance) {
	this.nodeOrigin = nodeOrigin;
	this.nodeDest = nodeDest;
	this.distance = distance;
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
    public Double getDistance() {
        return this.distance;
    }
    /**
     * @param distance the distance to set
     */
    public void setDistance(Double distance) {
        this.distance = distance;
    }    
}
