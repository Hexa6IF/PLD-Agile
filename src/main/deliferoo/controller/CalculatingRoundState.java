package controller;

import java.util.List;
import java.util.Map;
import javafx.application.Platform;

import algorithm.Dijkstra;
import algorithm.TSPHeuristic;
import model.BestPath;
import model.Delivery;
import model.FullMap;
import model.SpecialNode;
import view.Window;

public class CalculatingRoundState implements State {

    public CalculatingRoundState() {
    }

    @Override
    public void init(Window window, Controller controller) {
	window.disableButtons(true, true, true, true, true, true, true, false);
	window.updateMessage("Calculating optimal round...");
	this.calculateRound(window, controller, controller.getCyclist().getDeliveries(), controller.getCurrentMap());
    }

    @Override
    public void calculateRound(Window window, Controller controller, List<Delivery> deliveries, FullMap map) {
	Runnable runnableTask = () -> {
	    Map<String, Map<String, BestPath>> bestPaths = Dijkstra.calculateAllShortestPaths(deliveries, map);
	    controller.getCyclist().setShortestPaths(bestPaths);    
	    //controller.tspSolver = new TSPSimple();
	    controller.tspSolver = new TSPHeuristic();
	    controller.tspSolver.registerCallBack(controller);
	    controller.tspSolver.searchSolution(60000, bestPaths, deliveries);
	};
	controller.executor.execute(runnableTask);	
    }

    @Override
    public void updateRound(Window window, Controller controller) {
	List<SpecialNode> round = controller.tspSolver.getTransformedSolution();
	controller.getCyclist().setRound(round);
	Platform.runLater(() -> {
	    try {		
		window.updateRound(round, controller.getCyclist().getBestPaths());
		controller.getCyclist().setShortestPaths(controller.getCyclist().getBestPaths());
		controller.getCyclist().setRound(round);
	    } catch (Exception ex) {
		ex.printStackTrace();
	    }
	});
    }
    
    @Override
    public void stopTSPCalculation(Window window, Controller controller) {
	controller.tspSolver.stopCalculation();
	Platform.runLater(() -> {
	    try {		
		window.confirmRound();;
	    } catch (Exception ex) {
		ex.printStackTrace();
	    }
	});
	controller.setCurrentState(controller.ROUND_CALCULATED_STATE);
    }
}
