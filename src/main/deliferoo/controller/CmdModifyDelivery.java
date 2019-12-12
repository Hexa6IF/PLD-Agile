package controller;

import java.util.List;
import java.util.Map;

import model.BestPath;
import model.Delivery;
import model.SpecialNode;

public class CmdModifyDelivery implements Command {
    
    private List<Delivery> deliveries;
    private List<SpecialNode> round;
    private Map<String, Map<String, BestPath>> bestPaths;
    private Map<String, Map<String, BestPath>> modifiedBestPaths;
    
    private Delivery from;
    private Delivery to;
    
    public CmdModifyDelivery(List<Delivery> deliveries, List<SpecialNode> round, Delivery from, Delivery to, 
	    Map<String, Map<String, BestPath>> original, Map<String, Map<String, BestPath>> modified) {
	this.deliveries = deliveries;
	this.round = round;
	this.from = from;
	this.to = to;
	this.bestPaths = original;
	this.modifiedBestPaths = modified;
    }
    
    public void doCmd() {
	deliveries.set(deliveries.indexOf(from), to);
	round.set(round.indexOf(from.getPickupNode()), to.getPickupNode());
	round.set(round.indexOf(from.getDeliveryNode()), to.getDeliveryNode());
	String pickupNodeId = to.getPickupNode().getNode().getNodeId();
	String deliveryNodeId = to.getDeliveryNode().getNode().getNodeId();
	bestPaths.put(pickupNodeId, modifiedBestPaths.get(pickupNodeId));
	bestPaths.put(deliveryNodeId, modifiedBestPaths.get(deliveryNodeId));
	for(String id : bestPaths.keySet()) {
	    bestPaths.get(id).put(pickupNodeId, modifiedBestPaths.get(pickupNodeId).get(id));
	    bestPaths.get(id).put(deliveryNodeId, modifiedBestPaths.get(deliveryNodeId).get(id));
	}
    }
    
    public void undoCmd() {	
	deliveries.set(deliveries.indexOf(to), from);
	
	round.set(round.indexOf(to.getPickupNode()), from.getPickupNode());
	round.set(round.indexOf(to.getDeliveryNode()), from.getDeliveryNode());
    }
}