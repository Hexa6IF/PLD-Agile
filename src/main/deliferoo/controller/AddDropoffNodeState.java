package controller;

import javafx.geometry.Bounds;
import model.SpecialNode;
import model.SpecialNodeType;
import view.Window;

public class AddDropoffNodeState implements State {

	/**
	 * State class constructor
	 */
	public AddDropoffNodeState() {
	}

	@Override
	public void init(Window window, Controller controller) {
		window.updateDeliveryDetail(controller.getTempDelivery());
		window.disableButtons(true, true, true, false, true, true, true, false);
		window.setDurationEdit(false, true);
		window.updateMessage("Select dropoff location and duration.");
	}

	@Override
	public void placeNode(Window window, Controller controller, String nodeId, Bounds bounds) {
		SpecialNode newDropoff = new SpecialNode(controller.getCurrentMap().getNodeMap().get(nodeId),
				SpecialNodeType.DROPOFF, 0d, null, controller.getTempDelivery());
		controller.getTempDelivery().setDeliveryNode(newDropoff);
		window.drawTempMarker(newDropoff, controller.getTempDelivery().getDeliveryIndex());
	}

	@Override
	public void confirmButtonClick(Window window, Controller controller) {
		try {
			controller.getTempDelivery().getDeliveryNode().setDuration(window.getDropoffDuration());
			window.updateDeliveryDetail(controller.getTempDelivery());
			controller.setCurrentState(controller.ADD_TO_ROUND_STATE);
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
