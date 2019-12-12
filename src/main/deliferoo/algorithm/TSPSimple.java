package algorithm;

import java.util.List;
import java.util.Map;

import model.SpecialNode;

/**
 * 
 * @author sadsitha
 *
 */
public class TSPSimple extends TSP {

    public TSPSimple(Number speed) {
	super(speed);
    }

    @Override
    int bound(SpecialNode currentNode, SpecialNode startNode, List<SpecialNode> undiscovered,
	    Map<SpecialNode, Map<SpecialNode, Integer>> cost) {
	return 0;
    }

}
