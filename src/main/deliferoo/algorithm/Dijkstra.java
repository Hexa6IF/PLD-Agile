package algorithm;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import javafx.util.Pair;
import model.BestPath;
import model.Delivery;
import model.Edge;
import model.FullMap;
import model.SpecialNode;

public class Dijkstra {    
    public Map<String, BestPath> calculateShortestPaths(List<Delivery> deliveries, FullMap map) {
	Integer nodeCount = map.getNodeMap().size();
	
	Map<String, Double> shortestPaths = new HashMap<String, Double>();
	Map<String, String> predecessor = new HashMap<String, String>();

	Comparator<Pair<String, Double>> customComparator = new Comparator<Pair<String, Double>>() {
	    @Override
	    public int compare(Pair<String, Double> p1, Pair<String, Double> p2) {
		Double difference = p1.getValue() - p2.getValue();
		if (difference > 0) {
		    return 1;
		} else if (difference < 0) {
		    return -1;
		}
		return 0;
	    }
	};

	Set<String> undiscoveredNodes = new HashSet<String>();
	PriorityQueue<Pair<String, Double>> unsettledNodes = new PriorityQueue<Pair<String, Double>>(nodeCount, customComparator);
	Set<String> settledNodes = new HashSet<String>();
	
	Map<String, Map<String, Edge>> neighborGraph = getNeighborGraph(map);

	for(String nodeId : map.getNodeMap().keySet()) {
	    undiscoveredNodes.add(nodeId);
	    shortestPaths.put(nodeId, Double.POSITIVE_INFINITY);
	}
	
	SpecialNode startNode = deliveries.remove(0).getPickupNode();
	String startNodeId = startNode.getNode().getNodeId();
	
	undiscoveredNodes.remove(startNodeId);
	unsettledNodes.add(new Pair<String, Double>(startNodeId, 0d));

	while (unsettledNodes.size() != 0) {
	    Pair<String, Double> currentNode = unsettledNodes.poll();
	    for(String neighborId : neighborGraph.get(currentNode.getKey()).keySet()) {
		if(!settledNodes.contains(neighborId)) {
		    Double distance = neighborGraph.get(currentNode.getKey()).get(neighborId).getDistance();
		    if(shortestPaths.get(neighborId) < currentNode.getValue() + distance) {
			shortestPaths.put(neighborId, currentNode.getValue() + distance);
			predecessor.put(neighborId, currentNode.getKey());
		    }
		    
		    if(undiscoveredNodes.contains(neighborId)) {
			undiscoveredNodes.remove(neighborId);
			unsettledNodes.add(new Pair<String, Double>(neighborId, distance));
		    }
		}
	    }
	    settledNodes.add(currentNode.getKey());
	}
	
	return getBestPaths(neighborGraph, predecessor, startNode, deliveries, map);
    }
    
    private Map<String, BestPath> getBestPaths(Map<String, Map<String, Edge>> neighborGraph, Map<String, String> predecessor, SpecialNode startNode, List<Delivery> deliveries, FullMap map) {
	Set<SpecialNode> specialNodes = getSpecialNodes(deliveries);
	Map<String, BestPath> bestPaths = new HashMap<String, BestPath>();
	
	for(SpecialNode endNode : specialNodes) {
	    String nodeId = endNode.getNode().getNodeId();
	    LinkedList<Edge> edges = new LinkedList<Edge>();
	    while(!nodeId.equals(startNode.getNode().getNodeId())) {
		String precedingId = predecessor.get(nodeId);		
		Edge partialPath = neighborGraph.get(precedingId).get(nodeId);
		edges.addFirst(partialPath);
		nodeId = precedingId;
	    }
	    bestPaths.put(endNode.getNode().getNodeId(), new BestPath(startNode, endNode, edges));
	}
	
	return bestPaths;
    }
    
    private Set<SpecialNode> getSpecialNodes(List<Delivery> deliveries) {
	Set<SpecialNode> specialNodes = new HashSet<SpecialNode>();
	
	for(Delivery delivery : deliveries) {
	    specialNodes.add(delivery.getDeliveryNode());
	    specialNodes.add(delivery.getPickupNode());
	}
	
	return specialNodes;
    }

    private Map<String, Map<String, Edge>> getNeighborGraph(FullMap map) {	
	Map<String, Map<String, Edge>> neighbors = new HashMap<String, Map<String, Edge>>();
	
	for(String nodeId : map.getNodeMap().keySet()) {
	    neighbors.put(nodeId, new HashMap<String,Edge>());
	}
	
	for(Edge edge : map.getEdgeList()) {
	    String startId = edge.getStart().getNodeId();
	    String endId = edge.getEnd().getNodeId();
	    
	    neighbors.get(startId).put(endId, edge);
	    neighbors.get(endId).put(startId, edge);	    
	}
	
	return neighbors;
    }
}