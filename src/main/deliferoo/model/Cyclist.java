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
    private Number speed;

    public Cyclist(Number speed) {
	this.speed = speed;
    }


    /**
     * @return the speed
     */
    public Number getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(Number speed) {
        this.speed = speed;
    }


    /**
     * @return the startTime
     */
    public Integer getStartTime() {
        return startTime;
    }


    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }


    /**
     * @return the bestPaths
     */
    public Map<String, Map<String, BestPath>> getBestPaths() {
        return bestPaths;
    }


    /**
     * @param bestPaths the bestPaths to set
     */
    public void setBestPaths(Map<String, Map<String, BestPath>> bestPaths) {
        this.bestPaths = bestPaths;
    }


    /**
     * @return the deliveries
     */
    public List<Delivery> getDeliveries() {
        return deliveries;
    }


    /**
     * @param deliveries the deliveries to set
     */
    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }


    /**
     * @return the round
     */
    public List<SpecialNode> getRound() {
        return round;
    }


    /**
     * @param round the round to set
     */
    public void setRound(List<SpecialNode> round) {
        this.round = round;
    }
}
