/**
 * 
 */
package com.novamente.nlgen.util;

/**
 * @author lianlian
 * 
 */
public class WordProperty {

	String label;
	String word;
	String type;

	public WordProperty(String label, String word, String type) {
		this.label = label;
		this.word = word;
		this.type = type;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
