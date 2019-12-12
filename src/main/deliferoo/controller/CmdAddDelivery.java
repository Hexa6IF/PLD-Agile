package controller;

import java.util.List;

import model.Delivery;
import model.SpecialNode;

public class CmdAddDelivery implements Command {
    Controller controller;
    private List<SpecialNode> round;
    private List<Delivery> deliveries;
    private Delivery toAdd;
    private Integer order;
    
    public CmdAddDelivery(Controller controller, List<SpecialNode> round, List<Delivery> deliveries, Integer order, Delivery toAdd) {
	this.controller = controller;
	this.round = round;
	this.deliveries = deliveries;
	this.toAdd = toAdd;
	this.order = order;
    }
    
    public void doCmd() {
	this.deliveries.add(toAdd);
	this.round.add(order, toAdd.getPickupNode());
	this.round.add(order + 1, toAdd.getDeliveryNode());
	controller.setCurrentState(controller.DELIVERY_SELECTED_STATE);
    }
    
    public void undoCmd() {
	this.deliveries.remove(toAdd);
	this.round.remove(toAdd.getPickupNode());
	this.round.remove(toAdd.getDeliveryNode());
	controller.setCurrentState(controller.MAP_LOADED_STATE);
    }
}
