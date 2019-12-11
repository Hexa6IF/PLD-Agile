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
	window.updateMessage(controller.getSelectedDelivery().getPickupNode().toString());
	tr.set(tr.indexOf(controller.getSelectedDelivery().getPickupNode()), td.getPickupNode());
	tr.set(tr.indexOf(controller.getSelectedDelivery().getDeliveryNode()), td.getDeliveryNode());

	controller.setTempBestPaths(tbp);
	controller.setTempDelivery(td);
	controller.setTempRound(tr);

	window.enableDeliveryModification(controller.getSelectedDelivery());
	window.setRoundOrdering(false);
	window.disableButtons(true, true, true, false, true, true, true, false);
	//window.updateMessage("Modifying selected node.");
    }

    @Override
    public void changeNodePosition(Window window, Controller controller, Integer deliveryId, SpecialNodeType type, String newNodeId) {
	Node newNode = controller.getCurrentMap().getNodeMap().get(newNodeId);
	
	SpecialNode start = controller.getTempDelivery().getPickupNode();
	SpecialNode end = controller.getTempDelivery().getDeliveryNode();

	List<SpecialNode> tr = controller.getTempRound();
	Map<String, Map<String, BestPath>> tbp = controller.getTempBestPaths();

	if(type == SpecialNodeType.PICKUP) {
	    start.setNode(newNode);;
	} else {
	    end.setNode(newNode);;
	}

	Map<String, BestPath> fromTempPickup = Dijkstra.calculateShortestPaths(start, new HashSet<SpecialNode>(tr), controller.getCurrentMap());
	Map<String, BestPath> fromTempDropoff = Dijkstra.calculateShortestPaths(end, new HashSet<SpecialNode>(tr), controller.getCurrentMap());

	/* Add new entries */
	if(tbp.containsKey(start.getNode().getNodeId())) {
	    for(String id : fromTempPickup.keySet()) {
		tbp.get(start.getNode().getNodeId()).put(id, fromTempPickup.get(id));
	    }
	} else {
	    tbp.put(start.getNode().getNodeId(), fromTempPickup);
	}
	
	if(tbp.containsKey(end.getNode().getNodeId())) {
	    for(String id : fromTempDropoff.keySet()) {
		tbp.get(end.getNode().getNodeId()).put(id, fromTempDropoff.get(id));
	    }
	} else {
	    tbp.put(end.getNode().getNodeId(), fromTempDropoff);
	}
	for(String id : tbp.keySet()) {
	    tbp.get(id).put(start.getNode().getNodeId(), fromTempPickup.get(id));
	    tbp.get(id).put(end.getNode().getNodeId(), fromTempDropoff.get(id));
	}
	
	window.updateDeliveryDetail(controller.getTempDelivery());
	window.updateRound(tr, tbp);
    }

    public void changeNodeDuration(Window window, Controller controller, Integer deliveryId) {
	
    }
    
    @Override
    public void confirmButtonClick(Window window, Controller controller) {
	Map<String, Map<String, BestPath>> newBestPaths = controller.getTempBestPaths();
	Delivery newDelivery = controller.getTempDelivery();
	Command modifyCommand = new CmdModifyDelivery(controller.getCyclist().getDeliveries(), controller.getCyclist().getRound(),
		controller.getSelectedDelivery(), controller.getTempDelivery(), controller.getCyclist().getBestPaths(), newBestPaths);
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
