package controller;

/**
 * Interface to be followed by all classes implemeting a Command
 * 
 * @author sadsitha
 *
 */
public interface Command {

    /**
     * Execute the current command
     */
    void doCde();

    /**
     * Execute the inverse of the current command
     */
    void undoCde();
}
