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
	deliveries.remove(from);
	deliveries.add(to);
	
	round.set(round.indexOf(from.getPickupNode()), to.getPickupNode());
	round.set(round.indexOf(from.getDeliveryNode()), to.getDeliveryNode());
	
	bestPaths.put(to.getPickupNode().getNode().getNodeId(), modifiedBestPaths.get(to.getPickupNode().getNode().getNodeId()));
	bestPaths.put(to.getDeliveryNode().getNode().getNodeId(), modifiedBestPaths.get(to.getDeliveryNode().getNode().getNodeId()));
	for(String id : bestPaths.keySet()) {
	    bestPaths.get(id).put(to.getPickupNode().getNode().getNodeId(), modifiedBestPaths.get(to.getPickupNode().getNode().getNodeId()).get(id));
	    bestPaths.get(id).put(to.getDeliveryNode().getNode().getNodeId(), modifiedBestPaths.get(to.getDeliveryNode().getNode().getNodeId()).get(id));
	}
	
    }
    
    public void undoCmd() {
	deliveries.remove(to);
	deliveries.add(from);
    }
}