package fi.neter.opencog.atomspace;

/**
 * From file: TruthValue.h
 * @author tero
 *
 */
public enum TruthValueType {

	/**
	 * enum TruthValueType {
    SIMPLE_TRUTH_VALUE = 1,
    COUNT_TRUTH_VALUE,
    INDEFINITE_TRUTH_VALUE,
    COMPOSITE_TRUTH_VALUE,
    NUMBER_OF_TRUTH_VALUE_TYPES
};

	 */
	SIMPLE_TRUTH_VALUE(1),
	COUNT_TRUTH_VALUE(2),
	INDEFINITE_TRUTH_VALUE(3),
	COMPOSITE_TRUTH_VALUE(4),
	NUMBER_OF_TRUTH_VALUE_TYPES(5);
	
	int value;
	
	private TruthValueType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
