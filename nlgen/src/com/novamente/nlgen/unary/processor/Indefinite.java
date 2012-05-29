package com.novamente.nlgen.unary.processor;




import com.novamente.nlgen.util.WordNode;
import com.novamente.nlgen.unary.AbstractAlgorithm;

public class Indefinite extends AbstractAlgorithm {
	@Override
	public WordNode apply(WordNode node,WordNode preNode) {
		// TODO Auto-generated method stub
		if(node.getOriginWord().matches("[aeiou].*")){
			node.setWord("an "+node.getOriginWord());
		}else{
			node.setWord("a "+node.getOriginWord());
		}
		return node;
	}
}