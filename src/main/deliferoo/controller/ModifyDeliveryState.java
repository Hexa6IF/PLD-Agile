package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import algorithm.Dijkstra;
import model.BestPath;
import model.Delivery;
import model.FullMap;
import model.SpecialNode;
import model.SpecialNodeType;
import view.Window;
import xml.XMLParser;

/**
 * 
 * @author sadsitha
 *
 */
public class ModifyDeliveryState implements State {

    /**
     * State class constructor
     */
    public ModifyDeliveryState() {}
    
    @Override
    public void init(Window window, Controller controller) {
	Delivery selected = controller.getSelectedDelivery();
	SpecialNode tempPickupNode = new SpecialNode(selected.getPickupNode());
	SpecialNode tempDropoffNode = new SpecialNode(selected.getDeliveryNode());
	
	controller.setTmpDelivery(new Delivery(tempDropoffNode, tempPickupNode, selected.getDeliveryIndex()));
	window.disableButtons(true, true, true, false, true, true, true, false);
	window.updateMessage("Modifying selected node.");
    }
    
    @Override
    public void moveSpecialNode(Window window, Controller controller, SpecialNode nodeToMove, String newNodeId) {
	SpecialNode tmpNode = new SpecialNode(nodeToMove);
	
	tmpNode.setNode(controller.getCurrentMap().getNodeMap().get(newNodeId));
	
	if(nodeToMove.getSpecialNodeType() == SpecialNodeType.PICKUP) {
	    controller.getTmpDelivery().setPickupNode(tmpNode);
	} else {
	    controller.getTmpDelivery().setDeliveryNode(tmpNode);
	}
	
	SpecialNode tmpPickup = controller.getTmpDelivery().getPickupNode();
	SpecialNode tmpDropoff = controller.getTmpDelivery().getDeliveryNode();
	SpecialNode oriPickup = controller.getSelectedDelivery().getPickupNode();
	SpecialNode oriDropoff = controller.getSelectedDelivery().getDeliveryNode();
	
	List<SpecialNode> tmpRound = new ArrayList<SpecialNode>(controller.getCyclist().getRound());
	
	Map<String, Map<String, BestPath>> bestPaths = new HashMap<String, Map<String, BestPath>>(controller.getCyclist().getShortestPaths());	
	
	tmpRound.set(tmpRound.indexOf(oriPickup), tmpPickup);
	tmpRound.set(tmpRound.indexOf(oriDropoff), tmpDropoff);
	
	bestPaths.remove(oriPickup.getNode().getNodeId());
	bestPaths.remove(oriDropoff.getNode().getNodeId());
	
	Map<String, BestPath> fromTmpPickup = Dijkstra.calculateShortestPaths(tmpPickup, new HashSet<SpecialNode>(tmpRound), controller.getCurrentMap());
	Map<String, BestPath> fromTmpDropoff = Dijkstra.calculateShortestPaths(tmpDropoff, new HashSet<SpecialNode>(tmpRound), controller.getCurrentMap());
	
	for(String id : bestPaths.keySet()) {
	    bestPaths.put(id, new HashMap<String, BestPath>(bestPaths.get(id)));
	    bestPaths.get(id).remove(oriPickup.getNode().getNodeId());
	    bestPaths.get(id).remove(oriDropoff.getNode().getNodeId());
	    bestPaths.get(id).put(tmpPickup.getNode().getNodeId(), fromTmpPickup.get(id));
	    bestPaths.get(id).put(tmpDropoff.getNode().getNodeId(), fromTmpDropoff.get(id));
	}
	bestPaths.put(tmpPickup.getNode().getNodeId(), fromTmpPickup);
	bestPaths.put(tmpDropoff.getNode().getNodeId(), fromTmpDropoff);
	window.updateRound(tmpRound, bestPaths);
    }
    
    @Override
    public void confirmButtonClick(Window window, Controller controller) {
	controller.doCommand(new CmdModifyDelivery(controller.getCyclist().getDeliveries(), controller.getSelectedDelivery(), controller.getTmpDelivery()));
	controller.setCurrentState(controller.DELIVERY_SELECTED_STATE);
    }
    
    @Override
    public void cancelButtonClick(Window window, Controller controller) {
	window.updateRound(controller.getCyclist().getRound(), controller.getCyclist().getShortestPaths());
	window.drawMarkers(controller.getCyclist().getDeliveries(), 20);
	window.updateSelectedDelivery(controller.getSelectedDelivery());
	controller.setCurrentState(controller.DELIVERY_SELECTED_STATE);
    }
    
    @Override
    public void loadMap(Window window, Controller controller, File mapFile) {
	FullMap map = XMLParser.getInstance().parseMap(mapFile);
	window.updateMap(map);
	window.updateDeliveries(new ArrayList<Delivery>());
	controller.setCurrentMap(map);
	controller.setCurrentState(controller.MAP_LOADED_STATE);
    }
    
    @Override
    public void loadDeliveries(Window window, Controller controller, File deliveriesFile, FullMap map) {
	List<Delivery> deliveries = XMLParser.getInstance().parseDeliveries(deliveriesFile, map);
	window.updateDeliveries(deliveries);
	controller.getCyclist().setDeliveries(deliveries);
	controller.setCurrentState(controller.CALCULATING_ROUND_STATE);
    }
}
