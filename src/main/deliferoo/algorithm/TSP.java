package algorithm;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import controller.TSPCallback;
import model.BestPath;
import model.Delivery;
import model.SpecialNode;
import model.SpecialNodeType;

/**
 * 
 * @author Louis
 *
 */
public abstract class TSP {

    private ArrayList<BestPath> bestPathSolution;
    private ArrayList<SpecialNode> bestSolution;
    private int bestSolutionCost = 0;
    private Boolean timeLimitReached;
    private Map<String, Map<String, BestPath>> graph;
    private List<Delivery> deliveries;
    private Map<String, Map<String, Integer>> cost;
    private TSPCallback tspCallback;
    private Boolean calculationShouldContinue;

    /**
     * Constructor for TSP
     */
    public TSP() {
	this.calculationShouldContinue = true;
    }

    /**
     * @return true if the algorithm's computing time is greater than the time limit
     *         set by the user
     */
    public Boolean getTimeLimitReached() {
	return timeLimitReached;
    }

    /**
     * @return the cost in minutes for the best solution
     */
    public int getBestSolutionCost() {
	return bestSolutionCost;
    }

    /**
     * Create a list of BestPath from a list of SpecialNodes The order of the
     * special nodes is computed by branchAndBound whereas the list of BestPath the
     * graph computed by Dijkstra
     * 
     * @return
     */
    public List<BestPath> getBestPathSolution() {
	if (this.bestSolution == null)
	    return null;
	else {
	    this.setPassageTimeForBestSolution(this.cost);
	    this.bestPathSolution = new ArrayList<BestPath>();
	    for (int i = 0; i < this.bestSolution.size() - 1; i++) {
		BestPath path = this.graph.get(this.bestSolution.get(i).getNode().getNodeId())
			.get(this.bestSolution.get(i + 1).getNode().getNodeId());
		this.bestPathSolution.add(path);
	    }
	}
	return this.bestPathSolution;
    }

    /**
     * This is the method to execute if we want to compute the best solution
     * 
     * @param timeLimit  the algorithm stops if the time limit in ms is reached
     * @param graph      computed by Dijkstra
     * @param deliveries
     */
    public void searchSolution(int timeLimit, Map<String, Map<String, BestPath>> graph, List<Delivery> deliveries) {
	this.initClassVar(timeLimit, graph, deliveries);
	this.cost = this.createCostFromGraph();
	ArrayList<SpecialNode> undiscovered = this.initUndiscovered();
	ArrayList<SpecialNode> discovered = new ArrayList<SpecialNode>();
	SpecialNode startNode = this.deliveries.get(0).getPickupNode();
	discovered.add(startNode);
	branchAndBound(startNode, undiscovered, discovered, 0, this.cost, System.currentTimeMillis(), timeLimit);
	if (this.tspCallback != null)
	    this.tspCallback.calculationsCompleted(); //indicate that calculation is complete
    }

    /**
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
     * @return
     */
    private ArrayList<SpecialNode> initUndiscovered() {
	ArrayList<SpecialNode> undiscovered = new ArrayList<SpecialNode>();
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
     * @return
     */
    private Map<String, Map<String, Integer>> createCostFromGraph() {
	HashMap<String, Map<String, Integer>> cost = new HashMap<String, Map<String, Integer>>();
	for (String nodeIDKeyOne : this.graph.keySet()) {
	    HashMap<String, Integer> subMap = new HashMap<String, Integer>();
	    for (String nodeIDKeyTwo : this.graph.get(nodeIDKeyOne).keySet()) {
		Integer duration = this.graph.get(nodeIDKeyOne).get(nodeIDKeyTwo).getDistance().intValue() * 60 / 15000;
		subMap.put(nodeIDKeyTwo, duration);
	    }
	    cost.put(nodeIDKeyOne, subMap);
	}
	return cost;
    }

    /**
     * The algorithm's heuristic.
     * 
     * @param currentNode
     * @param undiscovered : tableau des sommets restant a visiter
     * @param cost         : cout[i][j] = duree pour aller de i a j, avec 0 <= i <
     *                     nbSommets et 0 <= j < nbSommets
     * @param duration     : duree[i] = duree pour visiter le sommet i, avec 0 <= i
     *                     < nbSommets
     * @return une borne inferieure du cout des permutations commencant par
     *         sommetCourant, contenant chaque sommet de nonVus exactement une fois
     *         et terminant par le sommet 0
     */
    abstract int bound(SpecialNode currentNode, String startNodeID, ArrayList<SpecialNode> undiscovered,
	    Map<String, Map<String, Integer>> cost);

    /**
     * 
     * @param currentNode
     * @param undiscovered : tableau des sommets restant a visiter
     * @param cost         : cout[i][j] = duree pour aller de i a j, avec 0 <= i <
     *                     nbSommets et 0 <= j < nbSommets
     * @param duration     : duree[i] = duree pour visiter le sommet i, avec 0 <= i
     *                     < nbSommets
     * @return un iterateur permettant d'iterer sur tous les sommets de nonVus
     */
    private Iterator<SpecialNode> iterator(SpecialNode currentNode, ArrayList<SpecialNode> undiscovered) {
	return new IteratorSeq(undiscovered, currentNode);
    }

    /**
     * Methode definissant le patron (template) d'une resolution par separation et
     * evaluation (branch and bound) du TSP
     * 
     * @param currentNode    le dernier sommet visite
     * @param undiscovered   la liste des sommets qui n'ont pas encore ete visites
     * @param discovered     la liste des sommets visites (y compris sommetCrt)
     * @param discoveredCost la somme des couts des arcs du chemin passant par tous
     *                       les sommets de vus + la somme des duree des sommets de
     *                       vus
     * @param cost           : cout[i][j] = duree pour aller de i a j, avec 0 <= i <
     *                       nbSommets et 0 <= j < nbSommets
     * @param duration       : duree[i] = duree pour visiter le sommet i, avec 0 <=
     *                       i < nbSommets
     * @param startTime      : moment ou la resolution a commence
     * @param timeLimit      : limite de temps pour la resolution
     */
    private void branchAndBound(SpecialNode currentNode, ArrayList<SpecialNode> undiscovered,
	    ArrayList<SpecialNode> discovered, int discoveredCost, Map<String, Map<String, Integer>> cost,
	    long startTime, int timeLimit) {
	if (!this.calculationShouldContinue && (System.currentTimeMillis() - startTime > timeLimit)) {
	    timeLimitReached = true;
	} else if (undiscovered.size() == 0 && !discovered.contains(this.deliveries.get(0).getDeliveryNode())) {
	    undiscovered.add(this.deliveries.get(0).getDeliveryNode());
	    branchAndBound(currentNode, undiscovered, discovered, discoveredCost, cost, startTime, timeLimit);
	} else if (undiscovered.size() == 0) { // tous les sommets ont ete visites
	    SpecialNode startNode = this.deliveries.get(0).getPickupNode();
	    discoveredCost += cost.get(currentNode.getNode().getNodeId()).get(startNode.getNode().getNodeId());
	    if (discoveredCost < bestSolutionCost) { // on a trouve une solution meilleure que bestSolution
		this.bestSolution.clear();
		this.bestSolution.addAll(discovered);
		bestSolutionCost = discoveredCost;
		if (this.tspCallback != null)
		    this.tspCallback.bestSolutionUpdated(); // indicate that a new best solution has been found
	    }
	} else if (discoveredCost < bestSolutionCost) {
	    Iterator<SpecialNode> it = iterator(currentNode, undiscovered);
	    while (it.hasNext()) {
		SpecialNode nextNode = it.next();
		discovered.add(nextNode);
		Long costCurrentToNext = cost.get(currentNode.getNode().getNodeId()).get(nextNode.getNode().getNodeId())
			.longValue();
		undiscovered.remove(nextNode);
		if (nextNode.getSpecialNodeType() == SpecialNodeType.PICKUP) {
		    undiscovered.add(nextNode.getDelivery().getDeliveryNode());
		}
		String startNodeID = this.deliveries.get(0).getPickupNode().getNode().getNodeId();
		branchAndBound(
			nextNode, undiscovered, discovered, discoveredCost + costCurrentToNext.intValue()
				+ nextNode.getDuration().intValue() + this.bound(currentNode, startNodeID, undiscovered, cost),
			cost, startTime, timeLimit);
		discovered.remove(nextNode);
		undiscovered.add(nextNode);
		if (nextNode.getSpecialNodeType() == SpecialNodeType.PICKUP) {
		    undiscovered.remove(nextNode.getDelivery().getDeliveryNode());
		}
	    }
	}
    }

    /**
     * Set the passage time for special nodes
     * 
     * @param cost
     */
    private void setPassageTimeForBestSolution(Map<String, Map<String, Integer>> cost) {
	for (int i = 1; i < this.bestSolution.size(); i++) {
	    SpecialNode node = this.bestSolution.get(i);
	    SpecialNode previousNode = this.bestSolution.get(i - 1);
	    LocalTime previousPassageTime = previousNode.getPassageTime();
	    Long costToAdd = cost.get(previousNode.getNode().getNodeId()).get(node.getNode().getNodeId()).longValue();
	    Long minutesToAdd = previousNode.getDuration().longValue() + costToAdd;
	    node.setPassageTime(previousPassageTime.plusMinutes(minutesToAdd));
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
