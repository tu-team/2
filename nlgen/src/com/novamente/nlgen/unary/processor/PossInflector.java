package com.novamente.nlgen.unary.processor;

import java.util.HashMap;
import java.util.Map;

import com.novamente.nlgen.util.WordNode;
import com.novamente.nlgen.util.RelationExample;
import com.novamente.nlgen.unary.AbstractAlgorithm;

public class PossInflector extends AbstractAlgorithm {
	private static final Map<String,String> pronouns=new HashMap<String,String>();
	static{
		pronouns.put("me", "my");
//		pronouns.put("you", "yours");
		pronouns.put("you", "your");
		pronouns.put("him", "his");
		pronouns.put("her", "her");
		pronouns.put("them", "their");
//		pronouns.put("its", "its");
		pronouns.put("it", "its");
	}
	@Override
	public WordNode apply(WordNode node,WordNode preNode) {
		// TODO Auto-generated method stub
		for(RelationExample re:node.getBinaryRelations()){
			if(re.getLabel().equals("_poss")&&re.getWord2().equals(node.getOriginWord())){
				if(pronouns.containsKey(node.getWord())){
					node.setWord(pronouns.get(node.getWord()));
				}else{
					if(node.getWord().endsWith("s")){
						node.setWord(node.getWord()+"'");
					}else{
						node.setWord(node.getWord()+"'s");
					}
				}
			}
		}
		return node;
	}

}
