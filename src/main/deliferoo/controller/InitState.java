package controller;

import java.io.File;

import model.FullMap;
import view.Window;
import xml.XMLParser;

public class InitState implements State {
    // Initial state
    // Waiting to load map

    /**
     * State class constructor
     */
    public InitState() {

    }

    @Override
    public void loadMap(Window window, Controller controller, File mapFile) {
	FullMap map = XMLParser.getInstance().parseMap(mapFile);
	window.updateMap(map);
	controller.setCurrentMap(map);
	controller.setCurrentState(controller.mapLoadedState);
    }
    
    @Override
    public void loadDeliveries(Window window, Controller controller, File deliveriesFile, FullMap map) {
	window.updateMessage("Please load a map before loading deliveries");
    }
}
	