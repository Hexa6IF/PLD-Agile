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
    
    private static SpecialNode startNode;
    private static SpecialNode finishNode;
    
    public static Map<String, Map<String, BestPath>> calculateAllShortestPaths(List<Delivery> deliveries, FullMap map) {
	Set<SpecialNode> specialNodes = getSpecialNodes(deliveries);
	Map<String, Map<String, BestPath>> totalShortPaths = new HashMap<String, Map<String, BestPath>>(); 
	
	for(SpecialNode startNode : specialNodes) {
	    if (startNode == Dijkstra.finishNode) {
		continue;
	    }
	    totalShortPaths.put(startNode.getNode().getNodeId(), calculateShortestPaths(startNode, specialNodes, map));
	}
	return totalShortPaths;
    }
    
    public static Map<String, BestPath> calculateShortestPaths(SpecialNode startNode, Set<SpecialNode> specialNodes, FullMap map) {
	Integer nodeCount = map.getNodeMap().size();
	
	Map<String, Double> shortestPaths = new HashMap<String, Double>(); //Shortest paths to respective nodes currently found
	Map<String, String> predecessor = new HashMap<String, String>(); //Preceding node of each node

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
	
	String startNodeId = startNode.getNode().getNodeId();
	
	undiscoveredNodes.remove(startNodeId);
	unsettledNodes.add(new Pair<String, Double>(startNodeId, 0d));

	while (unsettledNodes.size() != 0) {
	    Pair<String, Double> currentNode = unsettledNodes.poll();
	    for(String neighborId : neighborGraph.get(currentNode.getKey()).keySet()) { // For each neighbor 
		if(!settledNodes.contains(neighborId)) { // If neighbor is not a settled node
		    Double distance = neighborGraph.get(currentNode.getKey()).get(neighborId).getDistance(); // Current distance to the specified neighbor
		    if(shortestPaths.get(neighborId) > currentNode.getValue() + distance) { // Check if discovered distance is shorter than current shortest distance
			shortestPaths.put(neighborId, currentNode.getValue() + distance);
			predecessor.put(neighborId, currentNode.getKey());
			unsettledNodes.add(new Pair<String, Double>(neighborId, currentNode.getValue() + distance)); 
		    }
		    
		    if(undiscoveredNodes.contains(neighborId)) { // If neighbor is undiscovered -> neighbor becomes unsettled
			undiscoveredNodes.remove(neighborId);
			unsettledNodes.add(new Pair<String, Double>(neighborId, currentNode.getValue() + distance));
		    }
		}
	    }
	    settledNodes.add(currentNode.getKey()); // Current node becomes settled
	}
	
	return getBestPaths(map, specialNodes, startNode, predecessor, neighborGraph);
    }
    
    private static Map<String, BestPath> getBestPaths(FullMap map, Set<SpecialNode> specialNodes, SpecialNode startNode, Map<String, String> predecessor, Map<String, Map<String, Edge>> neighborGraph){
	Map<String, BestPath> bestPaths = new HashMap<String, BestPath>();
	
	for(SpecialNode sn : specialNodes) {
	    if (sn == Dijkstra.startNode) {
		continue;
	    }
	    String nodeId = sn.getNode().getNodeId();
	    LinkedList<Edge> edges = new LinkedList<Edge>();
	    while(!nodeId.equals(startNode.getNode().getNodeId())) {
		String precedingId = predecessor.get(nodeId);
		if(precedingId == null) {
		    break;
		}
		Edge partialPath = neighborGraph.get(precedingId).get(nodeId);
		edges.addFirst(partialPath);
		nodeId = precedingId;
	    }
	    bestPaths.put(sn.getNode().getNodeId(), new BestPath(startNode, sn, edges));
	}
	
	return bestPaths;
    }
    
    private static Set<SpecialNode> getSpecialNodes(List<Delivery> deliveries) {
	Set<SpecialNode> specialNodes = new HashSet<SpecialNode>();
	Dijkstra.startNode = deliveries.get(0).getPickupNode();
	Dijkstra.finishNode = deliveries.get(0).getDeliveryNode();
	
	for(Delivery delivery : deliveries) {
	    specialNodes.add(delivery.getDeliveryNode());
	    specialNodes.add(delivery.getPickupNode());
	}
	
	return specialNodes;
    }

    private static Map<String, Map<String, Edge>> getNeighborGraph(FullMap map) {	
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