package controller;

import java.util.List;

import model.Delivery;
import model.SpecialNode;

public class CmdRemoveDelivery implements Command {
	private List<Delivery> deliveries;
	private List<SpecialNode> round;
	private Delivery toRemove;
	private Integer pickupIndex;
	private Integer dropoffIndex;

	public CmdRemoveDelivery(List<Delivery> deliveries, List<SpecialNode> round, Delivery toRemove) {
		this.deliveries = deliveries;
		this.round = round;
		this.toRemove = toRemove;
		this.pickupIndex = round.indexOf(toRemove.getPickupNode());
		this.dropoffIndex = round.indexOf(toRemove.getDeliveryNode());
	}

	public void doCmd() {
		this.deliveries.set(toRemove.getDeliveryIndex(), null);
		this.round.remove(toRemove.getPickupNode());
		this.round.remove(toRemove.getDeliveryNode());
	}

	public void undoCmd() {
		this.deliveries.set(toRemove.getDeliveryIndex(), toRemove);
		this.round.add(pickupIndex, toRemove.getPickupNode());
		this.round.add(dropoffIndex, toRemove.getDeliveryNode());
	}
}
