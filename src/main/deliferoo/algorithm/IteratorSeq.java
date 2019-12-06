package algorithm;

import java.util.Collection;
import java.util.Iterator;

public class IteratorSeq implements Iterator<String> {

	private String[] candidates;
	private int nbCandidates;

	/**
	 * Cree un iterateur pour iterer sur l'ensemble des sommets de nonVus
	 * @param undiscovered
	 * @param currentNode
	 */
	public IteratorSeq(Collection<String> undiscovered, int currentNode){
		this.candidates = new String[undiscovered.size()];
		nbCandidates = 0;
		for (String s : undiscovered){
			candidates[nbCandidates++] = s;
		}
	}
	
	@Override
	public boolean hasNext() {
		return nbCandidates > 0;
	}

	@Override
	public String next() {
		return candidates[--nbCandidates];
	}

	@Override
	public void remove() {}

}
