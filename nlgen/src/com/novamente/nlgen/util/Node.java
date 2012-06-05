/**
 * 
 */
package com.novamente.nlgen.util;

import java.util.LinkedList;
import java.util.List;

/**
 * @author lianlian
 * 
 */
public class Node {

	WordNode wordNode;

	String semanticRole = "";

	List<Node> modifier;

	List<Node> object;// for the objects of the preposition-object phrase

	public Node(WordNode wordNode) {
		this.wordNode = wordNode;
		this.modifier = new LinkedList<Node>();
		this.object = new LinkedList<Node>();
	}

	public void setWordNode(WordNode wordNode) {
		this.wordNode = wordNode;
	}

	public void setSemanticRole(String label) {
		this.semanticRole = label;
	}

	public void setModifier(List<Node> modifier) {
		this.modifier = modifier;
	}

	public void setObject(List<Node> object) {
		this.object = object;
	}

	public WordNode getWordNode() {
		return wordNode;
	}

	public String getSemanticRole() {
		return semanticRole;
	}

	public List<Node> getModifier() {
		return modifier;
	}

	public List<Node> getObject() {
		return object;
	}
}
