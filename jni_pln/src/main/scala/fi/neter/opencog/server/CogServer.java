package fi.neter.opencog.server;

import fi.neter.opencog.atomspace.AtomSpace;

/**
 * This class implements the official server used by the opencog framework. It
 * is responsible for managing 4 base structures: cycles, modules, agents and
 * requests. Additionally, it holds a reference to a NetworkServer instance,
 * which handles the network sockets used to provide external access to the
 * server.
 *
 * Cycles are handled by the server's main loop (method 'serverLoop'). Each
 * cycle has a minimum duration controlled by the parameter
 * "SERVER_CYCLE_DURATION" (in seconds). At the start of every cycle, the server
 * processes the queued requests and then executes an interaction of each
 * scheduled agent. When all the agents are finished, the server sleeps for the
 * remaining time until the end of the cycle (this avoids a 'busy wait'-style
 * main loop which was originally characteristic of Novamente server's).
 *
 * Module management is the part responsible for extending the server through
 * the use of dynamically loadable libraries (or modules). Valid modules must
 * extended the class defined in Module.h and be compiled and linked as a
 * shared library. Currently, only Unix DSOs are supported; Win32 DLLs will
 * hopefully come soon. The server api itself provides methods to
 * load, unload and retrieve  modules. The server provides modules with two
 * entry points: the constructor, which is typically invoked by the module's
 * load function; and the 'init' method, which is called after the module has
 * been instantiated and its meta-data has been filled.
 *
 * Agent management is done through inheritance from the Registry<Agent> class.
 * The agent registry api provides several methods to: 1. register, unregister
 * and list agents classes; 2. create and destroy agent instances; 3. start and
 * stop agents. We chose to wrap the register methods in the server class to
 * avoid conflicts with the other registry inheritance (Registry<Command>).
 *
 * Just like agent management, request management uses the same Registry base
 * template -- only, this time, using the Request base class. Thus, the
 * functionalities provided are very similar: 1. register, unregister and list
 * request classes; 2. create requests instances; 3. push/pop from requests
 * queue. Contrary to the agent management, the lifecycle of each Request is
 * controlled by the server itself (that why no "destroyRequest" is provided),
 * which destroys the instance right after its execution.
 *
 *  class CogServer : public BaseServer, public Registry<Agent>, public Registry<Request>
 */
public class CogServer extends BaseServer {
	/**
	 * Pointer to the C++ object. Note: 64-bit only.
	 */
	public long handle;
	
	/**
	 * Returns the atomspace instance.
	 * 
	 * static AtomSpace* getAtomSpace();
	 * 
	 * This is "inherited" from the BaseServer. The C++ and Java static inheritance is different.
	 */
	public static native AtomSpace getAtomSpace();
	
    /** CogServer's constructor. Initializes the mutex, atomspace and cycleCount
     *  variables
     *  
     * CogServer(void);
     */
	public static native CogServer construct();

    /** Factory method. override's the base class factory method and returns an
     *  instance of CogServer instead
     *  
     * static CogServer* createInstance(void);
     */
	public static native CogServer createInstance();

    /** CogServer's destructor. Disables the network server is unloads all
     * modules. */
    /**
     * virtual ~CogServer(void);
     */
	public native void destroy();
}
