package algorithm;

import java.util.Collection;
import java.util.Iterator;

import model.SpecialNode;

public class IteratorSeq implements Iterator<SpecialNode> {

	private SpecialNode[] candidates;
	private int nbCandidates;

	/**
	 * Cree un iterateur pour iterer sur l'ensemble des sommets de nonVus
	 * 
	 * @param undiscovered
	 * @param currentNode
	 */
	public IteratorSeq(Collection<SpecialNode> undiscovered, SpecialNode currentNode) {
		this.candidates = new SpecialNode[undiscovered.size()];
		nbCandidates = 0;
		for (SpecialNode s : undiscovered) {
			candidates[nbCandidates++] = s;
		}
	}

	@Override
	public boolean hasNext() {
		return nbCandidates > 0;
	}

	@Override
	public SpecialNode next() {
		return candidates[--nbCandidates];
	}

	@Override
	public void remove() {
	}

}
