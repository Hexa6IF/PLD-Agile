package model;

/**
 * Delivery model class A delivery corresponds to a pair of SpecialNodes with
 * their delivery timelength and pickup timelength
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
     * @param deliveryIndex
     */
    public Delivery(SpecialNode deliveryNode, SpecialNode pickupNode, Integer deliveryIndex) {
	this.deliveryNode = deliveryNode;
	this.pickupNode = pickupNode;
	this.deliveryIndex = deliveryIndex;
    }
    
    public Delivery(Delivery delivery) {
	this.deliveryNode = new SpecialNode(delivery.getDeliveryNode());
	this.pickupNode = new SpecialNode(delivery.getPickupNode());
	this.deliveryIndex = delivery.getDeliveryIndex();
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

    /**
     * @return the deliveryIndex
     */
    public Integer getDeliveryIndex() {
	return this.deliveryIndex;
    }

    @Override
    public String toString() {
	return "Delivery [deliveryNode=" + deliveryNode.toString() + ", pickupNode=" + pickupNode.toString()
		+ ", deliveryIndex=" + deliveryIndex + "]";
    }

    /**
     * @param deliveryIndex the deliveryIndex to set
     */
    public void setDeliveryIndex(Integer deliveryIndex) {
	this.deliveryIndex = deliveryIndex;
    }
}