package com.novamente.nlgen.util;

/**
 * @author lianlian this class defines the structure of link loaded from the
 *         corpus
 * 
 */
public class LinkNode {

	int leftLoc, rightLoc;
	String label, lLabel, rLabel;
	String lWord, rWord;
	String lPOS, rPOS;

	public String getLPOS() {
		return lPOS;
	}

	public void setLPOS(String lpos) {
		lPOS = lpos;
	}

	public String getRPOS() {
		return rPOS;
	}

	public void setRPOS(String rpos) {
		rPOS = rpos;
	}

	public LinkNode() {
		new LinkNode("", "", "");
	}

	public LinkNode(String lWord, String rWord, String label) {
		this.lWord = lWord;
		this.rWord = rWord;
		this.label = label;
	}

	public int getLeftLoc() {
		return leftLoc;
	}

	public void setLeftLoc(int leftLoc) {
		this.leftLoc = leftLoc;
	}

	public int getRightLoc() {
		return rightLoc;
	}

	public void setRightLoc(int rightLoc) {
		this.rightLoc = rightLoc;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLLabel() {
		return lLabel;
	}

	public void setLLabel(String label) {
		lLabel = label;
	}

	public String getRLabel() {
		return rLabel;
	}

	public void setRLabel(String label) {
		rLabel = label;
	}

	public String getLWord() {
		return lWord;
	}

	public void setLWord(String word) {
		lWord = word;
	}

	public String getRWord() {
		return rWord;
	}

	public void setRWord(String word) {
		rWord = word;
	}

	public int hashCode() {
		return 0;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof LinkNode))
			return false;
		LinkNode lns = (LinkNode) obj;
		return lns.label.equals(label) && lns.lLabel.equals(lLabel)
				&& lns.rLabel.equals(rLabel) && lns.lWord.equals(lWord)
				&& lns.rWord.equals(rWord) && lns.leftLoc == leftLoc
				&& lns.rightLoc == rightLoc;
	}

}
