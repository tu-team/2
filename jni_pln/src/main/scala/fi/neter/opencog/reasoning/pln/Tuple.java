package fi.neter.opencog.reasoning.pln;

/**
 * Just a helper class. Based on std::pair.
 * @author tero
 *
 * @param <A>
 * @param <B>
 */
public class Tuple <A, B> {
	private A a;
	private B b;
	
	public A get1() {
		return a;
	}
	public B get2() {
		return b;
	}
	
	public void set1(A a) {
		this.a = a;
	}

	public void set2(B b) {
		this.b = b;
	}
}
