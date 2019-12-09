package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import algorithm.Dijkstra;
import algorithm.TSPDeliferoo;
import model.BestPath;
import model.Delivery;
import model.FullMap;
import model.Round;
import model.SpecialNode;
import view.Window;

public class CalculatingRoundState implements State {
    
    public CalculatingRoundState() {}
    
    @Override
    public void init(Window window, Controller controller) {
	window.disableButtons(true, true, true, true, true, true, true, false);
	window.updateMessage("Calculating optimal round...");
	this.calculateRound(window, controller, controller.getCyclist().getDeliveries(), controller.getCurrentMap());
    }
    
    @Override
    public void calculateRound(Window window, Controller controller, List<Delivery> deliveries, FullMap map) {
	Map<String, Map<String, BestPath>> bestPaths = Dijkstra.calculateAllShortestPaths(deliveries, map);
	TSPDeliferoo tsp = new TSPDeliferoo();
	tsp.searchSolution(4000, bestPaths, deliveries);
	Round r = new Round(tsp.getBestPathSolution());
	List<SpecialNode> round = new ArrayList<>();
	
	for(int i = 0; i<r.getResultPath().size(); i++) {
	    if(i == 0) {
		round.add(r.getResultPath().get(i).getStart());
	    }
	    round.add(r.getResultPath().get(i).getEnd());
	}
	
	window.updateRound(round, bestPaths);
	
	controller.getCyclist().setShortestPaths(bestPaths);
	controller.getCyclist().setRound(round);
	controller.setCurrentState(controller.ROUND_CALCULATED_STATE);	
    }
}
