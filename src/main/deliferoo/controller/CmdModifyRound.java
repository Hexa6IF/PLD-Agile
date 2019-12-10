package controller;

import java.util.ArrayList;
import java.util.List;

import model.Cyclist;
import model.SpecialNode;

public class CmdModifyRound implements Command {
    
    private List<SpecialNode> round;
    private List<Integer> order;
    private Cyclist cyclist;
    
    public CmdModifyRound(List<SpecialNode> round, List<SpecialNode> newRound, Cyclist cyclist) {
	this.round = round;
	this.order = new ArrayList<Integer>();
	this.cyclist = cyclist;
	
	for(int i = 0; i < round.size(); i++) {
	    for(int j = 0; i < newRound.size(); j++) {
		if(round.get(i) == newRound.get(j)) {
		    order.add(i, j);
		    break;
		}
	    }
	}
    }
    
    public void doCmd() {
	List<SpecialNode> newRound = new ArrayList<>(this.round);
	for(int i = 0; i < this.order.size(); i++) {
	    Integer oldIndex = i;
	    Integer newIndex = this.order.get(i);
	    this.round.set(newIndex, newRound.get(oldIndex));
	}
	CalculationHelper.updatePrecedences(this.round);
	CalculationHelper.updatePassageTimesSpecialNodes(this.round, cyclist);
    }
    
    public void undoCmd() {
	List<SpecialNode> oldRound = new ArrayList<>(this.round);
	for(int i = 0; i < this.order.size(); i++) {
	    Integer oldIndex = i;
	    Integer newIndex = this.order.get(i);
	    this.round.set(oldIndex, oldRound.get(newIndex));
	}
	CalculationHelper.updatePrecedences(this.round);
	CalculationHelper.updatePassageTimesSpecialNodes(this.round, cyclist);
    }
}
