package model;


import java.util.Date;

/**
 * Delivery model class
 * A delivery corresponds to a pair of Nodes with their
 * delivery timelength and pickup timelength
 * 
 * @author louis
 */
public class Delivery {
    private Node deliveryNode;
    private Node pickupNode;
    private Date deliveryTimelength;
    private Date pickupTimelength;
    
    /**
     * @param deliveryNode
     * @param pickupNode
     * @param deliveryTimelength
     * @param pickupTimelength
     */
    public Delivery(Node deliveryNode, Node pickupNode, Date deliveryTimelength, Date pickupTimelength) {
	this.deliveryNode = deliveryNode;
	this.pickupNode = pickupNode;
	this.deliveryTimelength = deliveryTimelength;
	this.pickupTimelength = pickupTimelength;
    }
    /**
     * @return the deliveryNode
     */
    public Node getDeliveryNode() {
        return this.deliveryNode;
    }
    /**
     * @param deliveryNode the deliveryNode to set
     */
    public void setDeliveryNode(Node deliveryNode) {
        this.deliveryNode = deliveryNode;
    }
    /**
     * @return the pickupNode
     */
    public Node getPickupNode() {
        return this.pickupNode;
    }
    /**
     * @param pickupNode the pickupNode to set
     */
    public void setPickupNode(Node pickupNode) {
        this.pickupNode = pickupNode;
    }
    /**
     * @return the deliveryTimelength
     */
    public Date getDeliveryTimelength() {
        return this.deliveryTimelength;
    }
    /**
     * @param deliveryTimelength the deliveryTimelength to set
     */
    public void setDeliveryTimelength(Date deliveryTimelength) {
        this.deliveryTimelength = deliveryTimelength;
    }
    /**
     * @return the pickupTimelength
     */
    public Date getPickupTimelength() {
        return this.pickupTimelength;
    }
    /**
     * @param pickupTimelength the pickupTimelength to set
     */
    public void setPickupTimelength(Date pickupTimelength) {
        this.pickupTimelength = pickupTimelength;
    }
    
    
}
