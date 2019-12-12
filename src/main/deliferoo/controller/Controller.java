package controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import algorithm.TSP;
import javafx.geometry.Bounds;
import model.BestPath;
import model.Cyclist;
import model.Delivery;
import model.FullMap;
import model.SpecialNode;
import model.SpecialNodeType;
import view.SpecialNodeTextView;
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
    
    private CommandList commandList;
    private FullMap currentMap;
    private Cyclist cyclist;
    
    private Delivery selectedDelivery;
    
    private Map<String, Map<String, BestPath>> tempBestPaths;
    private Delivery tempDelivery;
    private List<SpecialNode> tempRound;

    protected TSP tspSolver;
    protected ExecutorService executor;
    
    protected final InitState INIT_STATE = new InitState();
    protected final AddWarehouseNodeState ADD_WAREHOUSE_NODE_STATE = new AddWarehouseNodeState();
    protected final AddPickupNodeState ADD_PICKUP_NODE_STATE = new AddPickupNodeState();
    protected final AddDropoffNodeState ADD_DROPOFF_NODE_STATE = new AddDropoffNodeState();
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
     * Get the controller's current map
     * @return current map
     */
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
    
    /**
     * Get the controller's cyclist
     * @return cyclist
     */
    protected Cyclist getCyclist() { 
	return this.cyclist;
    }
    
    /**
     * Get the current selected delivery
     * @return
     */
    protected Delivery getSelectedDelivery() {
	return this.selectedDelivery;
    }
    
    /**
     * Set the current selected delivery
     * @param selectedDelivery
     */
    protected void setSelectedDelivery(Delivery selectedDelivery) {
	this.selectedDelivery = selectedDelivery;
    }
    
    protected Map<String, Map<String, BestPath>> getTempBestPaths() {
	return this.tempBestPaths;
    }
    
    protected void setTempBestPaths(Map<String, Map<String, BestPath>> tempBestPaths) {
	this.tempBestPaths = tempBestPaths;
    }
    
    protected Delivery getTempDelivery() {
	return this.tempDelivery;
    }
    
    protected void setTempDelivery(Delivery tmpDelivery) {
	this.tempDelivery = tmpDelivery;
    }
    
    protected List<SpecialNode> getTempRound() {
	return this.tempRound;
    }
    
    protected void setTempRound(List<SpecialNode> tempRound) {
	this.tempRound = tempRound;
    }
    
    protected boolean canUndo() {
	return this.commandList.getCurrentIndex() >= 0;
    }
    
    protected boolean canRedo() {
	return this.commandList.getCurrentIndex() < this.commandList.getLength() - 1;
    }
    
    protected void doCommand(Command cmd) {
	this.commandList.addCmd(cmd);
	CalculationHelper.updatePassageTimesSpecialNodes(this.cyclist.getRound(), this.getCyclist());
	this.window.clearTempMarkers();
	this.window.updateDeliveries(this.cyclist.getDeliveries());
	this.window.drawMarkers(this.cyclist.getDeliveries(), 20);
	this.window.updateRound(this.cyclist.getRound(), this.cyclist.getBestPaths());
    }

    /**
     * Method called when the "Undo" button is clicked
     */
    public void undo() {
	this.commandList.undo();
	this.currentState.init(this.window, this);
	CalculationHelper.updatePassageTimesSpecialNodes(this.cyclist.getRound(), this.getCyclist());
	this.window.drawMarkers(this.cyclist.getDeliveries(), 20);
	this.window.updateRound(this.cyclist.getRound(), this.cyclist.getBestPaths());
	this.selectedDelivery = cyclist.getDeliveries().get(selectedDelivery.getDeliveryIndex());
	
	if(selectedDelivery != null) {
	    System.out.println(selectedDelivery);
	    this.window.updateDeliveryDetail(selectedDelivery);
	}
    }

    /**
     * Method called when the "Redo" button is clicked
     */
    public void redo() {
	this.commandList.redo();
	this.currentState.init(this.window, this);
	CalculationHelper.updatePassageTimesSpecialNodes(this.cyclist.getRound(), this.getCyclist());
	this.window.drawMarkers(this.cyclist.getDeliveries(), 20);
	this.window.updateRound(this.cyclist.getRound(), this.cyclist.getBestPaths());
	this.selectedDelivery = cyclist.getDeliveries().get(selectedDelivery.getDeliveryIndex());
	
	if(selectedDelivery != null) {
	    this.window.updateDeliveryDetail(selectedDelivery);
	}
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
    
    public void changeNodePosition(Integer deliveryId, SpecialNodeType type, String newNodeId) {
	this.currentState.changeNodePosition(this.window, this, deliveryId, type,  newNodeId);
    }
    
    public void changeRoundOrder(List<SpecialNodeTextView> newOrder) {
	this.currentState.changeRoundOrder(this.window, this, newOrder);
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
    
    public void placeNode(String nodeId, Bounds bounds) {
	this.currentState.placeNode(this.window, this, nodeId, bounds);
    }
}
