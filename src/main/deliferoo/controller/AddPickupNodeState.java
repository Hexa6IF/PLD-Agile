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
	public AddPickupNodeState() {
	}

	@Override
	public void init(Window window, Controller controller) {
		Delivery td = new Delivery();
		td.setDeliveryIndex(controller.getCyclist().getDeliveries().size());

		controller.setTempDelivery(td);
		window.disableButtons(true, true, true, false, true, true, true, false);
		window.updateDeliveryDetail(td);
		window.setDurationEdit(true, false);
		window.setRoundOrdering(false);
		window.updateMessage("Select pickup location and enter pickup duration.");
	}

	@Override
	public void placeNode(Window window, Controller controller, String nodeId, Bounds bounds) {
		SpecialNode newPickup = new SpecialNode(controller.getCurrentMap().getNodeMap().get(nodeId),
				SpecialNodeType.PICKUP, 0d, null, controller.getTempDelivery());
		controller.getTempDelivery().setPickupNode(newPickup);
		window.drawTempMarker(newPickup, controller.getTempDelivery().getDeliveryIndex());
	}

	@Override
	public void confirmButtonClick(Window window, Controller controller) {
		try {
			controller.getTempDelivery().getPickupNode().setDuration(window.getPickupDuration());
			controller.setCurrentState(controller.ADD_DROPOFF_NODE_STATE);
		} catch (Exception e) {
			window.updateMessage("Please select a node and enter the duration before confirming");
		}
	}

	@Override
	public void cancelButtonClick(Window window, Controller controller) {
		window.updateSelectedDelivery(controller.getCyclist().getDeliveries().get(0));
		window.setDurationEdit(false, false);
		window.clearTempMarkers();
		controller.setSelectedDelivery(controller.getCyclist().getDeliveries().get(0));
		controller.setCurrentState(controller.DELIVERY_SELECTED_STATE);
	}
}
