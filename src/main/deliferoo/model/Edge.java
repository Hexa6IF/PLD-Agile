package model;
/**
 * Model class representing the edge between 2 Nodes.
 * 
 * @author louis
 */
public class Edge extends AbstractEdge {
    private String streetname;

    /**
     * @param nodes
     * @param nodeOrigin
     * @param nodeDest
     */
    public Edge(Node nodeOrigin, Node nodeDest, Float distance) {
	super(nodeOrigin, nodeDest, distance);
    }
    
    /**
     * @param nodeOrigin
     * @param nodeDest
     * @param distance
     * @param streetname
     */
    public Edge(Node nodeOrigin, Node nodeDest, Float distance, String streetname) {
	super(nodeOrigin, nodeDest, distance);
	this.streetname = streetname;
    }

    /**
     * @return the streetname
     */
    public String getStreetname() {
        return this.streetname;
    }

    /**
     * @param streetname the streetname to set
     */
    public void setStreetname(String streetname) {
        this.streetname = streetname;
    }

}
