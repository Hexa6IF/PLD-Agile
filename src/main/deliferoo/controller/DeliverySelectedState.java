package controller;

import java.io.File;
import java.util.ArrayList;
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
public class DeliverySelectedState implements State {

    /**
     * State class constructor
     */
    public DeliverySelectedState() {}
    
    @Override
    public void init(Window window, Controller controller) {
	window.disableButtons(false, false, false, true, true, true);
	window.updateMessage("Node selection...");
    }
    
    @Override
    public void selectDeliveryClick(Window window, Controller controller, Integer deliveryIndex) {
	for(Delivery delivery : controller.cyclist.getDeliveries()) {
	    if(delivery.getDeliveryIndex() == deliveryIndex) {
		window.updateSelectedDelivery(delivery);
		break;
	    }
	}
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
	controller.setDeliveries(deliveries);
	controller.setCurrentState(controller.CALCULATING_ROUND_STATE);
    }
}
