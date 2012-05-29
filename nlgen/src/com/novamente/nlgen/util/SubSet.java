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

public class SubSet {

	Node root;

	List<Node> mainRole;

	public SubSet() {
		this.mainRole = new LinkedList<Node>();
	}

	public SubSet(Node root) {
		this.root = root;
		this.mainRole = new LinkedList<Node>();
	}

	public SubSet(Node root, List<Node> mainRole) {
		this.root = root;
		this.mainRole = mainRole;
	}

	public void setRoot(Node node) {
		this.root = node;
	}

	public void setMainRole(List<Node> mainRole) {
		this.mainRole = mainRole;
	}

	public Node getRoot() {
		return root;
	}

	public List<Node> getMainRole() {
		return mainRole;
	}

	public String toString() {
		String str = "(";
		for (Node temp : root.modifier) {
			str += temp.semanticRole + "(" + temp.wordNode.originalWord + ")";
		}
		str += ") ";
		str += root.semanticRole + root.wordNode.originalWord + "(";
		for (Node temp : mainRole) {
			str += " "+temp.semanticRole + "(" + temp.wordNode.originalWord;
			if (!temp.semanticRole.equals("PP")) {
				for (Node temp1 : temp.modifier) {
					str += "(" + temp1.semanticRole + "("
							+ temp1.wordNode.originalWord + "))";
				}
				str += ")";
			} else {
				for (Node temp2 : temp.object) {
					str += "(" + temp2.semanticRole + "("
							+ temp2.wordNode.originalWord;
					for (Node temp3 : temp2.modifier) {
						str += "(" + temp3.semanticRole + "("
								+ temp3.wordNode.originalWord + "))";
					}
					str += ")";
				}
				str += "))";
			}
		}
		str += ")";
		return str;
	}

}
