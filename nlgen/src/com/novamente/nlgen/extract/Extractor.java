/**
 * 
 */
package com.novamente.nlgen.extract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import com.novamente.nlgen.util.Node;
import com.novamente.nlgen.util.RelationExample;
import com.novamente.nlgen.util.SubSet;
import com.novamente.nlgen.util.WordNode;
import com.novamente.nlgen.io.FileTool;

/**
 * @author Administrator
 * 
 */
public class Extractor {

	public HashMap<String, WordNode> allWordNodes = new HashMap<String, WordNode>();

	public HashMap<String, Node> allNodes = new HashMap<String, Node>();

	public static Set<String> Need2Change = new HashSet<String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			add("pos");
			add("noun_number");
			add("tense");
			add("gender");
			add("person-FLAG");
			add("QUERY-TYPE");
			add("DEFINITE-FLAG");
			add("POLYWORD-FLAG");
			add("ORGANIZATION-FLAG");
			add("MONEY-FLAG");
			add("LOCATION-FLAG");
			add("IDIOM-FLAG");
			add("ENTITY-FLAG");
			add("EMOTICON-FLAG");
			add("DATE-FLAG");
			add("HYP");
		}
	};

	public Vector<String> loadRelFile(String fileName, String fileCode) {
		return FileTool.readLine(fileName, fileCode);
	}

	// extract the related relations for a definite word
	public List<String> extract(Vector<String> allRelations, String word) {
		List<String> wordRelation = new ArrayList<String>();
		for (String str : allRelations) {
			if (str.contains(word)) {
				wordRelation.add(str);
			}
		}
		return wordRelation;
	}

	// change the relation from String type to RelationExample type
	public List<RelationExample> relationFormalization(
			List<String> relations) {
		List<RelationExample> rel = new ArrayList<RelationExample>();
		for (String str : relations) {
			int i = str.indexOf("(");
			int j = str.indexOf(",");
			int k = str.indexOf(")");
			if (!Need2Change.contains(str.substring(0, str.indexOf('(')))) {
				RelationExample temp = new RelationExample(str.substring(i + 1,
						j).trim(), str.substring(j + 1, k).trim(), str
						.substring(0, i).trim(), true);
				rel.add(temp);
			} else {
				RelationExample temp = new RelationExample(str.substring(i + 1,
						j).trim(), str.substring(j + 1, k).trim(), str
						.substring(0, i).trim(), false);
				rel.add(temp);
			}
		}
		return rel;
	}

	// Define the wordNode for every word
	public WordNode extractWordNode(List<RelationExample> relations,
			String word) {
		WordNode wordNode = new WordNode(word);
		List<RelationExample> unaryRelations = new LinkedList<RelationExample>();
		List<RelationExample> binaryRelations = new LinkedList<RelationExample>();
		for (RelationExample temp : relations) {
			if (temp.isBinary() == false) {
				unaryRelations.add(temp);
				if (temp.getLabel().equals("pos")) {
					wordNode.pos = temp.getWord2();
				}
			} else {
				binaryRelations.add(temp);
			}
		}
		if (unaryRelations.size() > 1) {
			// skip the pos relation
			wordNode.unaryProcessed = true;
		}
		wordNode.binaryRelations = binaryRelations;
		wordNode.unaryRelations = unaryRelations;
		return wordNode;
	}

	// extract all the words which form a sentence
	public Vector<String> extractWord(Vector<String> allRelations) {
		Vector<String> allWords = new Vector<String>();
		for (String str : allRelations) {
			if (str.startsWith("pos") && !str.contains("punctuation")) {
				allWords.add(str.substring(str.indexOf("(") + 1, str
						.indexOf(",")));
			}
		}
		return allWords;
	}

	// extract the predicate of a sentence
	public String extractVerb(Vector<String> allRelations) {
		for (String str : allRelations) {
			if (str.startsWith("_subj")) {
				return str.substring(str.indexOf("("), str.indexOf(","));
			}
		}
		return null;
	}

	// Define the Node for every word
	public Node extractNode(WordNode wordnode) {
		Node node = new Node(wordnode);
		for (RelationExample relationExample : wordnode.getBinaryRelations()) {
			if (relationExample.getWord2().equals(wordnode.word)) {
				if (relationExample.getLabel().contains("mod")) {
					// add the modifiers
					WordNode temp1 = allWordNodes.get(relationExample
							.getWord1());
					Node tempNode = extractNode(temp1);
					tempNode.setSemanticRole(relationExample.getLabel());
					node.getModifier().add(tempNode);
				} else if (relationExample.getLabel().contains("_")) {
					node.setSemanticRole(relationExample.getLabel());
				}
			} else if (relationExample.getLabel().equals(wordnode.word)) {
				// prepositional phrase
				WordNode temp2 = allWordNodes.get(relationExample.getWord2()
						.trim());

				node.setSemanticRole("PP");
				node.getObject().add(extractNode(temp2));
			}
		}
		return node;
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

	// initial the hashmap which contains all the word nodes
	public void initialWordNode(Vector<String> allWords,
			Vector<String> allRelations) {
		for (String word : allWords) {
			List<String> relations = extract(allRelations, word);
			List<RelationExample> formalization = relationFormalization(relations);
			WordNode temp = extractWordNode(formalization, word);
			allWordNodes.put(word.trim(), temp);
		}
	}

	public Vector<Node> extractPredicate() {
		Vector<Node> predicates = new Vector<Node>();
		for (String temp : allWordNodes.keySet()) {
			WordNode wn = allWordNodes.get(temp);
			if (wn.getPOS().equals("verb") && wn.getBinaryRelations().size()>0) {
				predicates.add(extractNode(wn));
			}
		}
		return predicates;
	}

/*	*//**
	 * @param args
	 * For test
	 */
	public static void main(String[] args) {
		Extractor extractor = new Extractor();
		Vector<String> allRelations = FileTool.readLine("data\\11", "GBK");
		Vector<String> allWords = extractor.extractWord(allRelations);
		extractor.initialWordNode(allWords, allRelations);
		Vector<Node> predicates = extractor.extractPredicate();
		for (String word : allWords) {
			List<String> relations = extractor.extract(allRelations, word);
			List<RelationExample> formalization = extractor
					.relationFormalization(relations);
			WordNode temp = extractor.extractWordNode(formalization, word);
			// System.out.println(temp.word);
			extractor.allNodes.put(word, extractor.extractNode(temp));
		}
		if (predicates.size() == 0) {
			System.err.println("It is not a well-formed sentence...");
		} else {
			for (Node predicateNode : predicates) {
				// System.out.println(predicateNode.getWordNode().getWord());
				SubSet subset = extractor.extractSubset(predicateNode);
				System.out.println(subset.toString());
			}
		}
	}

}
