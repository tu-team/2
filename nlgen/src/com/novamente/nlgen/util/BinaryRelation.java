/**
 * 
 */
package com.novamente.nlgen.util;

/**
 * @author lianlian
 *
 */
public class BinaryRelation {
	String label;

	String word1;

	String word2;

	public BinaryRelation(String word1, String word2, String label) {
		this.word1 = word1.trim();
		this.word2 = word2.trim();
		this.label = label.trim();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getWord1() {
		return word1;
	}

	public void setWord1(String word1) {
		this.word1 = word1.trim();
	}

	public String getWord2() {
		return word2;
	}

	public void setWord2(String word2) {
		this.word2 = word2.trim();
	}
}
