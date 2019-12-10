package controller;

/**
 * Interface for Controller to expose callback methods for calculating TSP
 * 
 * @author sadsitha
 *
 */
public interface TSPCallback {
    /**
     * Method called by TSP solver to indicate that a new best solution has been found
     */
    default void bestSolutionUpdated() {
    }

    /**
     * Method called by TSP solver to indicate that it has explored all solutions
     */
    default void calculationsCompleted() {
    }
}