package com.novamente.nlgen.unary.processor;

import java.util.Locale;

import com.novamente.nlgen.util.WordNode;
import com.novamente.nlgen.unary.AbstractAlgorithm;
public class NounPluralizer extends AbstractAlgorithm {

	@Override
	public WordNode apply(WordNode node,WordNode preNode) {
		// TODO Auto-generated method stub
		String word=node.getWord();
		word=org.jvnet.inflector.Noun.pluralOf(word,Locale.ENGLISH);
		node.setWord(word);
		return node;
	}

}
