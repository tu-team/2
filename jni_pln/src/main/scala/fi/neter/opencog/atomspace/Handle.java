package fi.neter.opencog.atomspace;

/**
 * From Handle.h
 * @author tero
 *
 */
public class Handle {
	/**
	 * Pointer to the C++ object. Note: 64-bit only.
	 */
	public long handle;

	public static native Handle construct();
	
	/**
	 * static const Handle UNDEFINED;
	 * @return
	 */
	public static native Handle getUndefined();

	/**
	 * explicit Handle(const UUID u) : uuid(u) {};
	 */
	public static native Handle create(UUID u);
	
	/**
	 * Handle(const Handle& h) : uuid(h.uuid) {};
	 * @return
	 */
	public static native Handle create(Handle h);
	
	/**
	 * explicit Handle() : uuid(UNDEFINED.uuid) {};
	 */
	public static native Handle create();

	/**
	 * ~Handle() {}
	 */
	public native void destroy();

	/**
	 * inline UUID value(void) const {
	 */
	public native UUID value();

	/**
	 * inline Handle& operator=(const Handle& h) {
	 */
	public native Handle operatorSet(Handle h);

	/**
	 * inline bool operator==(const Handle& h) const { return uuid == h.uuid; }
	 */
	/**
	 * inline bool operator!=(const Handle& h) const { return uuid != h.uuid; }
	 */
	/**
	 * inline bool operator< (const Handle& h) const { return uuid <  h.uuid; }
	 */
	/**
	 * inline bool operator> (const Handle& h) const { return uuid >  h.uuid; }
	 */
	/**
	 * inline bool operator<=(const Handle& h) const { return uuid <= h.uuid; }
	 */
	/**
	 * inline bool operator>=(const Handle& h) const { return uuid >= h.uuid; }
	 */

	/**
	 * Returns a negative value, zero or a positive value if the first
	 * argument is respectively smaller than, equal to, or larger than
	 * the second argument.
	 *
	 * @param The first handle element.
	 * @param The second handle element.
	 * @return A negative value, zero or a positive value if the first
	 * argument is respectively smaller than, equal to, or larger then the
	 * second argument.
	 * 
	 * static int compare(Handle h1, Handle h2)
	 */

	
	/**
	 * static inline std::string operator+ (const char *lhs, Handle h)
	 */

	/**
	 * static inline std::string operator+ (const std::string &lhs, Handle h)
	 */

	/**
	 * inline std::size_t hash_value(Handle const& h)
	 */

	/**
	 * inline std::ostream& operator<<(std::ostream& out, const opencog::Handle& h) {
	 */
}
