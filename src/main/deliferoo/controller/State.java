package controller;

import java.io.File;
import java.util.List;

import model.Delivery;
import model.FullMap;
import view.Window;

/**
 * Interface to be followed by all classes implementing states of the
 * application
 * 
 * @author sadsitha
 *
 */
public interface State {

    /**
     * Method called when the "Undo" button is clicked
     * 
     * @param l the current command list
     */
    public default void undo(CommandList l) {
    }

    /**
     * Method called when the "Redo" button is clicked
     * 
     * @param l the current command list
     */
    public default void redo(CommandList l) {
    }

    /**
     * Method called when right mouse button is clicked
     * 
     * @param the window
     * @param the controller
     */
    public default void rightClick(Window window, Controller controller) {
	
    }

    /**
     * Method called when left mouse button is clicked
     * 
     * @param the window
     * @param the controller
     */
    public default void leftClick(Window window, Controller controller) {
    }

    /**
     * Method called when the "Add Delivery" button is clicked
     * 
     * @param the window
     * @param the controller
     */
    public default void addDeliveryClick(Window window, Controller controller) {
    }

    /**
     * Method called when the "Save Delivery" button is clicked
     * 
     * @param the window
     * @param the controller
     */
    public default void saveDeliveryClick(Window window, Controller controller) {
    }

    /**
     * Method called when the "Remove Delivery" button is clicked
     * 
     * @param the window
     * @param the controller
     */
    public default void removeDeliveryClick(Window window, Controller controller) {
    }

    /**
     * Method called when the "Cencel" button is clicked
     * 
     * @param the window
     * @param the controller
     */
    public default void cancelModificationClick(Window window, Controller controller) {
    }

    /**
     * Method called when the "Modify Delivery" button is clicked
     * 
     * @param the window
     * @param the controller
     */
    public default void modifyDeliveryClick(Window window, Controller controller) {
    }

    /**
     * Method called when a Delivery is clicked on (either on the map or on the text
     * view)
     * 
     * @param the window
     * @param the controller
     */
    public default void selectDeliveryClick(Window window, Controller controller) {
    }

    /**
     * Method called when a new deliveries file is loaded
     * 
     * @param the window
     * @param the controller
     * @param the deliveries file
     * @param the map
     */
    public default void loadDeliveries(Window window, Controller controller, File deliveriesFile, FullMap map) {
    }

    /**
     * Method called when a new map file is loaded
     * 
     * @param the window
     * @param the controller
     * @param the map file
     */
    public default void loadMap(Window window, Controller controller, File mapFile) {
    }

    /**
     * Method called when a round needs to be calculated
     * 
     * @param the window
     * @param the controller
     * @param the deliveries
     * @param the map
     */
    public default void calculateRound(Window window, Controller controller, List<Delivery> deliveries, FullMap map) {
    }
}
