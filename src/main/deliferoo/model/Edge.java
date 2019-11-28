package model;
/**
 * Model class representing the edge between 2 Nodes.
 * 
 * @author louis
 */
public class Edge {
    private String streetName;
    private Node start;
    private Node end;

    /**
     * @param nodeOrigin
     * @param nodeDest
     */
    public Edge(Node nodeOrigin, Node nodeDest) {
	this.start=nodeOrigin;
	this.end=nodeDest;
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
    public void setStreetname(String streetName) {
	this.streetName = streetName;
    }

    public Node getStart() {
	return this.start;
    }

    public void setStart(Node start) {
	this.start = start;
    }

    public Node getEnd() {
	return end;
    }

    public void setEnd(Node end) {
	this.end = end;
    }

}
