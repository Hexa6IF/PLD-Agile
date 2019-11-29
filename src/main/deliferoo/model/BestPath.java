package model;

import java.util.List;

/**
 * Abstract class representing the edge between 2 Special Nodes 
 * which belong to the list of deliveries.
 * 
 * @author louis
 */
public class BestPath {
    private SpecialNode start;
    private SpecialNode end;
    private List<Edge> path;
    private Double distance;

    /**
     * @param nodeOrigin
     * @param nodeDest
     * @param edges
     */
    public BestPath(SpecialNode nodeOrigin, SpecialNode nodeDest, List<Edge> path) {
	this.start = nodeOrigin; 
	this.end = nodeDest;
	this.path = path;
	distance = 0.0;
	for (int i = 0; i<path.size(); i++) {
	    distance += path.get(i).getDistance();
	}
    }

    public SpecialNode getStart() {
	return start;
    }

    public void setStart(SpecialNode start) {
	this.start = start;
    }

    public SpecialNode getEnd() {
	return end;
    }

    public void setEnd(SpecialNode end) {
	this.end = end;
    }

    public List<Edge> getPath() {
	return path;
    }

    public void setPath(List<Edge> path) {
	this.path = path;
    }

    /**
     * @return the distance
     */
    public Double getDistance() {
        return distance;
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(Double distance) {
        this.distance = distance;
    }
    
}
