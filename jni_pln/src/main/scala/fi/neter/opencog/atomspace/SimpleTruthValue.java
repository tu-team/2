package fi.neter.opencog.atomspace;

/**
 * From file: SimpleTruthValue.h
 * @author tero
 *
 */
public class SimpleTruthValue extends TruthValue {
	/**
	 * Pointer to the C++ object. Note: 64-bit only.
	 */
	public long handle;

    /**
     * SimpleTruthValue(strength_t mean, count_t count);
     */
	native public static SimpleTruthValue construct(float mean, float count);
	
    /**
     * SimpleTruthValue(const TruthValue&);
     */
    /**
     * SimpleTruthValue(SimpleTruthValue const&);
     */

    /**
     * SimpleTruthValue* clone() const;
     */
    /**
     * SimpleTruthValue& operator=(const TruthValue& rhs)
     * throw (RuntimeException);
     */
    
    /**
     * virtual bool operator==(const TruthValue& rhs) const;
     */

    /**
     * static SimpleTruthValue* fromString(const char*);
     * @return
     */

    /// Heuristic to compute the count given the confidence (according
    /// to the PLN book)
    /// count =  confidence * k / (1 - confidence)
    /// where k is the look-ahead
    /**
     * static count_t confidenceToCount(confidence_t);
     * @return
     */

    /// Heuristic to compute the confidence given the count (according
    /// to the PLN book)
    /// confidence = count / (count + k)
    /// where k is the look-ahead
    /**
     * static confidence_t countToConfidence(count_t);
     * @return
     */

    /**
     * float toFloat() const;
     */
    /**
     * std::string toString() const;
     * @return
     */
    /**
     * TruthValueType getType() const;
     * @return
     */
	public native TruthValueType getType();

    /**
     * strength_t getMean() const;
     * @return
     */
	public native float getMean();
    /**
     * count_t getCount() const;
     * @return
     */
	public native float getCount();
	
    /**
     * confidence_t getConfidence() const;
     */
	public native float getConfidence();
    /**
     * void setMean(strength_t);
     */
	public native void setMean(float mean);
    /**
     * void setCount(count_t);
     */
	public native void setCount(float count);
	
    /**
     * void setConfidence(confidence_t);
     */
	public native void setConfidence(float confidence);

}
