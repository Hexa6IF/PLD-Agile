package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import controller.TSPCallback;
import model.BestPath;
import model.Delivery;
import model.SpecialNode;

/**
 * 
 * @author Louis
 *
 */
public abstract class TSP {

    protected Map<String, Map<String, BestPath>> graph;
    protected List<Delivery> deliveries;
    protected ArrayList<Integer> bestSolution;
    protected Integer bestSolutionCost;
    protected ArrayList<ArrayList<Integer>> cost;
    protected ArrayList<Integer> duration;
    protected ArrayList<Integer> pickups;
    protected ArrayList<Integer> dropoffs;
    protected TSPCallback tspCallback;
    protected Long timeLimit;
    protected Long startTime;
    protected Boolean timeLimitReached;
    protected Boolean calculationShouldContinue;

    /**
     * Constructor for TSP
     */
    public TSP(Map<String, Map<String, BestPath>> graph, List<Delivery> deliveries, Long timeLimit) {
	this.calculationShouldContinue = true;
	this.timeLimit = timeLimit;
	this.graph = graph;
	this.deliveries = deliveries;
	this.cost = new ArrayList<ArrayList<Integer>>();
	this.duration = new ArrayList<Integer>();
	this.pickups = new ArrayList<Integer>();
	this.dropoffs = new ArrayList<Integer>();
	this.initialise(graph, deliveries);
    }

    private void initialise(Map<String, Map<String, BestPath>> graph, List<Delivery> deliveries) {
	Integer count = 0;
	for (Delivery i : deliveries) {
	    String pickupI = i.getPickupNode().getNode().getNodeId();
	    String dropoffI = i.getDeliveryNode().getNode().getNodeId();
	    ArrayList<Integer> subCostPickup = new ArrayList<Integer>();
	    ArrayList<Integer> subCostDropoff = new ArrayList<Integer>();
	    for (Delivery j : deliveries) {
		String pickupJ = j.getPickupNode().getNode().getNodeId();
		String dropoffJ = j.getDeliveryNode().getNode().getNodeId();
		Integer travelDurationpickupITopickupJ = graph.get(pickupI).get(pickupJ).getDistance().intValue() * 60
			/ 15000;
		Integer travelDurationpickupITodropoffJ = graph.get(pickupI).get(dropoffJ).getDistance().intValue() * 60
			/ 15000;
		Integer travelDurationdropoffITopickupJ = graph.get(dropoffI).get(pickupJ).getDistance().intValue() * 60
			/ 15000;
		Integer travelDurationdropoffITodropoffJ = graph.get(dropoffI).get(dropoffJ).getDistance().intValue()
			* 60 / 15000;
		subCostPickup.add(travelDurationpickupITopickupJ);
		subCostPickup.add(travelDurationpickupITodropoffJ);
		subCostDropoff.add(travelDurationdropoffITopickupJ);
		subCostDropoff.add(travelDurationdropoffITodropoffJ);
	    }
	    this.cost.add(subCostPickup);
	    this.cost.add(subCostDropoff);
	    Integer stayDurationpickupI = i.getPickupNode().getDuration().intValue();
	    Integer stayDurationdropoffI = i.getPickupNode().getDuration().intValue();
	    this.duration.add(stayDurationpickupI);
	    this.duration.add(stayDurationdropoffI);
	    this.pickups.add(count++);
	    this.dropoffs.add(count++);
	}
    }

    public void searchSolution() {
	this.timeLimitReached = false;
	this.startTime = System.currentTimeMillis();
	this.bestSolutionCost = Integer.MAX_VALUE;
	this.bestSolution = new ArrayList<Integer>();
	ArrayList<Integer> undiscovered = new ArrayList<Integer>(this.pickups);
	ArrayList<Integer> discovered = new ArrayList<Integer>();
	discovered.add(0);
	branchAndBound(0, undiscovered, discovered, 0);
	Long endTime = System.currentTimeMillis();
	System.out.println((endTime - startTime));
	if (this.tspCallback != null)
	    this.tspCallback.calculationsCompleted(); // indicate that calculation is complete
    }

    abstract int bound(Integer currentNode, List<Integer> undiscovered);

    void branchAndBound(Integer currentSummit, ArrayList<Integer> undiscovered, ArrayList<Integer> discovered,
	    Integer discoveredCost) {
	if (!this.calculationShouldContinue && System.currentTimeMillis() - this.startTime > this.timeLimit) {
	    this.timeLimitReached = true;
	    return;
	}
	Integer lastDropOff = this.dropoffs.get(this.dropoffs.get(0));
	if (undiscovered.size() == 0) {
	    if (!discovered.contains(lastDropOff)) {
		undiscovered.add(lastDropOff);
		branchAndBound(currentSummit, undiscovered, discovered, discoveredCost);
	    } else {
		discoveredCost += cost.get(currentSummit).get(0);
		if (discoveredCost < this.bestSolutionCost) { // on a trouve une solution meilleure que
							      // meilleureSolution
		    this.bestSolution = discovered;
		    this.bestSolutionCost = discoveredCost;
		    if (this.tspCallback != null)
			this.tspCallback.bestSolutionUpdated(); // indicate that a new best solution has been found
		}
	    }
	} else if (discoveredCost + bound(currentSummit, undiscovered) < this.bestSolutionCost) {
	    this.sortUndiscovered(currentSummit,undiscovered);
	    for (int i=0; i<undiscovered.size();++i) {
		Integer nextNode = undiscovered.get(i);
		discovered.add(nextNode);
		undiscovered.remove(nextNode);
		 Integer potentialDeliveryNode = nextNode+1;
		if (nextNode % 2 == 0) {
		    undiscovered.add(potentialDeliveryNode);
		}
		branchAndBound(nextNode, undiscovered, discovered,
			discoveredCost + this.cost.get(currentSummit).get(nextNode) + this.duration.get(nextNode));
		discovered.remove(nextNode);
		if (nextNode != lastDropOff) {
		    undiscovered.add(nextNode);
		}
		if (nextNode % 2 == 0) {
		    undiscovered.remove(potentialDeliveryNode);
		}
	    }
	}
    }

    /**
     * Sort undiscovered special node list in ascending order of distance from last
     * seen special node
     * 
     * @param lastDiscovered
     * @param undiscovered
     */
    private void sortUndiscovered(Integer lastDiscovered, ArrayList<Integer> undiscovered) {
	Comparator<Integer> comparator = new Comparator<Integer>() {
	    public int compare(Integer n1, Integer n2) {
		ArrayList<Integer> costsFromLast = cost.get(lastDiscovered);
		Integer costFromLastToN1 = costsFromLast.get(n1);
		Integer costFromLastToN2 = costsFromLast.get(n2);
		return costFromLastToN1 - costFromLastToN2;
	    }
	};
	Collections.sort(undiscovered, comparator);
    }

    public List<BestPath> getBestPathSolution() {
	if (this.bestSolution == null)
	    return null;
	else {
	    ArrayList<BestPath> bestPathSolution = new ArrayList<BestPath>();
	    ArrayList<SpecialNode> specialNodeList = new ArrayList<SpecialNode>();
	    for (Integer node : this.bestSolution) {
		Integer deliveryIndex = node / 2;
		Boolean isPickup = (node % 2) == 0;
		Delivery delivery = this.deliveries.get(deliveryIndex);
		SpecialNode sNode = isPickup ? delivery.getPickupNode() : delivery.getDeliveryNode();
		specialNodeList.add(sNode);
	    }
	    for (int i = 0; i < specialNodeList.size()-1; ++i) {
		String specialNodeId1 = specialNodeList.get(i).getNode().getNodeId();
		String specialNodeId2 = specialNodeList.get(i+1).getNode().getNodeId();
		BestPath bestPath = graph.get(specialNodeId1).get(specialNodeId2);
		bestPathSolution.add(bestPath);
	    }
	    return bestPathSolution;
	}
    }

    /**
     * Register the callback class
     * 
     * @param tspCallback
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
}
