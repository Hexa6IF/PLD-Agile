package algorithm;

import java.util.List;
import java.util.Map;

import model.SpecialNode;

public class TSPHeuristic extends TSP {

    public TSPHeuristic(Number speed) {
	super(speed);
    }

    @Override
    int bound(SpecialNode currentNode, SpecialNode startNode, List<SpecialNode> undiscovered, Map<SpecialNode, Map<SpecialNode, Integer>> cost) {
	Integer bound = 0;
	Integer lengthToCurrent = Integer.MAX_VALUE;
	Integer lengthUndiscovered = undiscovered.size();
	for (int i=0; i<lengthUndiscovered; ++i) {
	    SpecialNode undiscoveredNode = undiscovered.get(i);
	    Integer tempLengthToCurrent = cost.get(currentNode).get(undiscoveredNode);
	    if (tempLengthToCurrent < lengthToCurrent) {
		lengthToCurrent = tempLengthToCurrent;
	    }
	    Integer undiscToUndiscLength = Integer.MAX_VALUE;
	    Integer tempUndiscToUndiscLength = 0;
	    for (int j=0; j<lengthUndiscovered; ++j) {
		SpecialNode anotherUndiscoveredNode = undiscovered.get(j);
		tempUndiscToUndiscLength = cost.get(undiscoveredNode).get(anotherUndiscoveredNode);
		if (i != j && tempUndiscToUndiscLength < undiscToUndiscLength) {
		    undiscToUndiscLength = tempUndiscToUndiscLength;
		}
	    }
	    tempUndiscToUndiscLength = cost.get(undiscoveredNode).get(startNode);
	    if (tempUndiscToUndiscLength < undiscToUndiscLength) {
		undiscToUndiscLength = tempUndiscToUndiscLength;
	    }
	    bound += undiscToUndiscLength;
	}
	bound += lengthToCurrent;
	return bound;
    }

}
