/**
 * 
 */
package com.novamente.nlgen.extract;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Vector;
import java.util.List;

import com.novamente.nlgen.io.FileTool;

import com.novamente.nlgen.util.RelationExample;
import com.novamente.nlgen.util.WordNode;

/**
 * @author lianlian
 * 
 * input: the output file of RelEx;
 * 
 * output: classify the relations for every word in the sentence;
 * 
 */
public class ExtractRelationForWord {

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
			add("DEFINITE-FLAG");
			add("PRONOUN-FLAG");
			add("IDIOM-FLAG");
		}
	};

	public Vector<String> loadRelFile(String fileName, String fileCode) {
		return FileTool.readLine(fileName, fileCode);
	}

	// extract the related relations for a definite word
	public Vector<String> extract(Vector<String> allRelations, String word) {
		Vector<String> wordRelation = new Vector<String>();
		for (String str : allRelations) {
			if (str.contains(word)) {
				wordRelation.add(str);
			}
		}
		return wordRelation;
	}

	// change the relation from String type to RelationExample type
	public Vector<RelationExample> relationFormalization(
			Vector<String> relations) {
		Vector<RelationExample> rel = new Vector<RelationExample>();
		for (String str : relations) {
			int i = str.indexOf("(");
			int j = str.indexOf(",");
			int k = str.indexOf(")");
			if (!Need2Change.contains(str.substring(0, str.indexOf('(')))) {
				RelationExample temp = new RelationExample(str.substring(i + 1,
						j), str.substring(j + 1, k), str.substring(0, i),true);
				rel.add(temp);
			} else {
				RelationExample temp = new RelationExample(str.substring(i + 1,
						j), str.substring(j + 1, k), str.substring(0, i), false);
				rel.add(temp);
			}
		}
		return rel;
	}

	// Define the wordNode for every word
	public WordNode extractWordNode(Vector<RelationExample> relations,
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
			//skip the pos relation
			wordNode.unaryProcessed = true;
		}
		wordNode.binaryRelations=binaryRelations;
		wordNode.unaryRelations=unaryRelations;
		return wordNode;
	}

	// extract all the words which form a sentence
	public Vector<String> extractWord(Vector<String> allRelations) {
		Vector<String> allWords = new Vector<String>();
		for (String str : allRelations) {
			if (str.startsWith("pos")&& !str.contains("punctuation")) {
				allWords.add(str.substring(str.indexOf("(")+1, str.indexOf(",")));
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

	// change the binary form of some relations to the unary form: such as
	// "pos","tense",etc.
	public static Vector<String> changeForm(Vector<String> original) {
		Vector<String> newForm = new Vector<String>();
		for (String temp : original) {
			if (!temp.equals("")) {
				if (Need2Change.contains(temp.substring(0, temp.indexOf('(')))) {
					temp = temp.replace('(', ' ');
					temp = temp.replace(')', ' ');
					temp = temp.replace(",", "");
					String[] tempArray = temp.split(" ");
					if (tempArray.length == 4) {
						/*
						 * System.err.println("BAD RELATION");
						 * System.err.println(temp);
						 */
						temp = tempArray[3] + "(" + tempArray[0] + tempArray[2]
								+ ")";
					} else {
						temp = tempArray[2] + "(" + tempArray[1] + ")";
					}
				} else if (temp.contains("-FLAG")) {
					temp = temp.replace("-FLAG", "");
					temp = temp.substring(0, temp.indexOf("(")).toLowerCase()
							+ temp.substring(temp.indexOf("("), temp
									.indexOf(",")) + ")";
				}
				newForm.add(temp);
			} else {
				newForm.add(temp);
			}
		}
		return newForm;
	}
	
	
	//Just for test
	public static void main(String [] args){
		ExtractRelationForWord test=new ExtractRelationForWord();
		Vector<String> allRelations=FileTool.readLine("data/testData/1~", "GBK");
		Vector<String> allWords=test.extractWord(allRelations);
		for(String word: allWords){
			Vector<String> relations=test.extract(allRelations, word);
			Vector<RelationExample> formalization=test.relationFormalization(relations);
			WordNode temp=test.extractWordNode(formalization, word);
			System.out.print(temp.toString());
			System.out.println("************\n");
		}
	}

}
