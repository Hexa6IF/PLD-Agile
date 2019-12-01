package controller;

import java.io.File;
import java.util.List;

import model.BestPath;
import model.Delivery;
import model.FullMap;
import view.Window;

/**
 * Class that implements the MVC application controller
 * 
 * @author sadsitha
 *
 */
public class Controller {

    private Window window;
    private State currentState;
    private CommandList commandList;
    private FullMap currentMap;
    private List <BestPath> round;
    private List<Delivery> deliveries;
    protected final InitState initState = new InitState();
    protected final AddDeliveryState addDeliveryState = new AddDeliveryState();
    protected final DeliverySelectedState deliverySelectedState = new DeliverySelectedState();
    protected final MapLoadedState mapLoadedState = new MapLoadedState();
    protected final DeliveriesLoadedState deliveriesLoadedState = new DeliveriesLoadedState();
    protected final ModifyDeliveryState modifyDeliveryState = new ModifyDeliveryState();

    /**
     * Creates the controller of the application
     */
    public Controller() {
	this.window = new Window(this);
	this.window.launchWindow();
	this.currentState = this.initState;
    }

    /**
     * Set the controller's current state
     * 
     * @param state the new state
     */
    protected void setCurrentState(State state) {
	this.currentState = state;
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
     * Set the controller's current map
     * 
     * @param map the new map
     */
    protected void setDeliveries(List<Delivery> deliveries) {
	this.deliveries = deliveries;
    }
    
    /**
     * Set the controller's calculated round
     * 
     * @param round the new round
     */
    protected void setRound(List<BestPath> round) {
	this.round = round;
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
     * Method called to calculate the round
     * 
     */
    public void calculateRound() {
	this.currentState.calculateRound(this.window, this, this.deliveries, this.currentMap);
    }

}
