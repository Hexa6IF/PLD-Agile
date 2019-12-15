package controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import algorithm.Dijkstra;
import model.BestPath;
import model.Delivery;
import model.SpecialNode;
import model.SpecialNodeType;
import view.Window;

public class AddToRoundState implements State {
	/**
	 * State class constructor
	 */
	public AddToRoundState() {
	}

	@Override
	public void init(Window window, Controller controller) {
		window.disableButtons(true, true, true, true, true, true, true, false);
		window.updateMessage("Select node after which the pickup will be done.");
		window.setDisableTableBoxView(true);
	}

	@Override
	public void selectDeliveryClick(Window window, Controller controller, Integer deliveryIndex, SpecialNodeType type) {

		window.setDisableTableBoxView(false);
		Delivery td = controller.getTempDelivery();
		List<SpecialNode> tr = new ArrayList<>(controller.getCyclist().getRound());

		for (int i = 0; i < tr.size(); i++) {
			if (tr.get(i).getDelivery().getDeliveryIndex() == deliveryIndex && tr.get(i).getSpecialNodeType() == type) {
				Map<String, Map<String, BestPath>> bp = controller.getCyclist().getBestPaths();

				tr.add(controller.getTempDelivery().getPickupNode());
				tr.add(controller.getTempDelivery().getDeliveryNode());

				Map<String, BestPath> fromTempPickup = Dijkstra.calculateShortestPaths(td.getPickupNode(),
						new HashSet<SpecialNode>(tr), controller.getCurrentMap());
				Map<String, BestPath> fromTempDropoff = Dijkstra.calculateShortestPaths(td.getDeliveryNode(),
						new HashSet<SpecialNode>(tr), controller.getCurrentMap());

				bp.put(td.getPickupNode().getNode().getNodeId(), fromTempPickup);
				bp.put(td.getDeliveryNode().getNode().getNodeId(), fromTempDropoff);
				for (String id : bp.keySet()) {
					bp.get(id).put(td.getPickupNode().getNode().getNodeId(), fromTempPickup.get(id));
					bp.get(id).put(td.getDeliveryNode().getNode().getNodeId(), fromTempDropoff.get(id));
				}

				controller.doCommand(new CmdAddDelivery(controller.getCyclist().getRound(),
						controller.getCyclist().getDeliveries(), i + 1, controller.getTempDelivery()));
				controller.setSelectedDelivery(controller.getCyclist().getDeliveries()
						.get(controller.getCyclist().getDeliveries().size() - 1));
				controller.setCurrentState(controller.DELIVERY_SELECTED_STATE);
				break;
			}
			;
		}
	}

	@Override
	public void cancelButtonClick(Window window, Controller controller) {
		window.setDisableTableBoxView(false);
		window.updateSelectedDelivery(controller.getCyclist().getDeliveries().get(0));
		window.setDurationEdit(false, false);
		window.clearTempMarkers();
		controller.setSelectedDelivery(controller.getCyclist().getDeliveries().get(0));
		controller.setCurrentState(controller.DELIVERY_SELECTED_STATE);
	}
}
