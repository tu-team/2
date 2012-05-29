package com.novamente.nlgen.unary.processor;


import com.novamente.nlgen.util.WordNode;
import com.novamente.nlgen.unary.AbstractAlgorithm;

public class PastInflector extends AbstractAlgorithm {

	@Override
	public WordNode apply(WordNode node,WordNode preNode) {
		// TODO Auto-generated method stub
		if (IrregularVerb.isIrregular(node.getWord())) {
			node.setWord(IrregularVerb.getPast(node.getWord()));
			return node;
		} else {
			node.setWord(regularPast(node.getWord()));
			return node;
		}
	}

	private String regularPast(String word) {
		word = word.trim();
		if (word.matches(".*_\\d+")) {
			word = word.substring(0, word.lastIndexOf("_"));
			return word;
		}
		if (word.matches(".*[^aeiou]y")) {
			word = word.substring(0, word.length() - 1) + "ied";
			return word;
		}
		if (word.matches(".*[aeiou][^aeiouy]")) {
			word = word + word.charAt(word.length() - 1) + "ed";
			return word;
		}
		if (word.endsWith("e")) {
			word = word + "d";
			return word;
		}
		word = word + "ed";
		return word;
	}
}
