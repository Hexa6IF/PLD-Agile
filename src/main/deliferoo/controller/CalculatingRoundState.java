package controller;

import java.util.List;
import java.util.Map;
import javafx.application.Platform;

import algorithm.Dijkstra;
import algorithm.TSPHeuristic;
import model.BestPath;
import model.SpecialNode;
import view.Window;

public class CalculatingRoundState implements State {

    public CalculatingRoundState() {
    }

    @Override
    public void init(Window window, Controller controller) {
	window.disableButtons(true, true, true, true, true, true, true, false);
	window.updateMessage("Calculating optimal round...");
	this.calculateRound(window, controller);
    }

    @Override
    public void calculateRound(Window window, Controller controller) {
	Runnable runnableTask = () -> {
	    Map<String, Map<String, BestPath>> bestPaths = Dijkstra.calculateAllShortestPaths(controller.getCyclist().getDeliveries(), controller.getCurrentMap());
	    controller.getCyclist().setBestPaths(bestPaths);    
	    //controller.tspSolver = new TSPSimple();
	    controller.tspSolver = new TSPHeuristic();
	    controller.tspSolver.registerCallBack(controller);
	    controller.tspSolver.searchSolution(60000, bestPaths, controller.getCyclist().getDeliveries());
	};
	controller.executor.execute(runnableTask);
    }

    @Override
    public void updateRound(Window window, Controller controller) {
	List<SpecialNode> round = controller.tspSolver.getTransformedSolution();
	controller.getCyclist().setRound(round);
	Platform.runLater(() -> {
	    try {
		controller.getCyclist().setRound(round);
		window.updateRound(round, controller.getCyclist().getBestPaths());
	    } catch (Exception ex) {
		ex.printStackTrace();
	    }
	});
    }
    
    @Override
    public void cancelButtonClick(Window window, Controller controller) {
	this.stopTSPCalculation(window, controller);
    }

    @Override
    public void stopTSPCalculation(Window window, Controller controller) {
	controller.tspSolver.stopCalculation();
	Platform.runLater(() -> {
	    try {		
		window.confirmRound();
	    } catch (Exception ex) {
		ex.printStackTrace();
	    }
	});
	controller.setCurrentState(controller.ROUND_CALCULATED_STATE);
    }
}
