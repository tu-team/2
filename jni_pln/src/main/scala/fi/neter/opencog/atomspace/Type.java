package fi.neter.opencog.atomspace;

/**
 * From types.h
 * @author tero
 *
 */
public class Type {
	/**
	 * Pointer to the C++ object. Note: 64-bit only.
	 */
	public long handle;

	public static native Type construct();
	
	public native void set(Long value);
	public native Long get();
}
