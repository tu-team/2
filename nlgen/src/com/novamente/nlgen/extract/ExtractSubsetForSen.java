/**
 * 
 */
package com.novamente.nlgen.extract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.novamente.nlgen.util.*;
import com.novamente.nlgen.io.*;

/**
 * @author lianlian;
 * 
 */
public class ExtractSubsetForSen {

	public HashMap<String, WordNode> allWordNodes = new HashMap<String, WordNode>();

	public HashMap<String, Node> allNodes = new HashMap<String, Node>();

	// Define the Node for every word
	public Node extractNode(WordNode wordnode) {
		Node node = new Node(wordnode);
		for (RelationExample relationExample : wordnode.getBinaryRelations()) {
			if (relationExample.getWord2().equals(wordnode.word)) {
				if (relationExample.getLabel().contains("mod")) {
					// add the modifiers
					WordNode temp1 = allWordNodes.get(relationExample
							.getWord1());
					node.getModifier().add(extractNode(temp1));
				} else if (relationExample.getLabel().contains("_")) {
					node.setSemanticRole(relationExample.getLabel());
				}
			} else if (relationExample.getLabel().equals(wordnode.word)) {
				// prepositional phrase
				if (allWordNodes.containsKey(relationExample.getWord2())) {
					WordNode temp2 = allWordNodes.get(relationExample
							.getWord2());
					node.setSemanticRole("PP");
					node.getObject().add(extractNode(temp2));
				} else {
					System.err
							.println("No related WordNode can be found in the hashtable...");
				}

			}
		}
		return node;
	}

	// extract the verb Node
	public Node extractPredicateNode(WordNode verbNode) {
		Node predicateNode = new Node(verbNode);
		return predicateNode;
	}

	// extract the subset for the sentence
	public SubSet extractSubset(Node predicateNode) {
		SubSet subset = new SubSet(predicateNode);
		WordNode verbNode = predicateNode.getWordNode();
		for (RelationExample relationExample : verbNode.binaryRelations) {
			if (relationExample.getWord1().equals(verbNode.getWord())) {
				if (relationExample.getLabel().contains("_")) {
					subset.getMainRole().add(
							extractNode(allWordNodes.get(relationExample
									.getWord2())));
				} else {
					// prepositional phrase
					subset.getMainRole().add(
							extractNode(allWordNodes.get(relationExample
									.getLabel())));
				}
			}
		}
		return subset;
	}

	public void initialWordNode(Vector<String> allWords,
			Vector<String> allRelations) {
		ExtractRelationForWord wordExtractor = new ExtractRelationForWord();
		for (String word : allWords) {
			Vector<String> relations = wordExtractor
					.extract(allRelations, word);
			Vector<RelationExample> formalization = wordExtractor
					.relationFormalization(relations);
			WordNode temp = wordExtractor.extractWordNode(formalization, word);
			allWordNodes.put(word, temp);
		}
	}

	// Just for test
	public static void main(String[] args) {
		ExtractRelationForWord test = new ExtractRelationForWord();
		ExtractSubsetForSen test1 = new ExtractSubsetForSen();
		Vector<String> allRelations = FileTool.readLine("data/testData/7~",
				"GBK");
		Vector<String> allWords = test.extractWord(allRelations);
		List<Node> predicateNodes = new ArrayList<Node>();
		Node predicateNode = null;
		test1.initialWordNode(allWords, allRelations);
		for (String word : allWords) {
			Vector<String> relations = test.extract(allRelations, word);
			Vector<RelationExample> formalization = test
					.relationFormalization(relations);
			WordNode temp = test.extractWordNode(formalization, word);
			// System.out.println(temp.word);
			// System.out.println(temp.getBinaryRelations().size());
			test1.allNodes.put(word, test1.extractNode(temp));
			if (temp.pos.equals("verb")) {
				predicateNode = test1.extractNode(temp);
				predicateNodes.add(predicateNode);
			}
		}
		if (predicateNode == null) {
			System.err.println("It is not a well-formed sentence...");
		} else {
			for (Node predicate : predicateNodes) {
				SubSet subset = test1.extractSubset(predicate);
				System.out.println(subset.toString());
			}

		}

	}
}
