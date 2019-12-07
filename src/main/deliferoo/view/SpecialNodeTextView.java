package view;

import java.time.LocalTime;

import javafx.scene.paint.Color;
import model.SpecialNode;
import model.SpecialNodeType;

/**
 * Class to represent the text view of a special node
 * 
 * @author sadsitha
 *
 */
public class SpecialNodeTextView {

    private Integer deliveryIndex;
    private Color color;
    private SpecialNodeType type;
    private Double duration;
    private LocalTime time;
    private SpecialNode node;
    
/**
 * Constructor
 * 
 * @param specialNode
 * @param color
 */
    public SpecialNodeTextView(SpecialNode specialNode, Color color) {
	this.deliveryIndex = specialNode.getDelivery().getDeliveryIndex();
	this.color = color;
	this.type = specialNode.getSpecialNodeType();
	this.duration = specialNode.getDuration();
	this.time = specialNode.getPassageTime();
	this.node = specialNode;
    }

    /**
     * @return the color
     */
    public Color getColor() {
	return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
	this.color = color;
    }

    /**
     * @return the type
     */
    public SpecialNodeType getType() {
	return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(SpecialNodeType type) {
	this.type = type;
    }

    /**
     * @return the duration
     */
    public Double getDuration() {
	return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(Double duration) {
	this.duration = duration;
    }

    /**
     * @return the time
     */
    public LocalTime getTime() {
	return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(LocalTime time) {
	this.time = time;
    }

    /**
     * @return the deliveryIndex
     */
    public Integer getDeliveryIndex() {
	return deliveryIndex;
    }

    /**
     * @param deliveryIndex the deliveryIndex to set
     */
    public void setDeliveryIndex(Integer deliveryIndex) {
	this.deliveryIndex = deliveryIndex;
    }

    /**
     * @return the node
     */
    public SpecialNode getNode() {
	return node;
    }

    /**
     * @param node the node to set
     */
    public void setNode(SpecialNode node) {
	this.node = node;
    }
}
