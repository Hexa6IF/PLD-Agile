package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import controller.TSPCallback;
import model.BestPath;
import model.Delivery;
import model.SpecialNode;
import model.SpecialNodeType;

/**
 * Class that implements the TSP resolution.
 * It is based TSPTemplate given on Moodle
 * 
 * @author Louis
 * @author sadsitha
 * 
 *
 */
public abstract class TSP {

    private List<SpecialNode> bestSolution;
    private int bestSolutionCost = 0;
    private Boolean timeLimitReached;
    private Map<String, Map<String, BestPath>> graph;
    private List<Delivery> deliveries;
    private Map<SpecialNode, Map<SpecialNode, Integer>> cost;
    private TSPCallback tspCallback;
    private Boolean calculationShouldContinue;
    private Number speed;

    /**
     * Constructor for TSP
     * 
     * @param speed
     */
    public TSP(Number speed) {
	this.calculationShouldContinue = true;
	this.speed = speed;
    }

    /**
     * This is the method to execute if we want to compute the best solution
     * 
     * @param timeLimit  the algorithm stops if the time limit in ms is reached
     * @param graph      computed by Dijkstra
     * @param deliveries list of deliveries to calculate for
     */
    public void searchSolution(int timeLimit, Map<String, Map<String, BestPath>> graph, List<Delivery> deliveries) {
	this.initClassVar(timeLimit, graph, deliveries);
	Long startTime = System.currentTimeMillis();
	this.cost = this.createCostFromGraph();
	List<SpecialNode> undiscovered = this.initUndiscovered();
	List<SpecialNode> discovered = new ArrayList<SpecialNode>();
	SpecialNode startNode = this.deliveries.get(0).getPickupNode();
	discovered.add(startNode);
	sortUndiscovered(startNode, undiscovered);
	branchAndBound(startNode, undiscovered, discovered, 0, this.cost, System.currentTimeMillis(), timeLimit);
	Long endTime = System.currentTimeMillis();
	System.out.println((endTime - startTime));
	if (this.tspCallback != null)
	    this.tspCallback.calculationsCompleted(); // indicate that calculation is complete
    }

    /**
     * Initiate the class' attributes
     * 
     * @param timeLimit
     * @param graph
     * @param deliveries
     */
    private void initClassVar(int timeLimit, Map<String, Map<String, BestPath>> graph, List<Delivery> deliveries) {
	this.graph = graph;
	this.deliveries = deliveries;
	this.timeLimitReached = false;
	this.bestSolutionCost = Integer.MAX_VALUE;
	int nbNodes = this.deliveries.size() * 2;
	this.bestSolution = new ArrayList<SpecialNode>(nbNodes);
    }

    /**
     * Insert all pickup nodes in an ArrayList At the beginning, delivery nodes are
     * not discoverable because we have to pick up before delivering
     * 
     * @return the list of undiscovered nodes
     */
    private List<SpecialNode> initUndiscovered() {
	List<SpecialNode> undiscovered = new ArrayList<SpecialNode>();
	for (Delivery delivery : this.deliveries) {
	    if (delivery.getPickupNode().getSpecialNodeType() != SpecialNodeType.START) {
		undiscovered.add(delivery.getPickupNode());
	    }
	}
	return undiscovered;
    }

    /**
     * Stores in a data structure the time in minutes needed to go from special node
     * X to special node Y
     * 
     * @return The map of costs to travel from one special node to another
     */
    private Map<SpecialNode, Map<SpecialNode, Integer>> createCostFromGraph() {
	Map<SpecialNode, Map<SpecialNode, Integer>> cost = new HashMap<SpecialNode, Map<SpecialNode, Integer>>();
	Set<SpecialNode> specialNodes = new HashSet<SpecialNode>();
	for (Delivery delivery : this.deliveries) {
	    specialNodes.add(delivery.getPickupNode());
	    specialNodes.add(delivery.getDeliveryNode());
	}
	for (SpecialNode specialNode1 : specialNodes) {
	    String specialNode1Id = specialNode1.getNode().getNodeId();
	    Map<SpecialNode, Integer> subCost = new HashMap<SpecialNode, Integer>();
	    for (SpecialNode specialNode2 : specialNodes) {
		String specialNode2Id = specialNode2.getNode().getNodeId();
		Integer individualCost = this.graph.get(specialNode1Id).get(specialNode2Id).getDistance().intValue()
			* this.speed.intValue();
		individualCost += specialNode2.getDuration().intValue();
		subCost.put(specialNode2, individualCost);
	    }
	    cost.put(specialNode1, subCost);
	}
	return cost;
    }

    /**
     * The algorithm's heuristic
     * 
     * @param currentNode  The node being visited currently
     * @param startNode    The node where the round started
     * @param undiscovered The list of undicovered nodes
     * @param cost         The map of costs to travel from one special node to
     *                     another
     * @return The lower bound of costs of the permutations starting at startNode,
     *         containing all summits in specialNodes exactly once and ending on the
     *         finish node
     */
    abstract int bound(SpecialNode currentNode, SpecialNode startNode, List<SpecialNode> undiscovered,
	    Map<SpecialNode, Map<SpecialNode, Integer>> cost);

    /**
     * Create an iterator for iterating over undiscovered specialNodes
     * 
     * @param currentNode	The node being visited currently
     * @param undiscovered	The list of undiscovered specialNodes
     * @return An iterator capable of iterating over the list of undiscovered specialNodes
     */
    private Iterator<SpecialNode> iterator(SpecialNode currentNode, List<SpecialNode> undiscovered) {
	return new IteratorSeq(undiscovered, currentNode);
    }
    
    /**
     * Method that implements the branch and bound algorithm for a TSP
     * 
     * @param currentNode	The last specialNode visited
     * @param undiscovered	The list of specialNodes that have not yet been visited
     * @param discovered	The list of specialNodes that have been visited (including currentNode)
     * @param discoveredCost	The sum of costs to travel through all the discovered specialNodes
     * @param cost		The map of costs to travel from one specialNode to another
     * @param startTime		The start time of the algorithm execution
     * @param timeLimit		The time limit for the resolution of the problem
     */
    private void branchAndBound(SpecialNode currentNode, List<SpecialNode> undiscovered, List<SpecialNode> discovered,
	    int discoveredCost, Map<SpecialNode, Map<SpecialNode, Integer>> cost, long startTime, int timeLimit) {

	SpecialNode finishNode = this.deliveries.get(0).getDeliveryNode();
	SpecialNode startNode = this.deliveries.get(0).getPickupNode();

	if (!this.calculationShouldContinue || (System.currentTimeMillis() - startTime > timeLimit)) {
	    timeLimitReached = true;
	} else if (undiscovered.size() == 0) { // tous les sommets ont ete visites
	    discoveredCost += cost.get(currentNode).get(finishNode);
	    if (discoveredCost < bestSolutionCost) { // on a trouve une solution meilleure que bestSolution
		this.bestSolution.clear();
		this.bestSolution.addAll(discovered);
		this.bestSolution.add(finishNode);
		bestSolutionCost = discoveredCost;
		if (this.tspCallback != null)
		    this.tspCallback.bestSolutionUpdated(); // indicate that a new best solution has been found
	    }
	} else if ((discoveredCost + this.bound(currentNode, startNode, undiscovered, cost)) < bestSolutionCost) {
	    sortUndiscovered(currentNode, undiscovered);
	    Iterator<SpecialNode> it = iterator(currentNode, undiscovered);
	    while (it.hasNext()) {
		SpecialNode nextNode = it.next();
		Boolean nextNodeIsPickup = nextNode.getSpecialNodeType() == SpecialNodeType.PICKUP;
		SpecialNode nextNodeDropOff = nextNode; // initializing with incorrect value
		discovered.add(nextNode);
		Long costCurrentToNext = cost.get(currentNode).get(nextNode).longValue();
		undiscovered.remove(nextNode);
		if (nextNodeIsPickup) {
		    nextNodeDropOff = nextNode.getDelivery().getDeliveryNode();
		    undiscovered.add(nextNodeDropOff);
		}
		branchAndBound(nextNode, undiscovered, discovered, discoveredCost + costCurrentToNext.intValue(), cost,
			startTime, timeLimit);
		discovered.remove(nextNode);
		undiscovered.add(nextNode);
		if (nextNodeIsPickup) {
		    undiscovered.remove(nextNodeDropOff);
		}
	    }
	}
    }

    /**
     * Sort undiscovered special node list in ascending order of cost from last
     * seen special node
     * 
     * @param lastDiscovered
     * @param undiscovered
     */
    private void sortUndiscovered(SpecialNode lastDiscovered, List<SpecialNode> undiscovered) {
	Comparator<SpecialNode> comparator = new Comparator<SpecialNode>() {
	    public int compare(SpecialNode n1, SpecialNode n2) {
		Map<SpecialNode, Integer> costsFromLast = cost.get(lastDiscovered);
		Integer costFromLastToN1 = costsFromLast.get(n1);
		Integer costFromLastToN2 = costsFromLast.get(n2);
		return costFromLastToN2 - costFromLastToN1;
	    }
	};
	Collections.sort(undiscovered, comparator);
    }

    /**
     * Register the callback class
     * 
     * @param tspCallback	The instance of TSPCallback to call callback methods on
     */
    public void registerCallBack(TSPCallback tspCallback) {
	this.tspCallback = tspCallback;
    }

    /**
     * Stop the ongoing TSP calculation
     */
    public void stopCalculation() {
	this.calculationShouldContinue = false;
    }
    
    /**
     * @return true if the algorithm's computing time is greater than the time limit
     *         set by the user
     */
    public Boolean getTimeLimitReached() {
	return timeLimitReached;
    }

    /**
     * Get the ordered list of SpecialNodes The order of the special nodes is
     * computed by branchAndBound
     * 
     * @return transformed solution : a list of special nodes
     */
    public List<SpecialNode> getTransformedSolution() {
	if (this.bestSolution == null)
	    return null;
	return this.bestSolution;
    }
}
