package fi.neter.opencog.server;

import fi.neter.opencog.atomspace.AtomSpace;

/**
 * This class implements a base server class that provides basic functionality
 * for third parties who might wish to use their custom server.
 * There are only two primitives provided by this class: an atomspace instance
 * and a factory method.
 *
 * Applications extending the BaseServer must ensure that:
 *      1. the derived server overrides the factory method 'createInstance'
 *      2. the first call to the 'opencog::server()' global function explicitly
 *         passes the derived server's factory method as parameter.
 *
 * See the files CogServerMain.cc, CogServerh and CogServercc for examples.
 * 
 * From file: BaseServer.h
 */
public class BaseServer {
	/**
	 * Pointer to the C++ object. Note: 64-bit only.
	 */
	public long handle;

	/**
	 * Returns the atomspace instance.
	 * 
	 * static AtomSpace* getAtomSpace();
	 */
	public static native AtomSpace getAtomSpace();

	/**
	 * Returns a new BaseServer instance.
	 * 
	 * static BaseServer* createInstance(void);
	 */
	public static native BaseServer createInstance();

	/**
	 * BaseServer(void);
	 */
	public static native BaseServer construct();
	/**
	 * virtual ~BaseServer(void);
	 */
	public native void destroy();
}
