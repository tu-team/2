/**
 * 
 */
package com.novamente.nlgen.match;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.novamente.nlgen.util.LinkNode;

/**
 * @author lianlian
 * 
 */
public class MatchedResult {

	List<List<LinkNode>> matchedLinks;
	HashMap<String, String> wordMap;
	
	public MatchedResult(){
		matchedLinks=new ArrayList<List<LinkNode>>();
		wordMap=new HashMap<String, String>();
	}
	
	public MatchedResult(List<List<LinkNode>> matchedLinks, HashMap<String, String> wordMap){
		this.matchedLinks=matchedLinks;
		this.wordMap=wordMap;
	}

	public List<List<LinkNode>> getMatchedLinks() {
		return matchedLinks;
	}

	public void setMatchedLinks(List<List<LinkNode>> matchedLinks) {
		this.matchedLinks = matchedLinks;
	}

	public HashMap<String, String> getWordMap() {
		return wordMap;
	}

	public void setWordMap(HashMap<String, String> wordMap) {
		this.wordMap = wordMap;
	}

}
