package com.novamente.nlgen.unary.processor;


import com.novamente.nlgen.util.RelationExample;
import com.novamente.nlgen.util.WordNode;
import com.novamente.nlgen.unary.AbstractAlgorithm;

public class Definite extends AbstractAlgorithm {

	@Override
	public WordNode apply(WordNode node, WordNode preNode) {
		for(RelationExample temp: node.getUnaryRelations()){
			if(temp.getLabel().equals("DEFINITE-FLAG") && !Character.isUpperCase(temp.getWord1().charAt(0))){
				if (/*preNode!=null && */preNode.pos.equals("adj") && node.pos.equals("noun")){
					node.setWord("the "+preNode.getWord()+" "+node.getWord());
				}else{
					node.setWord("the "+node.getWord());
				}
				
			}
		}
/*		int index = NounPhrase.getIndexOfNP(node);
		node.getSentence().insertNode(index, new WordNode("the"));*/
		return node;
	}


}
