package com.novamente.nlgen.match;

import com.novamente.nlgen.util.RelationExample;

public class MatchedNode {
	private RelationExample re;

	/*
	 * 0 not visited; 1 word1 visited; 2 word2 visited
	 */
	private int wordMark;
	private int senID;
	private int semIndex;


	public MatchedNode(String word1, String word2, String label) {
		re = new RelationExample(word1, word2, label);
		wordMark = 0;
	}

	public void setMark(int m) {
		wordMark = m;
	}

	public int getMark() {
		return wordMark;
	}

	public RelationExample getReEx() {
		return re;
	}

	public int getSenID() {
		return senID;
	}

	public int getSemIndex() {
		return semIndex;
	}

	public void setSenID(int i) {
		senID = i;
	}

	public void setSemIndex(int i) {
		semIndex = i;
	}
}
