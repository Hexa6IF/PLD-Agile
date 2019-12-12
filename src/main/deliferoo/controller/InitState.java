package controller;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import model.FullMap;
import view.Window;
import xml.XMLParser;

/**
 *  Initial state
 *  Waiting to load map
 *  
 * @author sadsitha
 */
public class InitState implements State {
    /**
     * State class constructor
     */
    public InitState() {}

    @Override
    public void init(Window window, Controller controller) {
	window.disableButtons(true, true, true, true, true, true, true, true);
	window.updateMessage("Welcome. Please load a map file to continue.");
    }
    
    @Override
    public void loadMap(Window window, Controller controller, File mapFile) {
	FullMap map = XMLParser.getInstance().parseMap(mapFile);
	if (map.getEdgeList().size() > 0 && map.getNodeMap().size() > 0) {
	    try {
		window.updateMap(map);
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
	window.updateMessage("Please load a map before loading deliveries");
    }
}
