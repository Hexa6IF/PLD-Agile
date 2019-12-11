package controller;

import java.util.List;

import model.Delivery;
import model.SpecialNode;

public class CmdRemoveDelivery implements Command{
    private List<Delivery> deliveries;
    private List<SpecialNode> round;
    private Delivery toRemove;
    
    public CmdRemoveDelivery(List<Delivery> deliveries, Delivery toRemove) {
	this.deliveries = deliveries;
	this.toRemove = toRemove;
    }
    
    public void doCmd() {
	this.deliveries.set(toRemove.getDeliveryIndex(), null);
    }
    
    public void undoCmd() {
	this.deliveries.set(toRemove.getDeliveryIndex(), toRemove);
    }
}
