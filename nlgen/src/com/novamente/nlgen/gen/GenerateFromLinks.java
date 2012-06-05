package com.novamente.nlgen.gen;

/**
 * @author lianlian
 *
 */
import java.util.*;

import com.novamente.nlgen.extract.Extractor;
import com.novamente.nlgen.util.*;

public class GenerateFromLinks {

	// generate the links from the matched result
	/**
	 * @param matchedLinks
	 *            :contains the links of the matched result
	 * @param mappedWords
	 *            : the corresponding wordNode with the matched words...
	 * @return
	 */
	public List<List<LinkNode>> generateLinks(
			List<List<LinkNode>> matchedLinks,
			HashMap<String, WordNode> mappedWords) {
		List<List<LinkNode>> links = new ArrayList<List<LinkNode>>();
		for (List<LinkNode> mlns : matchedLinks) {
			List<LinkNode> link = new ArrayList<LinkNode>();
			LinkNode temp = new LinkNode();
			for (LinkNode l : mlns) {
				String lWord = mappedWords.get(l.getLWord()).getWord();
				String rWord = mappedWords.get(l.getRWord()).getWord();
				temp = l;
				temp.setLWord(lWord);
				temp.setRLabel(rWord);
				link.add(temp);
			}
			links.add(link);
		}
		return links;
	}

	/**
	 * Generate the ordered words list
	 * 
	 * @param linkNodes
	 *            :linkNodes mapped from the matched syntactic relations
	 * @param nodes
	 *            :the map of wordString and wordNode
	 * @return the ordered words List (maybe more than one)
	 */
	public List<WordNode[]> getOrderedWordList(List<List<LinkNode>> linkNodes,
			HashMap<String, WordNode> nodes, HashMap<String, String> wordsMap) {
/*		for(Iterator<String> it=wordsMap.keySet().iterator(); it.hasNext();){
			String str=it.next();
			System.out.println(str+" "+wordsMap.get(str));
		}*/
		List<WordNode[]> nodeMaps = new ArrayList<WordNode[]>();
		for (List<LinkNode> lns : linkNodes) {
			TreeMap<Integer, WordNode> nodeMap = new TreeMap<Integer, WordNode>();
			for (LinkNode ln : lns) {
				WordNode nodeL=nodes.get(wordsMap.get(ln.getLWord()));
				WordNode nodeR=nodes.get(wordsMap.get(ln.getRWord()));
				if (nodeL == null && nodeR != null) {
//				System.out.println(ln.getLWord()+" "+ln.getRWord()+" 1 ");
					nodeMap.put(ln.getLeftLoc(), new WordNode(ln.getLWord()));
					nodeMap.put(ln.getRightLoc(), nodes.get(wordsMap.get(ln.getRWord())));
				} else if (nodeL != null && nodeR == null) {
//				System.out.println(ln.getLWord()+" "+ln.getRWord()+" 2 ");
					nodeMap.put(ln.getRightLoc(), new WordNode(ln.getRWord()));
					nodeMap.put(ln.getLeftLoc(), nodes.get(wordsMap.get(ln.getLWord())));
				} else if (nodeL != null && nodeR != null) {
//				System.out.println(ln.getLWord()+" "+ln.getRWord()+" 3 ");
					nodeMap.put(ln.getLeftLoc(), nodes.get(wordsMap.get(ln.getLWord())));
					nodeMap.put(ln.getRightLoc(), nodes.get(wordsMap.get(ln.getRWord())));
				} else {
//				System.out.println(ln.getLWord()+" "+ln.getRWord()+" 4 ");
					nodeMap.put(ln.getLeftLoc(), new WordNode(ln.getLWord()));
					nodeMap.put(ln.getRightLoc(), new WordNode(ln.getRWord()));
				}
			}
			WordNode[] nodeList = sort(nodeMap);
			nodeMaps.add(nodeList);
		}
		return nodeMaps;
	}

	public WordNode[] sort(TreeMap<Integer, WordNode> nodeMaps) {
		WordNode[] nodes = new WordNode[nodeMaps.size()];
		int count = 0;
		for (Iterator<Integer> it = nodeMaps.keySet().iterator(); it.hasNext();) {
			int i = it.next();
			nodes[count] = nodeMaps.get(i);
			count++;
		}
		return nodes;
	}

	public HashMap<String, WordNode> getWordNodes(
			HashMap<String, String> wordMap, List<RelationExample> rels) {
		HashMap<String, WordNode> wordNodes = new HashMap<String, WordNode>();
		Extractor ex = new Extractor();
		for (Iterator<String> it = wordMap.keySet().iterator(); it.hasNext();) {
			String str = it.next();
			WordNode wn = ex.extractWordNode(rels, wordMap.get(str));
			wordNodes.put(wordMap.get(str), wn);
		}
		return wordNodes;
	}

	// generate one sentence
	public String generateSentence(WordNode[] wordNodes) {
		String sen = "";
		int i = 0;
		for (WordNode node : wordNodes) {
			i++;
			if (node == null) {
	//			System.err.println(i + " null nodes...");
				sen += "";
			} else {
			//	System.out.println(i + " " + node.getWord());
				sen += node.getWord() + " ";
				// System.out.println(sen);
			}
		}
		sen=sen.replace("LEFT-WALL", "");
		sen=sen.replace("RIGHT-WALL", "");
		return sen;
	}

}
