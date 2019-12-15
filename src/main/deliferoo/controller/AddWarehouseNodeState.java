package controller;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import javafx.geometry.Bounds;
import model.BestPath;
import model.Delivery;
import model.SpecialNode;
import model.SpecialNodeType;
import view.Window;

public class AddWarehouseNodeState implements State {
	/**
	 * State class constructor
	 */
	public AddWarehouseNodeState() {
	}

	@Override
	public void init(Window window, Controller controller) {
		Delivery td = new Delivery();
		td.setDeliveryIndex(controller.getCyclist().getDeliveries().size());

		controller.setTempDelivery(td);
		window.disableButtons(true, true, true, false, true, true, true, false);
		window.updateMessage("Select warehouse location.");
	}

	@Override
	public void placeNode(Window window, Controller controller, String nodeId, Bounds bounds) {
		SpecialNode newStart = new SpecialNode(controller.getCurrentMap().getNodeMap().get(nodeId),
				SpecialNodeType.START, 0d, null, controller.getTempDelivery());
		SpecialNode newEnd = new SpecialNode(controller.getCurrentMap().getNodeMap().get(nodeId),
				SpecialNodeType.FINISH, 0d, null, controller.getTempDelivery());

		DateTimeFormatter startTimeFormatter = DateTimeFormatter.ofPattern("H:m:s");
		newStart.setPassageTime(LocalTime.parse("8:0:0", startTimeFormatter));
		newEnd.setPassageTime(LocalTime.parse("8:0:0", startTimeFormatter));

		controller.getTempDelivery().setPickupNode(newStart);
		controller.getTempDelivery().setDeliveryNode(newEnd);
		window.drawTempMarker(newStart, controller.getTempDelivery().getDeliveryIndex());
	}

	@Override
	public void confirmButtonClick(Window window, Controller controller) {
		controller.getCyclist().getBestPaths().put(controller.getTempDelivery().getPickupNode().getNode().getNodeId(),
				new HashMap<String, BestPath>());
		controller.setSelectedDelivery(controller.getTempDelivery());
		controller.doCommand(new CmdAddDelivery(controller.getCyclist().getRound(),
				controller.getCyclist().getDeliveries(), 0, controller.getTempDelivery()));
		window.updateDeliveryDetail(controller.getTempDelivery());
		window.drawMarkers(controller.getCyclist().getDeliveries(), 20);
		controller.setCurrentState(controller.DELIVERY_SELECTED_STATE);
	}

	@Override
	public void cancelButtonClick(Window window, Controller controller) {
		window.clearTempMarkers();
		controller.setCurrentState(controller.MAP_LOADED_STATE);
	}
}
