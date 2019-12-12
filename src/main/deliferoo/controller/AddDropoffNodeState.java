package controller;

import javafx.geometry.Bounds;
import model.Delivery;
import model.SpecialNode;
import model.SpecialNodeType;
import view.Window;

public class AddDropoffNodeState implements State{
    
    /**
     * State class constructor
     */
    public AddDropoffNodeState() {}
    
    @Override
    public void init(Window window, Controller controller) {
	Delivery td = new Delivery();
	td.setDeliveryIndex(controller.getCyclist().getDeliveries().size());
	
	controller.setTempDelivery(td);
	window.disableButtons(true, true, true, false, true, true, true, false);
	window.updateMessage("Select dropoff location.");
    }
    
    @Override
    public void placeNode(Window window, Controller controller, String nodeId, Bounds bounds) {
	SpecialNode newDropoff = new SpecialNode(controller.getCurrentMap().getNodeMap().get(nodeId), 
		SpecialNodeType.DROPOFF, 0d, null, controller.getTempDelivery());
	window.drawTempMarker(newDropoff, controller.getTempDelivery().getDeliveryIndex());
    }
    
    @Override
    public void confirmButtonClick(Window window, Controller controller) {
	window.updateDeliveryDetail(controller.getTempDelivery());
	controller.setCurrentState(controller.ADD_DROPOFF_NODE_STATE);
    }
}
