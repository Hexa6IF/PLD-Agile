package controller;

import javafx.geometry.Bounds;
import model.Delivery;
import model.SpecialNode;
import model.SpecialNodeType;
import view.Window;

/**
 * 
 * @author sadsitha
 *
 */
public class AddPickupNodeState implements State {

    /**
     * State class constructor
     */
    public AddPickupNodeState() {}
    
    @Override
    public void init(Window window, Controller controller) {
	Delivery td = new Delivery();
	td.setDeliveryIndex(controller.getCyclist().getDeliveries().size());
	
	controller.setTempDelivery(td);
	window.disableButtons(true, true, true, false, true, true, true, false);
	window.updateMessage("Select pickup location.");
    }
    
    @Override
    public void placeNode(Window window, Controller controller, String nodeId, Bounds bounds) {
	SpecialNode newPickup = new SpecialNode(controller.getCurrentMap().getNodeMap().get(nodeId), 
		SpecialNodeType.PICKUP, 0d, null, controller.getTempDelivery());
	window.drawTempMarker(newPickup, controller.getTempDelivery().getDeliveryIndex());
    }
    
    @Override
    public void confirmButtonClick(Window window, Controller controller) {
	controller.setCurrentState(controller.ADD_DROPOFF_NODE_STATE);
    }
    
    @Override
    public void cancelButtonClick(Window window, Controller controller) {
	window.updateSelectedDelivery(controller.getCyclist().getDeliveries().get(0));
	controller.setSelectedDelivery(controller.getCyclist().getDeliveries().get(0));
	controller.setCurrentState(controller.DELIVERY_SELECTED_STATE);
    }
}
