package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import algorithm.Dijkstra;
import algorithm.TSP1;
import model.BestPath;
import model.Delivery;
import model.FullMap;
import model.Round;
import view.Window;
import xml.XMLParser;

/**
 * 
 * @author sadsitha
 *
 */
public class DeliveriesLoadedState implements State {
    // State where deliveries have been loaded

    /**
     * State class constructor
     */
    public DeliveriesLoadedState() {

    }

    @Override
    public void loadMap(Window window, Controller controller, File mapFile) {
	FullMap map = XMLParser.getInstance().parseMap(mapFile);
	controller.setCurrentMap(map);
	window.updateMap(map);
	window.updateDeliveries(new ArrayList<Delivery>());
	controller.setCurrentState(controller.MAP_LOADED_STATE);
    }

    @Override
    public void loadDeliveries(Window window, Controller controller, File deliveriesFile, FullMap map) {
	List<Delivery> deliveries = XMLParser.getInstance().parseDeliveries(deliveriesFile, map);
	controller.setDeliveries(deliveries);
	window.updateDeliveries(deliveries);
    }

    @Override
    public void calculateRound(Window window, Controller controller, List<Delivery> deliveries, FullMap map) {
	Map<String, Map<String, BestPath>> bestPaths = Dijkstra.calculateAllShortestPaths(deliveries, map);
	TSP1 tsp = new TSP1();
	tsp.searchSolution(4000, bestPaths);
	List<BestPath> roundPaths = tsp.getBestPathSolution();
	Round round = new Round(roundPaths);
	controller.setRound(round);
	window.updateRound(round);
	window.updateMessage("New round calculated");
    }
    
    @Override
    public void selectDeliveryClick(Window window, Controller controller, Integer deliveryIndex) {
	for(Delivery delivery : controller.cyclist.getDeliveries()) {
	    if(delivery.getDeliveryIndex() == deliveryIndex) {
		window.updateSelectedDelivery(delivery);
		break;
	    }
	}
	controller.setCurrentState(controller.DELIVERY_SELECTED_STATE);
    }
}
