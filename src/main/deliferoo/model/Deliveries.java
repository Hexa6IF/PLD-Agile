package model;

import java.util.Date;

/**
 * Deliveries model class
 * Deliveries correspond to the list of delivery points that the cyclist have to go
 * 
 * @author louis
 */

public class Deliveries {
    private Delivery[] deliveries;
    private Node warhouseAddress;
    private Date startTime;
    
    /**
     * @param deliveries
     */
    public Deliveries(Delivery[] deliveries, Node address, Date start) {
	this.deliveries = deliveries;
	this.warhouseAddress = address;
	this.startTime = start;
    }

    /**
     * @return the warhouseAddress
     */
    public Node getWarhouseAddress() {
        return this.warhouseAddress;
    }

    /**
     * @param warhouseAddress the warhouseAddress to set
     */
    public void setWarhouseAddress(Node warhouseAddress) {
        this.warhouseAddress = warhouseAddress;
    }

    /**
     * @return the startTime
     */
    public Date getStartTime() {
        return this.startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the deliveries
     */
    public Delivery[] getDeliveries() {
        return this.deliveries;
    }

    /**
     * @param deliveries the deliveries to set
     */
    public void setDeliveries(Delivery[] deliveries) {
        this.deliveries = deliveries;
    }
    
    
}
