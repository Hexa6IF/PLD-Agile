package model;
/**
 * Deliveries model class
 * Deliveries correspond to the list of delivery points that the cyclist have to go
 * 
 * @author louis
 */

public class Deliveries {
    private Delivery[] deliveries;
    
    /**
     * @param deliveries
     */
    public Deliveries(Delivery[] deliveries) {
	this.deliveries = deliveries;
    }

    /**
     * @return the deliveries
     */
    public Delivery[] getDeliveries() {
        return deliveries;
    }

    /**
     * @param deliveries the deliveries to set
     */
    public void setDeliveries(Delivery[] deliveries) {
        this.deliveries = deliveries;
    }
    
    
}
