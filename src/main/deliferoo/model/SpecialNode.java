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

    public SpecialNode(Node node, SpecialNodeType specialNodeType, Double duration, LocalTime passageTime, Delivery delivery) {
	this.node = node;
	this.specialNodeType = specialNodeType;
	this.duration = duration;
	this.passageTime = passageTime;
	this.delivery = delivery;
    }

    public Node getNode() {
	return node;
    }

    public void setNode(Node node) {
	this.node = node;
    }

    public SpecialNodeType getSpecialNodeType() {
	return specialNodeType;
    }

    public void setSpecialNodeType(SpecialNodeType specialNodeType) {
	this.specialNodeType = specialNodeType;
    }

    public Double getDuration() {
	return this.duration;
    }
    
    public void setDuration(Double duration) {
	this.duration = duration;
    }
    
    public LocalTime getPassageTime() {
	return this.passageTime;
    }
    
    public void setPassageTime(LocalTime passageTime) {
	this.passageTime = passageTime;
    }

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
