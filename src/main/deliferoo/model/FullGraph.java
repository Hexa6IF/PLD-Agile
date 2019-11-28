package model;
/**
 * Abstract class for representing a graph
 * composed of Edges.
 * @author lung
 *
 */
public class FullGraph extends AbstractGraph {
    
    private Double minLong;
    private Double maxLong;
    private Double minLat;
    private Double maxLat;
    
    /**
     * @param edges
     */
    public FullGraph(Edge[] edges, Node[] nodes, Double minLong, Double maxLong, Double minLat, Double maxLat) {
	super(edges, nodes);
	this.minLong = minLong;
	this.maxLong = maxLong;
	this.minLat = minLat;
	this.maxLat = maxLat;
    }
    
    public Double getRangeLongitude() {
	return (this.maxLong - this.minLong);
    }
    
    public Double getRangeLatitude() {
	return (this.maxLat - this.minLat);
    }
    
    public Double getMinLongitude() {
	return this.minLong;
    }
    
    public Double getMaxLongitude() {
	return this.maxLong;
    }
   
    public Double getMinLatitude() {
	return this.minLat;
    }
    
    public Double getMaxLatitude() {
	return this.maxLat;
    }
    
    public String toString(){
	String s  = ""; 
	s += "Nodes : \r\n";
	for (int i = 0; i < nodes.length; i++) {
	    s += nodes[i].toString();
	}
	s += "\r\nEdges : \r\n";
	for (int i = 0; i < edges.length; i++) {
	    s += edges[i].toString();
	}
	return s;
    }
}
