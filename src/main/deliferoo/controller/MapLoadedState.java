package controller;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import model.Delivery;
import model.FullMap;
import view.Window;
import xml.XMLParser;

/**
 * State where map has been loaded
 * Waiting to load deliveries
 * 
 * @author sadsitha
 */
public class MapLoadedState implements State {
    /**
     * State class constructor
     */
    public MapLoadedState() {}

    @Override
    public void init(Window window, Controller controller) {
	window.disableButtons(true, false, true, true, true, true, true, true);
	window.updateMessage("Map succesfully loaded.");
    }
    
    @Override
    public void addButtonClick(Window window, Controller controller) {
	controller.setCurrentState(controller.ADD_DELIVERY_STATE);
    }
    
    @Override
    public void loadMap(Window window, Controller controller, File mapFile) {
	FullMap map = XMLParser.getInstance().parseMap(mapFile);
	if (map.getEdgeList().size()>0 && map.getNodeMap().size()>0) {
	    try {
		window.updateMap(map);
		controller.setCurrentMap(map);
		
	    } catch (Exception e) {
		window.updateMessage("Error in loaded XML file. Please correct it or load an other file.");
		window.clearMap();
		fail(e);
	    }
	    
	} else {
	    window.updateMessage("The loaded XML file does not match the expected format. Please correct it or load an other file.");
	}
    }
    
    @Override
    public void loadDeliveries(Window window, Controller controller, File deliveriesFile, FullMap map) {
	List<Delivery> deliveries = XMLParser.getInstance().parseDeliveries(deliveriesFile, map);
	if (deliveries.size()>0) {
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
