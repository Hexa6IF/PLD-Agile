package controller;

import java.util.List;

import model.Delivery;

public class CmdModifyDelivery implements Command {
    
    private List<Delivery> deliveries;
    
    private Delivery original;
    private Delivery modified;
    
    public CmdModifyDelivery(List<Delivery> deliveries, Delivery original, Delivery modified) {
	this.deliveries = deliveries;
	this.original = original;
	this.modified = modified;
    }
    
    public void doCmd() {
	deliveries.remove(original);
	deliveries.add(modified);
    }
    
    public void undoCmd() {
	deliveries.remove(modified);
	deliveries.add(original);
    }
}