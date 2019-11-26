package model;
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
    private Integer deliveryTimelength;
    private Integer pickupTimelength;
    
    /**
     * @param deliveryNode
     * @param pickupNode
     * @param deliveryTimelength
     * @param pickupTimelength
     */
    public Delivery(Node deliveryNode, Node pickupNode, Integer deliveryTimelength, Integer pickupTimelength) {
	this.deliveryNode = deliveryNode;
	this.pickupNode = pickupNode;
	this.deliveryTimelength = deliveryTimelength;
	this.pickupTimelength = pickupTimelength;
    }
    /**
     * @return the deliveryNode
     */
    public Node getDeliveryNode() {
        return deliveryNode;
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
        return pickupNode;
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
    public Integer getDeliveryTimelength() {
        return deliveryTimelength;
    }
    /**
     * @param deliveryTimelength the deliveryTimelength to set
     */
    public void setDeliveryTimelength(Integer deliveryTimelength) {
        this.deliveryTimelength = deliveryTimelength;
    }
    /**
     * @return the pickupTimelength
     */
    public Integer getPickupTimelength() {
        return pickupTimelength;
    }
    /**
     * @param pickupTimelength the pickupTimelength to set
     */
    public void setPickupTimelength(Integer pickupTimelength) {
        this.pickupTimelength = pickupTimelength;
    }
    
    
}
