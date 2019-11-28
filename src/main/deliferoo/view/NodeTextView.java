package view;

import javafx.scene.paint.Color;

/**
 * Class to represent the text view of a special node
 * 
 * @author sadsitha
 *
 */
public class NodeTextView {

    public enum NodeType {
	DROPOFF, PICKUP, NORMAL;
    }

    private Number deliveryIndex;
    private Color color;
    private NodeType type;
    private Float duration;
    private String time;

    /**
     * @param deliveryIndex
     * @param color
     * @param type
     * @param duration
     * @param time
     */
    public NodeTextView(Number deliveryIndex, Color color, NodeType type, Float duration, String time) {
	this.deliveryIndex = deliveryIndex;
	this.color = color;
	this.type = type;
	this.duration = duration;
	this.time = time;
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
    public NodeType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(NodeType type) {
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
}
