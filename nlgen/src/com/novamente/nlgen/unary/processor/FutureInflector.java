package com.novamente.nlgen.unary.processor;

import com.novamente.nlgen.util.WordNode;
import com.novamente.nlgen.unary.AbstractAlgorithm;

public class FutureInflector extends AbstractAlgorithm {

	@Override
	public WordNode apply(WordNode node,WordNode preNode) {
		/*node.getSentence().insertNode(node.getSentence().getIndex(node), new WordNode("will"));*/
		node.setWord("will "+node.getWord());
		return node;
	}

}
