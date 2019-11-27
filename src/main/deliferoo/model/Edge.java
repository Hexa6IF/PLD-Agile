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
     * @return the streetname
     */
    public String getStreetname() {
        return streetname;
    }

    /**
     * @param streetname the streetname to set
     */
    public void setStreetname(String streetname) {
        this.streetname = streetname;
    }

}
