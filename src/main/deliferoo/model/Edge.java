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
    private Double distance;

    /**
     * @param start
     * @param end
     * @param distance
     * @param streetName
     */
    public Edge(Node start, Node end, Double distance, String streetName) {
	this.start = start;
	this.end = end;
	this.distance = distance;
	this.streetName = streetName;
    }

    /**
     * @return the streetName
     */
    public String getStreetName() {
	return streetName;
    }

    /**
     * @param streetname the streetName to set
     */
    public void setStreetName(String streetName) {
	this.streetName = streetName;
    }

    public Node getStart() {
	return this.start;
    }

    public void setStart(Node start) {
	this.start = start;
    }

    public Node getEnd() {
	return this.end;
    }

    public void setEnd(Node end) {
	this.end = end;
    }

    public Double getDistance() {
	return this.distance;
    }
    
    public void setDistance(Double distance) {
	this.distance = distance;
    }
}
