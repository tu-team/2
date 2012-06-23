package fi.neter.opencog.atomspace;

/**
 * From TruthValue.h
 * 
 * @author tero
 * 
 */
public class TruthValue {
	/**
	 * Pointer to the C++ object. Note: 64-bit only.
	 */
	public long handle;

	public static native Type construct();

	/**
	 * virtual ~TruthValue() {}
	 */
	public native void destroy();

	// Special TVs

	/**
	 * The shared reference to a special NullTruthValue object. This is supposed
	 * to be used as a Flag only and so, it cannot be used as a normal TV
	 * object, as for setting the TV object of an Atom, for example.
	 */
	/**
	 * static const TruthValue& NULL_TV();
	 */
	public static native TruthValue NULL_TV();

	/**
	 * The shared reference to a special default (Simple) TruthValue object with
	 * both mean and count set to default values (currently 0 and 0). This is
	 * supposed to be used as a temporary TV object (in Formulae and Rules
	 * internal TV arrays, for instance).
	 */
	/**
	 * static const TruthValue& DEFAULT_TV();
	 */
	public static native TruthValue DEFAULT_TV();

	/**
	 * The shared reference to a special TRUE (Simple) TruthValue object with
	 * MAX_TRUTH mean and MAX_TV_CONFIDENCE count.
	 */
	/**
	 * static const TruthValue& TRUE_TV();
	 */
	public static native TruthValue TRUE_TV();

	/**
	 * The shared reference to a special FALSE (Simple) TruthValue object with 0
	 * mean and MAX_TV_CONFIDENCE count.
	 */
	/**
	 * static const TruthValue& FALSE_TV();
	 */
	public static native TruthValue FALSE_TV();

	/**
	 * The shared reference to a special TRIVIAL (Simple) TruthValue object with
	 * 0 count.
	 */
	/**
	 * static const TruthValue& TRIVIAL_TV();
	 */
	public static native TruthValue TRIVIAL_TV();

	/**
	 * Gets the name of a TruthValue type
	 */
	/**
	 * static const char* getTVTypeName(TruthValueType);
	 */
	public static native String getTVTypeName(TruthValueType type);

	// PURE VIRTUAL METHODS:

	/**
	 * virtual strength_t getMean() const = 0;
	 */
	public native float getMean();

	/**
	 * virtual count_t getCount() const = 0;
	 */
	public native float getCount();

	/**
	 * virtual confidence_t getConfidence() const = 0;
	 */
	public native float getConfidence();

	/**
	 * virtual float toFloat() const = 0;
	 */
	public native float toFloat();

	/**
	 * virtual std::string toString() const = 0;
	 */
	public native String toString();

	/**
	 * virtual TruthValueType getType() const = 0;
	 */
	public native TruthValueType getType();

	/**
	 * Deep clone of this object. Returns a new object. .
	 */
	/**
	 * virtual TruthValue* clone() const = 0;
	 */
	public native TruthValue clone();

	/**
	 * Assignment operator. Must be implemented by each subclass to allow
	 * correct assignment, according with the exact class of the TruthValue
	 * objects in the left and right sides of the operator.
	 */
	/**
	 * virtual TruthValue& operator=(const TruthValue& rhs) = 0;
	 */
	public native TruthValue operatorAssign(TruthValue rhs);

	/**
	 * Equality. Used to determine if two truth values are the same, or not.
	 * Primarily useful see if a TV is equal to NULL_TV, TRUE_TV, FALSE_TV, etc.
	 */
	/**
	 * virtual bool operator==(const TruthValue& rhs) const = 0;
	 */
	public native boolean operatorEquals(TruthValue rhs);

	// VIRTUAL METHODS:

	/**
	 * Merge this TV object with the given TV object argument. It always returns
	 * a new TV object with the result of the merge, even if it is equals to one
	 * of the merged TV objects.
	 * 
	 * Currently tv1.merge(tv2) works as follows: If tv1 and tv2 are not
	 * CompositeTruthValue then the resulting TV is, either tv1 or tv2, the one
	 * with the highest confidence. If tv1 is a CompositeTruthValue see
	 * CompositeTruthValue::merge. If tv2 is a CompositeTruthValue but not tv1,
	 * then tv2.CompositeTruthValue::merge(tv1) is called.
	 */
	/**
	 * virtual TruthValue* merge(const TruthValue&) const;
	 */
	public native TruthValue merge(TruthValue rhs);

	/**
	 * Check if this TV is a null TV.
	 */
	/**
	 * virtual bool isNullTv() const;
	 */
	public native boolean isNullTv();

	/**
	 * Check if this TV is equal to the default TV. operator!= only compares
	 * pointers
	 */
	/**
	 * virtual bool isDefaultTV() const;
	 */
	public native boolean isDefaultTv();

	// STATIC METHODS:

	/**
	 * static const char* typeToStr(TruthValueType t) throw
	 * (InvalidParamException);
	 */
	public static native String typeToStr(TruthValueType t);

	/**
	 * static TruthValueType strToType(const char* str) throw
	 * (InvalidParamException);
	 */
	public static native TruthValueType strToType(String str);

	// Factories
	// former factory used by NMShell mkatom command
	/**
	 * static TruthValue* factory(const char*);
	 */
	public static native TruthValue factory(String str);

	/**
	 * static TruthValue* factory(TruthValueType, const char*) throw
	 * (InvalidParamException);
	 */
	public static native TruthValue factory(TruthValueType tvt, String str);
}
