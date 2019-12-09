package algorithm;

import java.util.ArrayList;
import java.util.Map;

import model.SpecialNode;

public class TSPHeuristic extends TSP {

    @Override
    int bound(SpecialNode currentNode, String finishNodeID, ArrayList<SpecialNode> undiscovered, Map<String, Map<String, Integer>> cost) {
	Integer bound = 0;
	if (undiscovered.size() == 0) {
	    return bound;
	}
	Integer lengthToCurrent = Integer.MAX_VALUE;
	String currentNodeID = currentNode.getNode().getNodeId();
	for (int i=0; i<undiscovered.size(); i++) {
	    SpecialNode undiscoveredNode = undiscovered.get(i);
	    String undiscoveredNodeID = undiscoveredNode.getNode().getNodeId();
	    if (cost.get(currentNodeID).get(undiscoveredNodeID) < lengthToCurrent) {
		lengthToCurrent = cost.get(currentNodeID).get(undiscoveredNodeID);
	    }
	    Integer undiscToUndiscLength = Integer.MAX_VALUE;
	    /*for (int j=0; j<undiscovered.size(); j++) {
		SpecialNode anotherUndiscoveredNode = undiscovered.get(j);
		String anotherUndiscoveredNodeID = anotherUndiscoveredNode.getNode().getNodeId();
		if (i != j && cost.get(undiscoveredNodeID).get(anotherUndiscoveredNodeID) < undiscToUndiscLength) {
		    undiscToUndiscLength = cost.get(undiscoveredNodeID).get(anotherUndiscoveredNodeID);
		}
	    }*/
	    if (cost.get(undiscoveredNodeID).get(finishNodeID) < undiscToUndiscLength) {
		undiscToUndiscLength = cost.get(undiscoveredNodeID).get(finishNodeID);
	    }
	    bound += undiscToUndiscLength;
	}
	bound += lengthToCurrent;

	return bound;
    }

}
