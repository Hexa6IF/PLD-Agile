package model;
/**
 * Model class representing the edge between 2 Nodes.
 * 
 * @author louis
 */
public class Edge extends AbstractEdge {
    
    private String streetName;

    /**
     * @param nodes
     * @param nodeOrigin
     * @param nodeDest
     */
    public Edge(Node nodeOrigin, Node nodeDest, Float distance, String streetName) {
	super(nodeOrigin, nodeDest, distance);
	this.streetName = streetName;
    }

    /**
     * @return the streetname
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * @param streetname the streetname to set
     */
    public void setStreetName(String streetname) {
        this.streetName = streetname;
    }

}
