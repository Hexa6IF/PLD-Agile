package model;
/**
 * Delivery model class
 * A delivery corresponds to a pair of SpecialNodes with their
 * delivery timelength and pickup timelength
 * 
 * @author louis
 */
public class Delivery {
    private SpecialNode deliveryNode;
    private SpecialNode pickupNode;
    private Integer deliveryIndex;
    
    /**
     * @param deliveryNode
     * @param pickupNode
     */
    public Delivery(SpecialNode deliveryNode, SpecialNode pickupNode, Integer deliveryIndex) {
	this.deliveryNode = deliveryNode;
	this.pickupNode = pickupNode;
	this.deliveryIndex = deliveryIndex;
    }
    /**
     * @return the deliveryNode
     */
    public SpecialNode getDeliveryNode() {
        return deliveryNode;
    }
    /**
     * @param deliveryNode the deliveryNode to set
     */
    public void setDeliveryNode(SpecialNode deliveryNode) {
        this.deliveryNode = deliveryNode;
    }
    /**
     * @return the pickupNode
     */
    public SpecialNode getPickupNode() {
        return pickupNode;
    }
    /**
     * @param pickupNode the pickupNode to set
     */
    public void setPickupNode(SpecialNode pickupNode) {
        this.pickupNode = pickupNode;
    }
    
    public Integer getDeliveryIndex() {
	return this.deliveryIndex;
    }    
}