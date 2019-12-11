package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import model.Delivery;
import model.FullMap;
import model.SpecialNode;
import model.SpecialNodeType;
import view.SpecialNodeTextView;
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
	if(controller.getSelectedDelivery().getDeliveryIndex() == 0) {
	    window.disableButtons(true, false, true, true, false, !controller.canUndo(), !controller.canRedo(), true);
	} else {
	    window.disableButtons(false, false, false, true, false, !controller.canUndo(), !controller.canRedo(), true);
	}
	window.setRoundOrdering(true);
    }
    
    @Override
    public void modifyButtonClick(Window window, Controller controller) {
	controller.setCurrentState(controller.MODIFY_DELIVERY_STATE);
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
    public void changeRoundOrder(Window window, Controller controller, List<SpecialNodeTextView> newOrder) {
	List<Delivery> deliveries = controller.getCyclist().getDeliveries();
	List<SpecialNode> tr = new ArrayList<SpecialNode>();
	
	for(int i = 0; i < newOrder.size(); i++) {
	    SpecialNodeTextView sntv = newOrder.get(i);
	    Integer deliveryId = sntv.getDeliveryIndex();
	    SpecialNodeType type = sntv.getType();
	    if(type == SpecialNodeType.START || type == SpecialNodeType.PICKUP) {
		tr.add(i, deliveries.get(deliveryId).getPickupNode());
	    } else {
		tr.add(i, deliveries.get(deliveryId).getDeliveryNode());
	    }
	}
	CalculationHelper.updatePrecedences(tr);
	controller.doCommand(new CmdModifyRound(controller.getCyclist().getRound(), tr));
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
