package model;
/**
 * Abstract class representing the edge between 2 Nodes.
 * 
 * @author louis
 */

public abstract class AbstractEdge {
    protected Node[] nodes;
    protected Node nodeOrigin;
    protected Node nodeDest;
    protected Long distance;
    
    /**
     * @param nodes
     * @param nodeOrigin
     * @param nodeDest
     * @param distance
     */
    public AbstractEdge(Node[] nodes, Node nodeOrigin, Node nodeDest) {
	this.nodes = nodes;
	this.nodeOrigin = nodeOrigin;
	this.nodeDest = nodeDest;
	//this.distance = 
    }
    /**
     * @return the nodes
     */
    public Node[] getNodes() {
        return this.nodes;
    }
    /**
     * @param nodes the nodes to set
     */
    public void setNodes(Node[] nodes) {
        this.nodes = nodes;
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
    public Long getDistance() {
        return this.distance;
    }
    /**
     * @param distance the distance to set
     */
    public void setDistance(Long distance) {
        this.distance = distance;
    }
    
    
}
