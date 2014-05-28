package eu.citadel.converter.gui.wizard.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pair<L,R> {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(Pair.class);

	private final L left;
	private final R right;

	public Pair(L left, R right) {
		logger.trace("Pair(L, R) - start");

		this.left = left;
		this.right = right;

		logger.trace("Pair(L, R) - end");
	}

	public L getLeft() {
		logger.trace("getLeft() - start");

		logger.trace("getLeft() - end");
 return left; }
	public R getRight() {
		logger.trace("getRight() - start");

		logger.trace("getRight() - end");
 return right; }

	@Override
	public int hashCode() { return left.hashCode() ^ right.hashCode(); }

	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof Pair)) return false;
		Pair<?, ?> pairo = (Pair<?, ?>) o;
		return this.left.equals(pairo.getLeft()) &&
				this.right.equals(pairo.getRight());
	}
	
	@Override
	public String toString() {
		return left.toString();
	}

}