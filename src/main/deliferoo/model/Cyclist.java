package model;

import java.util.List;

/**
 * Model class representing the cyclist containing its
 * to-do deliveries and the circuit it may follow
 * 
 * @author 
 */
public class Cyclist {
    private Integer startTime;
    private List<Delivery> deliveries;
    private Round round;

    public Cyclist() {
	super();
    }
    
    public Integer getStartTime() {
	return this.startTime;
    }
    
    public void setStartTime(Integer startTime) {
	this.startTime = startTime;
    }
    
    public List<Delivery> getDeliveries() {
	return this.deliveries;
    }
    
    public void setDeliveries(List<Delivery> deliveries) {
	this.deliveries = deliveries;
    }
    
    public Round getRound() {
	return round;
    }
    
    public void setRound(Round round) {
	this.round = round;
    }
}
