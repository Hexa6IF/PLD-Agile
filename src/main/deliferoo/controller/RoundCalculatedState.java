package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import model.Delivery;
import model.FullMap;
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
	window.disableButtons(true, false, true, true, false, true, true, true);
	window.updateMessage("Round succesfully calculated");
    }
    
    @Override
    public void selectDeliveryClick(Window window, Controller controller, Integer deliveryIndex) {
	for(Delivery delivery : controller.getCyclist().getDeliveries()) {
	    if(delivery.getDeliveryIndex() == deliveryIndex) {
		window.updateSelectedDelivery(delivery);
		controller.setSelectedDelivery(delivery);
		break;
	    }
	}
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
    //TO DELETE
    @Override
    public void calculateButtonClick(Window window, Controller controller) {
    	window.drawSimulation();
    	State.super.calculateButtonClick(window, controller);
    }
}
