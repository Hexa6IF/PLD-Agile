package controller;

import java.io.File;
import java.util.List;

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
public class Controller {

    private Window window;
    private State currentState;
    protected CommandList commandList;
    protected FullMap currentMap;
    protected Cyclist cyclist;
    protected final InitState INIT_STATE = new InitState();
    protected final AddDeliveryState ADD_DELIVERY_STATE = new AddDeliveryState();
    protected final DeliverySelectedState DELIVERY_SELECTED_STATE = new DeliverySelectedState();
    protected final MapLoadedState MAP_LOADED_STATE = new MapLoadedState();
    protected final DeliveriesLoadedState DELIVERIES_LOADED_STATE = new DeliveriesLoadedState();
    protected final ModifyDeliveryState MODIFY_DELIVERY_STATE = new ModifyDeliveryState();

    /**
     * Creates the controller of the application
     */
    public Controller() {
	this.window = new Window(this);
	this.window.launchWindow();
	this.cyclist = new Cyclist();
	this.currentState = this.INIT_STATE;
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
     * Method called to calculate the round
     * 
     */
    public void calculateRound() {
	this.currentState.calculateRound(this.window, this, this.cyclist.getDeliveries(), this.currentMap);
    }

    /**
     *  Method called when a delivery is selected
     *  
     * @param deliveryIndex
     */
    public void selectDeliveryClick(Integer deliveryIndex) {
	this.currentState.selectDeliveryClick(this.window, this, deliveryIndex);
    }
}
