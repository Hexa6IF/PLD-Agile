package controller;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import algorithm.TSP;
import model.Cyclist;
import model.Delivery;
import model.FullMap;
import model.Round;
import view.Window;

/**
 * Class that implements the MVC application controller
 * 
 * @author sadsitha
 *
 */
public class Controller implements TSPCallback{

    private Window window;
    private State currentState;
    protected CommandList commandList;
    protected FullMap currentMap;
    protected Cyclist cyclist;
    protected Delivery currentSelectedDelivery;
    protected TSP tspSolver;
    protected ExecutorService executor;
    protected final InitState INIT_STATE = new InitState();
    protected final AddDeliveryState ADD_DELIVERY_STATE = new AddDeliveryState();
    protected final DeliverySelectedState DELIVERY_SELECTED_STATE = new DeliverySelectedState();
    protected final MapLoadedState MAP_LOADED_STATE = new MapLoadedState();
    protected final RoundCalculatedState ROUND_CALCULATED_STATE = new RoundCalculatedState();
    protected final ModifyDeliveryState MODIFY_DELIVERY_STATE = new ModifyDeliveryState();
    protected final CalculatingRoundState CALCULATING_ROUND_STATE = new CalculatingRoundState();

    /**
     * Creates the controller of the application
     */
    public Controller() {
	this.window = new Window(this);
	this.window.launchWindow();
	this.cyclist = new Cyclist();
	this.executor = new ThreadPoolExecutor(1, 2, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
	this.setCurrentState(this.INIT_STATE);
    }

    /**
     * Set the controller's current state
     * 
     * @param state the new state
     */
    protected void setCurrentState(State state) {
	this.currentState = state;
	this.currentState.init(this.window, this);
    }
    
    /**
     * Set the controller's current map
     * 
     * @param map the new map
     */
    protected void setCurrentMap(FullMap map) {
	this.currentMap = map;
    }
    
    /**
     * Set the current list of deliveries
     * 
     * @param deliveries the list of deliveries
     */
    protected void setDeliveries(List<Delivery> deliveries) {
	this.cyclist.setDeliveries(deliveries);
    }
    
    /**
     * Set the controller's calculated round
     * 
     * @param bestPaths the new round
     */
    protected void setRound(Round round) {
	this.cyclist.setRound(round);
    }

    /**
     * Method called when the "Undo" button is clicked
     */
    public void undo() {
    }

    /**
     * Method called when the "Redo" button is clicked
     */
    public void redo() {
    }

    /**
     * Method called when a new deliveries file is loaded
     * 
     * @param deliveryFile
     */
    public void loadDeliveries(File deliveryFile) {
	this.currentState.loadDeliveries(this.window, this, deliveryFile, this.currentMap);
    }

    /**
     * Method called when a new map file is loaded
     * 
     * @param mapFile
     */
    public void loadMap(File mapFile) {
	this.currentState.loadMap(this.window, this, mapFile);
    }

    /**
     *  Method called when a delivery is selected
     *  
     * @param deliveryIndex
     */
    public void selectDeliveryClick(Integer deliveryIndex) {
	this.currentState.selectDeliveryClick(this.window, this, deliveryIndex);
    }
    
    /**
     * Method called by TSP solver to indicate that a new best solution has been
     * found
     */
    @Override
    public void bestSolutionUpdated() {
	this.currentState.updateRound(this.window, this);
    }
    
    /**
     * Method called by TSP solver to indicate that it has explored all solutions
     */
    @Override
    public void calculationsCompleted() {
	this.currentState.stopTSPCalculation(this.window, this);
    }
}
