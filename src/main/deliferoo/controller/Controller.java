package controller;

import java.io.File;

import model.Cyclist;
import model.Delivery;
import model.FullMap;
import model.SpecialNode;
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
    private Cyclist cyclist;
    private Delivery selectedDelivery;
    private Delivery tmpDelivery;
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
	this.commandList = new CommandList();
	this.cyclist = new Cyclist();
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
    
    protected FullMap getCurrentMap() {
	return this.currentMap;
    }
    
    /**
     * Set the controller's current map
     * 
     * @param map the new map
     */
    protected void setCurrentMap(FullMap map) {
	this.currentMap = map;
    }
    
    protected Cyclist getCyclist() { 
	return this.cyclist;
    }
    
    protected Delivery getSelectedDelivery() {
	return this.selectedDelivery;
    }
    
    protected void setSelectedDelivery(Delivery selectedDelivery) {
	this.selectedDelivery = selectedDelivery;
    }
    
    protected Delivery getTmpDelivery() {
	return this.tmpDelivery;
    }
    
    protected void setTmpDelivery(Delivery tmpDelivery) {
	this.tmpDelivery = tmpDelivery;
    }
    
    protected void doCommand(Command cmd) {
	this.commandList.addCmd(cmd);
    }

    /**
     * Method called when the "Undo" button is clicked
     */
    public void undo() {}

    /**
     * Method called when the "Redo" button is clicked
     */
    public void redo() {}

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
     * Method called when the "Modify Delivery" button is clicked
     * 
     * @param the window
     * @param the controller
     */
    public void modifyButtonClick() {
	this.currentState.modifyButtonClick(this.window, this);
    }

    /**
     * Method called when the "Add Delivery" button is clicked
     * 
     * @param the window
     * @param the controller
     */
    public void addButtonClick() {
	this.currentState.addButtonClick(this.window, this);
    }
    
    /**
     * Method called when the "Modify Delivery" button is clicked
     * 
     * @param the window
     * @param the controller
     */
    public void removeButtonClick() {
	this.currentState.removeButtonClick(this.window, this);
    }
    
    /**
     * Method called when the "Modify Delivery" button is clicked
     * 
     * @param the window
     * @param the controller
     */
    public void confirmButtonClick() {
	this.currentState.confirmButtonClick(this.window, this);
    }
    
    /**
     * Method called when the "Modify Delivery" button is clicked
     * 
     * @param the window
     * @param the controller
     */
    public void calculateButtonClick() {
	this.currentState.calculateButtonClick(this.window, this);
    }

    /**
     * Method called when the "Cancel" button is clicked
     * 
     * @param the window
     * @param the controller
     */
    public void cancelButtonClick() {
	this.currentState.cancelButtonClick(this.window, this);
    }
    
    public void moveSpecialNode(SpecialNode node, String newNodeId) {
	this.currentState.moveSpecialNode(this.window, this, node, newNodeId);
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
