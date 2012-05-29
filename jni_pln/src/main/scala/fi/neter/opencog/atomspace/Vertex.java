package fi.neter.opencog.atomspace;

/**
 * From file: types.h
 * @author tero
 *
 */
public class Vertex {
	/**
	 * typedef boost::variant<Handle, Type, int, unsigned int, float, bool,
	 * unsigned char, char, short int> Vertex;
	 */
	public native Object getValue();
	public native void setValue(Object value);
}
