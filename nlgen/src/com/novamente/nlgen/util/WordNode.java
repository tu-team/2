/**
 * 
 */
package com.novamente.nlgen.util;

import java.util.List;

/**
 * @author lianlian
 * 
 */
public class WordNode {

	public String originalWord;

	public String word;

	public String pos;

	public boolean unaryProcessed;

	public List<RelationExample> unaryRelations;

	public List<RelationExample> binaryRelations;

	public boolean isUnaryProcessed() {
		return unaryProcessed;
	}

	public void setUnaryProcessed(boolean unaryProcessed) {
		this.unaryProcessed = unaryProcessed;
	}

	public WordNode() {

	}

	public WordNode(String word) {
		this.setOriginWord(word);
	}

	public String getOriginWord() {
		return originalWord;
	}

	public void setOriginWord(String originWord) {
		this.originalWord = originWord;
		this.word = originWord;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getPOS() {
		return pos;
	}

	public boolean containsRelation(String label) {
		return containsUnary(label) || containsBinary(label);
	}

	public boolean containsUnary(String label) {
		for (RelationExample re : unaryRelations) {
			if (re.getLabel().equals("pos") || re.getLabel().equals("tense")) {
				if (re.getWord2().equals(label)) {
					return true;
				}
			} else if (re.getLabel().equals(label)) {
				return true;
			}
		}
		return false;
	}

	public boolean containsBinary(String label) {
		for (RelationExample re : binaryRelations) {
			if (re.getLabel().equals(label)) {
				return true;
			}
		}
		return false;
	}

	public Object clone() {
		WordNode word = new WordNode(originalWord);
		word.getUnaryRelations().addAll(unaryRelations);
		word.getBinaryRelations().addAll(binaryRelations);
		return word;
	}

	public List<RelationExample> getUnaryRelations() {
		return unaryRelations;
	}

	public void setUnaryRelations(List<RelationExample> unaryRelations) {
		this.unaryRelations = unaryRelations;
	}

	public List<RelationExample> getBinaryRelations() {
		return binaryRelations;
	}

	public void setBinaryRelations(List<RelationExample> binaryRelations) {
		this.binaryRelations = binaryRelations;
	}

	public String toString() {
		String str = getWord() + " " + getPOS() + "\n";
		// System.out.println(unaryRelations.size());
		for (RelationExample temp : unaryRelations) {
			str += temp.toString() + "\n";
		}
		for (RelationExample temp : binaryRelations) {
			str += temp.toString() + "\n";
		}
		return str;
	}

	public void removeNumber() {
		if (originalWord.matches(".*_\\d+")) {
			word = originalWord.substring(0, originalWord.lastIndexOf("_"));
		}
	}

}
