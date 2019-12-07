package model;

import java.time.LocalTime;;

/**
 * Model class.
 * SpecialNode corresponds to a Node used in a delivery 
 * 
 * @author 
 */
public class SpecialNode {
    private Node node;
    private SpecialNodeType specialNodeType;
    private Double duration;
    private LocalTime passageTime;
    private Delivery delivery;

    /**
     * 
     * @param node
     * @param specialNodeType
     * @param duration
     * @param passageTime
     * @param delivery
     */
    public SpecialNode(Node node, SpecialNodeType specialNodeType, Double duration, LocalTime passageTime, Delivery delivery) {
	this.node = node;
	this.specialNodeType = specialNodeType;
	this.duration = duration;
	this.passageTime = passageTime;
	this.delivery = delivery;
    }

    /**
     * 
     * @return node
     */
    public Node getNode() {
	return node;
    }

    /**
     * 
     * @param node
     */
    public void setNode(Node node) {
	this.node = node;
    }

    /**
     * 
     * @return specialNodeType
     */
    public SpecialNodeType getSpecialNodeType() {
	return specialNodeType;
    }

    /**
     * 
     * @param specialNodeType
     */
    public void setSpecialNodeType(SpecialNodeType specialNodeType) {
	this.specialNodeType = specialNodeType;
    }

    /**
     * @return duration in min
     */
    public Double getDuration() {
	return this.duration;
    }
    
    /**
     * 
     * @param duration
     */
    public void setDuration(Double duration) {
	this.duration = duration;
    }
    
    /**
     * 
     * @return passageTime
     */
    public LocalTime getPassageTime() {
	return this.passageTime;
    }
    
    /**
     * 
     * @param passageTime
     */
    public void setPassageTime(LocalTime passageTime) {
	this.passageTime = passageTime;
    }

    /**
     * @return string representation
     */
    @Override
    public String toString() {
	return "SpecialNode [node=" + node.toString() + ", duration=" + duration + ", passageTime=" + passageTime + "type=" + specialNodeType + "]";
    }

    /**
     * @return the delivery
     */
    public Delivery getDelivery() {
        return delivery;
    }

    /**
     * @param delivery the delivery to set
     */
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
}
