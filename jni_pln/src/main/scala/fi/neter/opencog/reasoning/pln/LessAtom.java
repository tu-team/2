package fi.neter.opencog.reasoning.pln;

/**
 * From: PLNAtom.h
 * 
 * struct lessatom : public std::binary_function<atom, atom, bool>
 * {
 * };

 * @author tero
 *
 */
public class LessAtom {
	/**
	 * bool operator()(const atom& lhs, const atom& rhs) const;
	 */
	public native boolean apply(Atom lhs, Atom rhs);
}
