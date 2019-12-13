package controller;

import java.util.List;

import model.Delivery;
import model.SpecialNode;

public class CmdAddDelivery implements Command {
    private List<SpecialNode> round;
    private List<Delivery> deliveries;
    private Delivery toAdd;
    private Integer order;
    
    public CmdAddDelivery(List<SpecialNode> round, List<Delivery> deliveries, Integer order, Delivery toAdd) {
	this.round = round;
	this.deliveries = deliveries;
	this.toAdd = toAdd;
	this.order = order;
    }
    
    public void doCmd() {
	this.deliveries.add(toAdd);
	this.round.add(order, toAdd.getPickupNode());
	this.round.add(order + 1, toAdd.getDeliveryNode());
    }
    
    public void undoCmd() {
	this.deliveries.remove(toAdd);
	this.round.remove(toAdd.getPickupNode());
	this.round.remove(toAdd.getDeliveryNode());
    }
}
