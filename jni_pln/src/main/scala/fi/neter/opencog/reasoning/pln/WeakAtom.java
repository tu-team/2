package fi.neter.opencog.reasoning.pln;

/**
 * From file: PLNUtils.h
 * @author tero
 *
 */
public class WeakAtom<T> {
	/**
	 * ATOM_REPRESENTATION_T value;
	 */
	/**
	 * Btr<bindingsT> bindings;
	 * @return
	 */

	/**
	 * ATOM_REPRESENTATION_T GetValue() const {
	 */

	/**
	 * weak_atom( ATOM_REPRESENTATION_T _value,
	 *             bindingsT* _bindings = NULL)
	 *             	            : value(_value), bindings(_bindings) {}
	 */
	   
	/**
	 * weak_atom( ATOM_REPRESENTATION_T _value,
	 *             Btr<bindingsT> _bindings)
	 *             : value(_value), bindings(_bindings) {}
	 */

	/**
	 * weak_atom() : bindings(new bindingsT) {}
	 */
	/**
	 * ~weak_atom() { }
	 */
	    /// Shared ownership of bindings!
	/**
	 * weak_atom(const weak_atom& rhs)
	 *          : value(rhs.value), bindings(rhs.bindings) {}
	 */
	   
	    /// Does not share ownership of bindings!!!
	/**
	 * weak_atom(const weak_atom& rhs, Btr<bindingsT> _bindings)
	 * 	         : value(rhs.value) {
	 */
	   
	/**
	 * bool operator()(pHandle h);
	 */
	/**
	 * bool operator<(const weak_atom<ATOM_REPRESENTATION_T>& rhs) const {
	 */

	/// this method is used nowhere and where reading it is seems buggy
	/**
	 * void apply_bindings();
	 */
}
