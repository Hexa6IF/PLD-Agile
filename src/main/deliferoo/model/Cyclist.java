package model;

import java.util.List;
import java.util.Map;

/**
 * Model class representing the cyclist containing its to-do deliveries and the
 * circuit it may follow
 * 
 * @author
 */
public class Cyclist {
    private Integer startTime;
    private Map<String, Map<String, BestPath>> bestPaths;
    private List<Delivery> deliveries;
    private List<SpecialNode> round;

    public Cyclist() {}

    public Integer getStartTime() {
	return this.startTime;
    }

    public void setStartTime(Integer startTime) {
	this.startTime = startTime;
    }
    
    public Map<String, Map<String, BestPath>> getBestPaths() {
	return this.bestPaths;
    }
    
    public void setShortestPaths(Map<String, Map<String, BestPath>> bestPaths) {
	this.bestPaths = bestPaths;
    }
    
    public List<Delivery> getDeliveries() {
	return this.deliveries;
    }

    public void setDeliveries(List<Delivery> deliveries) {
	this.deliveries = deliveries;
    }

    public List<SpecialNode> getRound() {
	return round;
    }

    public void setRound(List<SpecialNode> round) {
	this.round = round;
    }
}
