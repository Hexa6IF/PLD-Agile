package model;
import java.lang.Math;
/**
 * Model class.
 * A node corresponds to a place within a city,
 * identified by its ID and coordinates.
 * A node can be associated to a delivery.
 * 
 * @author louis
 */

public class Node {
    
    private Long idNode;
    private Float latitude;
    private Float longitude;
    private Delivery delivery;
    
    /**
     * @param idNode
     * @param latitude
     * @param longitude
     */
    public Node(Long idNode, Float latitude, Float longitude) {
	this.idNode = idNode;
	this.latitude = latitude;
	this.longitude = longitude;
    }

    /**
     * @return the idNode
     */
    public Long getIdNode() {
        return this.idNode;
    }

    /**
     * @param idNode the idNode to set
     */
    public void setIdNode(Long idNode) {
        this.idNode = idNode;
    }

    /**
     * @return the latitude
     */
    public Float getLatitude() {
        return this.latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public Float getLongitude() {
        return this.longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(Float longitude) {
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
    
    
}
