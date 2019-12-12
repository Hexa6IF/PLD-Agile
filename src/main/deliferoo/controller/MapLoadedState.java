package controller;

import java.io.File;
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
	window.disableButtons(true, false, true, true, true, true);
	window.updateMessage("Map succesfully loaded.");
    }
    
    @Override
    public void addDeliveryClick(Window window, Controller controller) {
	controller.setCurrentState(controller.ADD_DELIVERY_STATE);
    }
    
    @Override
    public void loadMap(Window window, Controller controller, File mapFile) {
	FullMap map = XMLParser.getInstance().parseMap(mapFile);
	window.updateMap(map);
	controller.setCurrentMap(map);
    }
    
    @Override
    public void loadDeliveries(Window window, Controller controller, File deliveriesFile, FullMap map) {
	List<Delivery> deliveries = XMLParser.getInstance().parseDeliveries(deliveriesFile, map);
	window.updateDeliveries(deliveries);
	controller.setDeliveries(deliveries);
	controller.setCurrentState(controller.CALCULATING_ROUND_STATE);
    }
   
}
