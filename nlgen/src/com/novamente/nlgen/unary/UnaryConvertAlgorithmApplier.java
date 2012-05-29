package com.novamente.nlgen.unary;

import java.util.List;

import com.novamente.nlgen.util.WordNode;



public class UnaryConvertAlgorithmApplier {
	List<AbstractAlgorithm> algs;
	String unaryAlgsPath;

	public UnaryConvertAlgorithmApplier(String unaryAlgsPath) {
		this.unaryAlgsPath = unaryAlgsPath;
		init();
	}

	private void init() {
		algs = UnaryConvertAlgorithmBuilder
				.parseAlgorithmFromFile(unaryAlgsPath);
	}

	public WordNode apply(WordNode word,WordNode preNode) {
		if(!word.isUnaryProcessed()){
			return word;
		}
		WordNode node = word;
		for (AbstractAlgorithm alg : algs) {
			if (alg.canApply(word)) {
				node=alg.apply(node, preNode);
			}
		}
		node.setUnaryProcessed(true);
		return node;
	}
}
