/**
 * 
 */
package controller;

import java.util.LinkedList;

/**
 * Class containing the list of commands executed on the application instance
 * 
 * This class was copied from PlaCo and adapted to Del'IFeroo
 * @author sadsitha
 * @author Christine Solnon
 *
 */
public class CommandList {
    private LinkedList<Command> commandList;
    private Integer currentIndex;

    /**
     * Constructor for CommandList
     */
    public CommandList() {
	currentIndex = -1;
	commandList = new LinkedList<Command>();
    }

    /**
     * Adding command c to the commandList
     * 
     * @param c the command to add
     */
    public void addCmd(Command c) {
	int i = this.currentIndex + 1;
	while (i < this.commandList.size()) {
	    this.commandList.remove(i);
	}
	this.currentIndex++;
	this.commandList.add(this.currentIndex, c);
	c.doCmd();
    }

    /**
     * Temporarily cancel the last command added
     */
    public void undo() {
	if (this.currentIndex >= 0) {
	    Command cde = this.commandList.get(this.currentIndex);
	    this.currentIndex--;
	    cde.undoCmd();
	}
    }

    /**
     * Definitely delete the last command added
     */
    public void cancel() {
	if (this.currentIndex >= 0) {
	    Command cde = this.commandList.get(this.currentIndex);
	    this.commandList.remove(this.currentIndex);
	    this.currentIndex--;
	    cde.undoCmd();
	}
    }

    /**
     * Reinsert the last cancelled commande with undo
     */
    public void redo() {
	if (this.currentIndex < this.commandList.size() - 1) {
	    this.currentIndex++;
	    Command cde = this.commandList.get(this.currentIndex);
	    cde.doCmd();
	}
    }

    /**
     * Definitively delete all added commands
     */
    public void reset() {
	this.currentIndex = -1;
	this.commandList.clear();
    }

}
