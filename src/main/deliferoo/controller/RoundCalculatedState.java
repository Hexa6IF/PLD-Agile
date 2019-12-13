package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import model.Delivery;
import model.FullMap;
import model.SpecialNodeType;
import view.Window;
import xml.XMLParser;

/**
 * State where deliveries have been loaded and round has been calculated
 * 
 * @author sadsitha
 */
public class RoundCalculatedState implements State {
    /**
     * State class constructor
     */
    public RoundCalculatedState() {}

    @Override
    public void init(Window window, Controller controller) {
	window.disableButtons(true, false, true, true, true, !controller.canUndo(), !controller.canRedo(), true);
	window.updateMessage("Round succesfully calculated");
    }
    
    @Override
    public void selectDeliveryClick(Window window, Controller controller, Integer deliveryIndex, SpecialNodeType type) {
	for(Delivery delivery : controller.getCyclist().getDeliveries()) {
	    if(delivery != null && delivery.getDeliveryIndex() == deliveryIndex) {
		window.updateSelectedDelivery(delivery);
		controller.setSelectedDelivery(delivery);
		break;
	    }
	}
	controller.setCurrentState(controller.DELIVERY_SELECTED_STATE);
    }
    
    @Override
    public void addButtonClick(Window window, Controller controller) {
	controller.setCurrentState(controller.ADD_PICKUP_NODE_STATE);
    }
    
    @Override
    public void loadMap(Window window, Controller controller, File mapFile) {
	FullMap map = XMLParser.getInstance().parseMap(mapFile);
	if (map.getEdgeList().size() > 0 && map.getNodeMap().size() > 0) {
	    try {
		window.updateMap(map);
		window.updateDeliveries(new ArrayList<Delivery>());
		controller.setCurrentMap(map);
		controller.setCurrentState(controller.MAP_LOADED_STATE);
	    } catch (Exception e) {
		window.updateMessage("Error in loaded XML file. Please correct it or load another file.");
		window.clearMap();
	    }
	} else {
	    window.updateMessage("The loaded XML file does not match the expected format. Please correct it or load another file.");
	}
    }

    @Override
    public void loadDeliveries(Window window, Controller controller, File deliveriesFile, FullMap map) {
	List<Delivery> deliveries = XMLParser.getInstance().parseDeliveries(deliveriesFile, map);
	if (deliveries.size() > 0) {
	    try {
		window.updateDeliveries(deliveries);
		controller.getCyclist().setDeliveries(deliveries);
		controller.setCurrentState(controller.CALCULATING_ROUND_STATE);
	    } catch (Exception e) {
		window.updateMessage("Error in loaded XML file. Please correct it or load an other file.");
		window.clearDeliveriesMarkers();
		List<Delivery> emptyDeliveries = new ArrayList<>();
		window.updateDeliveries(emptyDeliveries);
		window.clearDeliveriesRound();
	    }
	} else {
	    window.updateMessage("The loaded XML file does not match the expected format. Please correct it or load an other file.");
	}
    }
}
