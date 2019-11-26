package model;
/**
 * Abstract class for representing a graph
 * composed of Edges.
 * @author lung
 *
 */
public class FullGraph extends AbstractGraph {
    
    private Float minLatitude;
    private Float maxLatitude;
    private Float minLongitude;
    private Float maxLongitude;   
    
    /**
     * @param edges
     */
    public FullGraph(Edge[] edges, Node[] nodes, Float minLat, Float maxLat, Float minLong, Float maxLong) {
	super(edges, nodes);
	this.minLatitude = minLat;
	this.maxLatitude = maxLat;
	this.minLongitude = minLong;
	this.maxLongitude = maxLong;
    }
    
    public Float getMinLatitude() {
	return this.minLatitude;
    }
    
    public Float getMaxLatitude() {
	return this.maxLatitude;
    }
    
    public Float getMinLongitude() {
	return this.minLongitude;
    }
    
    public Float getMaxLongitude() {
	return this.maxLongitude;
    }
}
