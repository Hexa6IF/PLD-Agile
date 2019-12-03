package algorithm;

import java.util.ArrayList;
import java.util.Iterator;

public class TSP1 extends TemplateTSP {

	@Override
	protected Iterator<Integer> iterator(Integer currentNode, ArrayList<Integer> undiscovered, int[][] cost, int[] duration) {
		return new IteratorSeq(undiscovered, currentNode);
	}

	@Override
	protected int bound(Integer currentNode, ArrayList<Integer> undiscovered, int[][] cost, int[] duration) {
		return 0;
	}
}
