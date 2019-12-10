package algorithm;

import java.util.List;
import java.util.Map;

import model.BestPath;
import model.Delivery;

/**
 * 
 * @author sadsitha
 *
 */
public class TSPSimple extends TSP {

    public TSPSimple(Map<String, Map<String, BestPath>> graph, List<Delivery> deliveries, Long timeLimit) {
	super(graph, deliveries, timeLimit);
    }

    protected int bound(Integer currentNode, List<Integer> undiscovered) {
	return 0;
    }

}
