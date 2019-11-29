package algorithm;

import java.util.Collection;
import java.util.Iterator;

public class IteratorSeq implements Iterator<Integer> {

	private Integer[] candidates;
	private int nbCandidates;

	/**
	 * Cree un iterateur pour iterer sur l'ensemble des sommets de nonVus
	 * @param undiscovered
	 * @param currentNode
	 */
	public IteratorSeq(Collection<Integer> undiscovered, int currentNode){
		this.candidates = new Integer[undiscovered.size()];
		nbCandidates = 0;
		for (Integer s : undiscovered){
			candidates[nbCandidates++] = s;
		}
	}
	
	@Override
	public boolean hasNext() {
		return nbCandidates > 0;
	}

	@Override
	public Integer next() {
		return candidates[--nbCandidates];
	}

	@Override
	public void remove() {}

}
