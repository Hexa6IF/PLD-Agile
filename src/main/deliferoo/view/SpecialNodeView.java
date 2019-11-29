package view;

import java.time.format.DateTimeFormatter;

import javafx.scene.paint.Color;
import model.SpecialNode;
import model.SpecialNodeType;

/**
 * Class to represent the text view of a special node
 * 
 * @author sadsitha
 *
 */
public class SpecialNodeView {

    private Number deliveryIndex;
    private Color color;
    private SpecialNodeType type;
    private Float duration;
    private String time;
    private SpecialNode node; 

    /**
     * @param deliveryIndex
     * @param color
     * @param type
     * @param duration
     * @param time
     */
    public SpecialNodeView(Number deliveryIndex, Color color, SpecialNodeType type, Float duration, String time) {
	this.deliveryIndex = deliveryIndex;
	this.color = color;
	this.type = type;
	this.duration = duration;
	this.time = time;
    }

    /**
     * @param deliveryIndex
     * @param color
     * @param node
     * @throws Exception 
     */
    public SpecialNodeView(Number deliveryIndex, Color color, SpecialNode node) throws Exception {
	this.node = node;
	this.deliveryIndex = deliveryIndex;
	this.color = color;
	this.type = node.getSpecialNodeType();
	DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");
	if (node.getPassageTime() != null) {
	    this.time = df.format(node.getPassageTime());
	}
	this.duration = node.getDuration().floatValue();
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
    public Float getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(Float duration) {
        this.duration = duration;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return the deliveryIndex
     */
    public Number getDeliveryIndex() {
        return deliveryIndex;
    }

    /**
     * @param deliveryIndex the deliveryIndex to set
     */
    public void setDeliveryIndex(Number deliveryIndex) {
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
