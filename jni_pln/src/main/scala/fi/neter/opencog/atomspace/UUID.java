package fi.neter.opencog.atomspace;

import java.math.BigInteger;
import java.util.Random;

/**
 * // UUID == Universally Unique Identifier
 * typedef unsigned long UUID;
 * @author tero
 * from Handle.h
 *
 */
public class UUID extends BigInteger {

	public UUID(byte[] val) {
		super(val);
	}

	public UUID(int signum, byte[] magnitude) {
		super(signum, magnitude);
	}

	public UUID(int bitLength, int certainty, Random rnd) {
		super(bitLength, certainty, rnd);
	}

	public UUID(int numBits, Random rnd) {
		super(numBits, rnd);
	}

	public UUID(String val, int radix) {
		super(val, radix);
	}

	public UUID(String val) {
		super(val);
	}

}
