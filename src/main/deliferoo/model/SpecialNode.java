package model;

import java.util.Date;

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
    private Date passageTime;

    public SpecialNode(Node node, SpecialNodeType specialNodeType, Double duration, Date passageTime) {
	this.node = node;
	this.specialNodeType = specialNodeType;
	this.duration = duration;
	this.passageTime = passageTime;
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
    
    public Date getPassageTime() {
	return this.passageTime;
    }
    
    public void setPassageTime(Date passageTime) {
	this.passageTime = passageTime;
    }

    @Override
    public String toString() {
	return "SpecialNode [node=" + node.toString() + ", duration=" + duration + ", passageTime=" + passageTime + "]";
    }
}
