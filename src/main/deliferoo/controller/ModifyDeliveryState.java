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
import model.Node;
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
	Map<String, Map<String, BestPath>> tbp = new HashMap<String, Map<String,BestPath>>(controller.getCyclist().getBestPaths());
	for(String id : tbp.keySet()) {
	    tbp.put(id, new HashMap<>(controller.getCyclist().getBestPaths().get(id))); 
	}
	
	Delivery td = new Delivery(controller.getSelectedDelivery());
	
	/* Set temp round to reference the special nodes from temporary delivery */
	List<SpecialNode> tr = new ArrayList<SpecialNode>(controller.getCyclist().getRound());
	tr.set(tr.indexOf(controller.getSelectedDelivery().getPickupNode()), td.getPickupNode());
	tr.set(tr.indexOf(controller.getSelectedDelivery().getDeliveryNode()), td.getDeliveryNode());
	
	controller.setTempBestPaths(tbp);
	controller.setTempDelivery(td);
	controller.setTempRound(tr);
	
	window.enableDeliveryModification(controller.getSelectedDelivery());
	window.setRoundOrdering(false);
	window.disableButtons(true, true, true, false, true, true, true, false);
	window.updateMessage("Modifying selected node.");
    }
    
    @Override
    public void changeNodePosition(Window window, Controller controller, SpecialNode nodeToMove, String newNodeId) {
	Delivery td = controller.getTempDelivery();
	//Delivery sd = controller.getSelectedDelivery();
	
	List<SpecialNode> tr = controller.getTempRound();
	Map<String, Map<String, BestPath>> tbp = controller.getTempBestPaths();
	
	Node newNode = controller.getCurrentMap().getNodeMap().get(newNodeId);
	
	/* Remove old entries that do not correspond anymore */
	/**
	tbp.remove(td.getPickupNode().getNode().getNodeId());
	tbp.remove(td.getDeliveryNode().getNode().getNodeId());
	for(String id : tbp.keySet()) {
	    tbp.get(id).remove(sd.getPickupNode().getNode().getNodeId());
	    tbp.get(id).remove(sd.getDeliveryNode().getNode().getNodeId());
	}**/
	
	if(nodeToMove.getSpecialNodeType() == SpecialNodeType.PICKUP) {
	    td.getPickupNode().setNode(newNode);;
	} else {
	    td.getDeliveryNode().setNode(newNode);;
	}
	
	Map<String, BestPath> fromTempPickup = Dijkstra.calculateShortestPaths(td.getPickupNode(), new HashSet<SpecialNode>(tr), controller.getCurrentMap());
	Map<String, BestPath> fromTempDropoff = Dijkstra.calculateShortestPaths(td.getDeliveryNode(), new HashSet<SpecialNode>(tr), controller.getCurrentMap());
	
	/* Add new entries */
	tbp.put(td.getPickupNode().getNode().getNodeId(), fromTempPickup);
	tbp.put(td.getDeliveryNode().getNode().getNodeId(), fromTempDropoff);
	for(String id : tbp.keySet()) {
	    tbp.get(id).put(td.getPickupNode().getNode().getNodeId(), fromTempPickup.get(id));
	    tbp.get(id).put(td.getDeliveryNode().getNode().getNodeId(), fromTempDropoff.get(id));
	}
	window.updateDeliveryDetail(td);
	window.updateRound(tr, tbp);
    }
    
    @Override
    public void confirmButtonClick(Window window, Controller controller) {
	Map<String, Map<String, BestPath>> newBestPaths = controller.getTempBestPaths();
	Delivery newDelivery = controller.getTempDelivery();
	Command modifyCommand = new CmdModifyDelivery(controller.getCyclist().getDeliveries(), controller.getCyclist().getRound(),
		controller.getSelectedDelivery(), newDelivery, controller.getCyclist().getBestPaths(), newBestPaths);
	controller.doCommand(modifyCommand);

	window.drawMarkers(controller.getCyclist().getDeliveries(), 20);
	window.updateSelectedDelivery(newDelivery);
	controller.setSelectedDelivery(newDelivery);
	controller.setCurrentState(controller.DELIVERY_SELECTED_STATE);
    }
    
    @Override
    public void cancelButtonClick(Window window, Controller controller) {
	window.updateRound(controller.getCyclist().getRound(), controller.getCyclist().getBestPaths());
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
