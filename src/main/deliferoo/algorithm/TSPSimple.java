package algorithm;

import java.util.ArrayList;
import java.util.Map;

import model.SpecialNode;

/**
 * 
 * @author sadsitha
 *
 */
public class TSPSimple extends TSP {

    protected int bound(SpecialNode currentNode, String finishNodeID, ArrayList<SpecialNode> undiscovered,
	    Map<String, Map<String, Integer>> cost) {
	return 0;
    }

}
