package controller;

import java.io.File;
import java.util.List;

import model.Delivery;
import model.FullMap;
import view.Window;
import xml.XMLParser;

/**
 * 
 * @author sadsitha
 *
 */
public class MapLoadedState implements State {
    // State where map has been loaded
    // Waiting to load deliveries

    /**
     * State class constructor
     */
    public MapLoadedState() {

    }

    @Override
    public void loadMap(Window window, Controller controller, File mapFile) {
	FullMap map = XMLParser.getInstance().parseMap(mapFile);
	controller.setCurrentMap(map);
	window.updateMap(map);
    }
    
    @Override
    public void loadDeliveries(Window window, Controller controller, File deliveriesFile, FullMap map) {
	List<Delivery> deliveries = XMLParser.getInstance().parseDeliveries(deliveriesFile, map);
	controller.setDeliveries(deliveries);
	window.updateDeliveries(deliveries);
	controller.deliveriesLoadedState.calculateRound(window, controller, deliveries, map);
	controller.setCurrentState(controller.deliveriesLoadedState);
    }
}
