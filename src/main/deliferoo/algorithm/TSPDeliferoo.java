package algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import model.SpecialNode;

public class TSPDeliferoo extends TemplateTSP {

	@Override
	protected Iterator<SpecialNode> iterator(SpecialNode currentNode, ArrayList<SpecialNode> undiscovered) {
		return new IteratorSeq(undiscovered, currentNode);
	}

	@Override
	protected int bound(Integer currentNode, ArrayList<Integer> undiscovered, int[][] cost, int[] duration) {
		return 0;
	}
}
