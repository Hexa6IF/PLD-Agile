package controller;

import java.io.File;
import java.util.ArrayList;

import model.Delivery;
import model.FullMap;
import view.Window;
import xml.XMLParser;

/**
 *  Initial state
 *  Waiting to load map
 *  
 * @author marie
 */
public class ErrorState implements State {
    /**
     * State class constructor
     */
    public ErrorState() {}

    @Override
    public void init(Window window, Controller controller) {
	window.disableButtons(true, true, true, true, true, true, true, true);
	File fXmlFile = new File("././src/main/resources/pikachu.xml");
	FullMap map1 = XMLParser.getInstance().parseMap(fXmlFile);
	window.updateMap(map1);
	window.updateDeliveries(new ArrayList<Delivery>());
	controller.setCurrentMap(map1);
    }
    
    @Override
    public void loadMap(Window window, Controller controller, File mapFile) {
	try {
	    FullMap map = XMLParser.getInstance().parseMap(mapFile);
	    if (map.getEdgeList().size() > 0 && map.getNodeMap().size() > 0) {
		try {
		    window.updateMap(map);
		    controller.setCurrentMap(map);
		    controller.setCurrentState(controller.MAP_LOADED_STATE);
		} catch (Exception e) {
		    window.updateMessage("Error in loaded XML file. Please correct it or load another file.");
		    window.clearMap();
		    File fXmlFile = new File("././src/main/resources/pikachu.xml");
		    FullMap map1 = XMLParser.getInstance().parseMap(fXmlFile);
		    window.updateMap(map1);
		    window.updateDeliveries(new ArrayList<Delivery>());
		    controller.setCurrentMap(map1);
		}
	    } else {
		window.updateMessage("The loaded XML file does not match the expected format. Please correct it or load another file.");
		window.clearMap();
		File fXmlFile = new File("././src/main/resources/pikachu.xml");
		    FullMap map1 = XMLParser.getInstance().parseMap(fXmlFile);
		    window.updateMap(map1);
		    window.updateDeliveries(new ArrayList<Delivery>());
		    controller.setCurrentMap(map1);
	    }
	} catch (Exception e) {
	    window.updateMessage("Error in loaded XML file. Please correct it or load another file.");
	    window.clearMap();
	    File fXmlFile = new File("././src/main/resources/pikachu.xml");
	    FullMap map1 = XMLParser.getInstance().parseMap(fXmlFile);
	    window.updateMap(map1);
	    window.updateDeliveries(new ArrayList<Delivery>());
	    controller.setCurrentMap(map1);
	}
    }

    @Override
    public void loadDeliveries(Window window, Controller controller, File deliveriesFile, FullMap map) {
	window.updateMessage("Please load a map before loading deliveries");
    }
}
