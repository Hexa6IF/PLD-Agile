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
    private String idNode;
    private Double latitude;
    private Double longitude;
    private Delivery delivery;
    
    /**
     * @param idNode
     * @param latitude
     * @param longitude
     */
    public Node(String idNode, Double latitude, Double longitude) {
	this.idNode = idNode;
	this.latitude = latitude;
	this.longitude = longitude;
    }
    
    /**
     * Copy constructor
     * @param otherNode
     */
    public Node(Node otherNode) {
	this.idNode = otherNode.getIdNode();
	this.latitude = otherNode.getLatitude();
	this.longitude = otherNode.getLongitude();
    }

    /**
     * @return the idNode
     */
    public String getIdNode() {
        return this.idNode;
    }

    /**
     * @param idNode the idNode to set
     */
    public void setIdNode(String idNode) {
        this.idNode = idNode;
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

    /**
     * @return the delivery
     */
    public Delivery getDelivery() {
        return this.delivery;
    }

    /**
     * @param delivery the delivery to set
     */
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
    
    public String toString() {
	return "[Node id=" + this.idNode + " latitude=" + this.latitude + " longitude=" + this.longitude + "]\r\n";
    }
}
