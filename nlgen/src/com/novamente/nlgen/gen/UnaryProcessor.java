package com.novamente.nlgen.gen;

import java.util.List;

import com.novamente.nlgen.unary.UnaryConvertAlgorithmApplier;
import com.novamente.nlgen.util.RelationExample;
import com.novamente.nlgen.util.WordNode;

public class UnaryProcessor {
	UnaryConvertAlgorithmApplier uaa = new UnaryConvertAlgorithmApplier(
			"data\\unary-convert-algs.xml");
	
	public void unaryApply(List<WordNode> senNodes) {
		WordNode preNode = null;
		WordNode node;
		boolean spe=false;
		for (int i = 0; i < senNodes.size(); i++) {
			node = senNodes.get(i);
			if (i > 0) {
				preNode = senNodes.get(i - 1);
				for(RelationExample rel: node.getUnaryRelations()){
					if(rel.getLabel().contains("DEFINITE-FLAG") && !(rel.getWord1().charAt(0)>65 &&rel.getWord1().charAt(0)<90) )
						spe=true;
				}
				if (preNode.pos.equals("adj") && node.pos.equals("noun") && spe) {
					uaa.apply(node, preNode);
					senNodes.remove(i - 1);
				}else{
					uaa.apply(node, preNode);
				}
			} else
				uaa.apply(node, preNode);
			spe=false;
		}
	}
	
}
