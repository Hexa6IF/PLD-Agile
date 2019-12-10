package algorithm;

import java.util.List;
import java.util.Map;

import model.BestPath;
import model.Delivery;

public class TSPHeuristic extends TSP {

    public TSPHeuristic(Map<String, Map<String, BestPath>> graph, List<Delivery> deliveries, Long timeLimit) {
	super(graph, deliveries, timeLimit);
    }

    @Override
    int bound(Integer currentNode, List<Integer> undiscovered) {
	Integer bound = 0;
	Integer lengthToCurrent = Integer.MAX_VALUE;
	Integer lastDropOff = this.dropoffs.get(this.dropoffs.get(0));
	for (int i = 0; i < undiscovered.size(); i++) {
	    Integer undiscoveredNode = undiscovered.get(i);
	    if (this.cost.get(currentNode).get(undiscoveredNode) < lengthToCurrent) {
		lengthToCurrent = cost.get(currentNode).get(undiscoveredNode);
	    }
	    Integer undiscToUndiscLength = cost.get(undiscoveredNode).get(lastDropOff);
	    bound += undiscToUndiscLength;
	}
	bound += lengthToCurrent;
	return bound;
    }

}
