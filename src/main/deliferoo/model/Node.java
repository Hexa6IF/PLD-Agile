package model;

/**
 * Model class.
 * A node corresponds to a place within a city,
 * identified by its ID and coordinates.
 * A node can be associated to a delivery.
 * 
 * @author louis
 */
public class Node {
    private String nodeId;
    private Double latitude;
    private Double longitude;
    
    /**
     * @param idNode
     * @param latitude
     * @param longitude
     */
    public Node(String nodeId, Double latitude, Double longitude) {
	this.nodeId = nodeId;
	this.latitude = latitude;
	this.longitude = longitude;
    }

    /**
     * @return the idNode
     */
    public String getNodeId() {
        return this.nodeId;
    }

    /**
     * @param idNode the idNode to set
     */
    public void setIdNode(String nodeId) {
        this.nodeId = nodeId;
    }

    /**
     * @return the latitude
     */
    public Double getLatitude() {
        return this.latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public Double getLongitude() {
        return this.longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
	return "Node [nodeId=" + nodeId + ", latitude=" + latitude + ", longitude=" + longitude + "]";
    }
    
}
