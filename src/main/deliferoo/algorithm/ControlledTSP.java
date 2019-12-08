/**
 * 
 */
package algorithm;

import controller.TSPCallback;

/**
 * @author sadsitha
 *
 */
public interface ControlledTSP {
    /**
     * Register the callback class
     * 
     * @param tspCallback
     */
    public void registerCallBack(TSPCallback tspCallback);
    
    /**
     * Stop the ongoing TSP calculation
     */
    public void stopCalculation();
}
