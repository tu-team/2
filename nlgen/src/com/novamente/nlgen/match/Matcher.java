/**
 * 
 */
package com.novamente.nlgen.match;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import com.novamente.nlgen.util.LinkNode;
import com.novamente.nlgen.util.RelationExample;

/**
 * @author lianlian
 * 
 */
public interface Matcher {

	public MatchedSubset semRelMatcher(String fileLIB, TreeMap<String, List<MatchedNode>> srcSem);
	
	public MatchedSubset semRelMatcher(List<TreeMap<String, List<MatchedNode>>> lsem,
			List<List<TreeMap<String, List<MatchedNode>>>> lib);
	
	public MatchedSubset semRelMatcher(TreeMap<String, List<MatchedNode>> srcSem,
			List<List<TreeMap<String, List<MatchedNode>>>> lib);
	
	
	public HashMap<String, String> getWordMap(TreeMap<String, List<MatchedNode>> src,
			TreeMap<String, List<MatchedNode>> des);
	//get the word mapping list
	public HashMap<String, String> getWordMap(List<RelationExample> relExRel1, List<RelationExample> relExRel2);
	
	//get the corresponding links of the best matched sentence
	public List<List<LinkNode>> getCorrespondingLinks(MatchedSubset ms, String linksFileName);
	
	//get the matched result, which contains the syntactic links and mapping of words
	public MatchedResult getMatchedResult(List<RelationExample> relExRels,
			String relExFileName, String linksFileName);

}
