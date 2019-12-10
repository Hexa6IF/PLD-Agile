package controller;

import java.util.List;

import model.Delivery;
import model.SpecialNode;

public class CmdAddDelivery implements Command {    
    private List<SpecialNode> round;
    private List<Delivery> deliveries;
    
    public void doCmd() {
	
    }
    
    public void undoCmd() {
	
    }
}
