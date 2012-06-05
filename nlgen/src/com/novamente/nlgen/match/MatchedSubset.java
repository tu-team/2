package com.novamente.nlgen.match;

import java.util.*;

public class MatchedSubset {
	private int sentenceID;
	private int semanticRelIndex;
	private TreeMap<String, List<MatchedNode>> sem;

	public int getSenID() {
		return sentenceID;
	}

	public int getSemIndex() {
		return semanticRelIndex;
	}

	public void setSenID(int i) {
		sentenceID = i;
	}

	public void setSemIndex(int i) {
		semanticRelIndex = i;
	}

	public void setSem(TreeMap<String, List<MatchedNode>> tm) {
		sem = tm;
	}

	public TreeMap<String, List<MatchedNode>> getSem() {
		return sem;
	}
}
