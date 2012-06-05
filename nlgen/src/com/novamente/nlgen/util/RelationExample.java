/**
 * 
 */
package com.novamente.nlgen.util;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lianlian
 * 
 */
public class RelationExample {
	Set<String> unaryLabels = new HashSet<String>() {
		/**
		 */
		private static final long serialVersionUID = 1L;

		{
			add("pos");
			add("noun_number");
			add("tense");
			add("gender");
			add("person-FLAG");
			add("QUERY-TYPE");
			add("DEFINITE-FLAG");
			add("POLYWORD-FLAG");
			add("ORGANIZATION-FLAG");
			add("MONEY-FLAG");
			add("LOCATION-FLAG");
			add("IDIOM-FLAG");
			add("ENTITY-FLAG");
			add("EMOTICON-FLAG");
			add("DATE-FLAG");
			add("HYP");
			add("PRONOUN-FLAG");
			add("present");
			add(".a");
			add("adj");
			add("definite");
			add(".n");
			add("noun");
			add("singular");
			add(".v");
			add("verb");
			add("punctuation");
			add("det");
			add(".f");
			add("feminine");
			add(".g");
			add(".p");
			add(".r");
			add("prep");
			add("particle");
			add("imperative");
			add("hyp");
			add("idiom");
			add("conjunction");
			//XXX do not add person and pronoun because it may cause error in generation
		
		}
	};

	String label;

	boolean binary;

	String word1;

	String word2;

	public RelationExample(String word1, String word2, String label) {
		this.word1 = word1.trim();
		this.word2 = word2.trim();
		this.label = label.trim();
	}

	public RelationExample(String word1, String type, String label,
			boolean binary) {
		this.word1 = word1.trim();
		this.word2 = type.trim();
		this.label = label.trim();
		this.binary = binary;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isBinary() {
		if (unaryLabels.contains(this.label))
			return false;
		return true;
	}
	
	public boolean getBinary(){
		return binary;
	}

	public void setBinary(boolean binary) {
		this.binary = binary;
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

	public String toString() {
		String relationExample = "";
		relationExample += this.getLabel() + "(";
		relationExample += this.getWord1() + ",";
		relationExample += this.getWord2() + ")";
		return relationExample;
	}

}
