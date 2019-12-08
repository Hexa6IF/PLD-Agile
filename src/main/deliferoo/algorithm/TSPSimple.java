package algorithm;

import java.util.ArrayList;
import java.util.Map;

import model.SpecialNode;

public class TSPSimple extends TSP {

    protected int bound(SpecialNode currentNode, ArrayList<SpecialNode> undiscovered,
	    Map<String, Map<String, Integer>> cost) {
	return 0;
    }

}
