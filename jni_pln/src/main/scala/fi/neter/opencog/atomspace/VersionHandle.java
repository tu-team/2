package fi.neter.opencog.atomspace;

/**
 * From file: VersionHandle.h
 * @author tero
 *
 */
public class VersionHandle {
	/**
	 * #define NULL_VERSION_HANDLE VersionHandle()
	 */
	public static final VersionHandle NULL_VERSION_HANDLE = construct();
	
	
    // Default constructor, gets a NULL_VERSION_HANDLE.
    /**
     * VersionHandle();
     * @param ind
     * @param subs
     */
	public native static VersionHandle construct();

    // subs represents the context or hypothesis,
    // not the atom to make the handle for
    /**
     * VersionHandle(IndicatorType ind, Handle subs);
     */
    /**
     * VersionHandle( const VersionHandle& other );
     */

    // Needed for comparison within vtree
    /**
     * bool operator<(const VersionHandle &other) const;
     */
    /**
     * bool operator>(const VersionHandle &other) const;
     */
    /**
     * bool operator==(const VersionHandle &other) const;
     */
    /**
     * bool operator!=(const VersionHandle &other) const;
     */
    /**
     * VersionHandle& operator=( const VersionHandle& other );
     */

    /**
     * static const char* indicatorToStr(IndicatorType) throw (InvalidParamException);
     * @return
     */
    /**
     * static IndicatorType strToIndicator(const char*) throw (InvalidParamException);
     */

}
