package controller;

import java.util.List;
import java.util.Map;

import algorithm.Dijkstra;
import algorithm.TSPDeliferoo;
import model.BestPath;
import model.Delivery;
import model.FullMap;
import model.Round;
import view.Window;

public class CalculatingRoundState implements State {
    
    public CalculatingRoundState() {}
    
    @Override
    public void init(Window window, Controller controller) {
	window.disableButtons(true, true, true, true, true, false);
	window.updateMessage("Calculating optimal round...");
	this.calculateRound(window, controller, controller.cyclist.getDeliveries(), controller.currentMap);
    }
    
    @Override
    public void calculateRound(Window window, Controller controller, List<Delivery> deliveries, FullMap map) {
	Map<String, Map<String, BestPath>> bestPaths = Dijkstra.calculateAllShortestPaths(deliveries, map);
	TSPDeliferoo tsp = new TSPDeliferoo();
	tsp.searchSolution(4000, bestPaths, deliveries);
	Round round = new Round(tsp.getBestPathSolution());
	window.updateRound(round);
	controller.setRound(round);
	controller.setCurrentState(controller.ROUND_CALCULATED_STATE);	
    }
}
