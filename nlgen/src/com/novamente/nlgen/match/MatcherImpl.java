package com.novamente.nlgen.match;

import java.util.*;

import com.novamente.nlgen.util.LinkNode;
import com.novamente.nlgen.util.RelationExample;

class MarkCount {
	private int word1Count = 0;
	private int word2Count = 0;

	public int GetWord1Count() {
		return word1Count;
	}

	public void SetWord1Count(int wc) {
		word1Count = wc;
	}

	public int GetWord2Count() {
		return word2Count;
	}

	public void SetWord2Coutn(int wc) {
		word2Count = wc;
	}
}

class FindMark {
	private int total = 0;
	private int word1 = 0;
	private int word2 = 0;

	/*
	 * 0 for total 1 for word1 2 for word2
	 */
	public int GetValue(int flag) {
		if (flag == 0) {
			return total;
		}
		if (flag == 1) {
			return word1;
		}
		if (flag == 2) {
			return word2;
		}
		return -1;
	}

	public void SetValue(int flag, int value) {
		if (flag == 0) {
			total = value;
		}
		if (flag == 1) {
			word1 = value;
		}
		if (flag == 2) {
			word2 = value;
		}
	}
}

public class MatcherImpl extends MatcherTool implements Matcher {

	// get the matched result, which contains the syntactic links and mapping of
	// words
	// "relExRels" is the parameter of the input semantic relations
	public MatchedResult getMatchedResult(List<RelationExample> relExRels,
			String relExFileName, String linksFileName) {
		MatchedResult mr = new MatchedResult();
		List<RelationExample> bRels = getBinaryRels(relExRels);
		TreeMap<String, List<MatchedNode>> relMaps = makeMap(bRels, 0, 0);
		List<List<TreeMap<String, List<MatchedNode>>>> lib = loadRelexXML(relExFileName);
		MatchedSubset result = semRelMatcher(relMaps, lib);
		if (result != null) {
			List<List<LinkNode>> links = this.getCorrespondingLinks(result,
					linksFileName);
			HashMap<String, String> wordMap = this.getWordMap(result.getSem(),
					relMaps);
			mr = new MatchedResult(links, wordMap);
			return mr;
		} else
			return null;
	}

	public MatchedSubset semRelMatcher(String fileLIB,
			TreeMap<String, List<MatchedNode>> srcSem) {
		MatcherTool xt = new MatcherTool();
		List<List<TreeMap<String, List<MatchedNode>>>> listLIB = xt
				.loadRelexXML(fileLIB);
		for (List<TreeMap<String, List<MatchedNode>>> mSentence : listLIB) {
			for (TreeMap<String, List<MatchedNode>> tmOneSem : mSentence) {
				if (isMatchedSem(srcSem, tmOneSem)) {
					System.out.println("matching succeeds!");
					return getMatchedSubset(tmOneSem);
				}
			}
		}
		System.out.println("matching fails!");
		return null;
	}

	public MatchedSubset semRelMatcher(
			List<TreeMap<String, List<MatchedNode>>> lsem,
			List<List<TreeMap<String, List<MatchedNode>>>> lib) {
		MatcherTool xt = new MatcherTool();
		for (TreeMap<String, List<MatchedNode>> sem : lsem) {
			xt.printMAP(sem);
			MatchedSubset re = semRelMatcher(sem, lib);
			if (re != null) {
				return re;
			}
		}
		return null;
	}

	// matching by the "TreeMap" of each sentence
	public MatchedSubset semRelMatcher(
			TreeMap<String, List<MatchedNode>> srcSem,
			List<List<TreeMap<String, List<MatchedNode>>>> lib) {
		for (List<TreeMap<String, List<MatchedNode>>> mSentence : lib) {
			for (TreeMap<String, List<MatchedNode>> tmOneSem : mSentence) {
				if (isMatchedSem(srcSem, tmOneSem)) {
					System.out.println("matching succeeds");
					return getMatchedSubset(tmOneSem);
				}
			}
		}
		System.out.println("matching fails!");
		return null;
	}

	// get the corresponding links for the matched semantic relationships
	public List<List<LinkNode>> getCorrespondingLinks(MatchedSubset ms,
			String linksFileName) {
		if (ms == null) {
			return null;
		} else {
			int senID = ms.getSenID();
			return MatcherTool.loadLinkXML(linksFileName, senID);
		}
	}

	// format for matching
	public MatchedSubset getMatchedSubset(TreeMap<String, List<MatchedNode>> tl) {
		MatchedSubset t = new MatchedSubset();
		List<MatchedNode> list = tl.get(tl.keySet().iterator().next());
		Iterator<MatchedNode> im = list.iterator();
		MatchedNode mNode = im.next();
		t.setSenID(mNode.getSenID());
		t.setSemIndex(mNode.getSemIndex());
		t.setSem(tl);
		return t;
	}

	// test whether two semantic relationSets are matched
	public boolean isMatchedSem(TreeMap<String, List<MatchedNode>> rel1,
			TreeMap<String, List<MatchedNode>> rel2) {
		if (rel1.keySet().size() != rel2.keySet().size()) {
//			System.out.println("fail : keySets are not equal!");
			return false;
		}
		Iterator<String> srcIt = rel1.keySet().iterator();
		Iterator<String> desIt = rel2.keySet().iterator();
		while (srcIt.hasNext() && desIt.hasNext()) {
			String srcLabel = srcIt.next();
			String desLabel = desIt.next();
			if (!(srcLabel.equals(desLabel))) {
//				System.out.println("fail: labels are not equal!");
				return false;
			}
			if (!isMatchedList(rel1.get(srcLabel), rel2.get(desLabel))) {
//				System.out.println("fail: MatchList failed!");
				return false;
			}
		}
		if (!(CheckMutual(rel1, rel2))) {
//			System.out.println("fail: CheckMutaul fails!");
			return false;
		}
		return true;
	}

	public boolean isMatchedList(List<MatchedNode> src, List<MatchedNode> des) {
		String word = null;
		if (src.size() != des.size()) {
//			System.out.println("MatchList fail : size is not equal!");
			return false;
		}
		for (MatchedNode mNode : src) {
			word = mNode.getReEx().getWord1();
			MarkCount mc = new MarkCount();
			List<MatchedNode> tempList = getList(src, word, mc);
			if (tempList.size() != 0) {
	//			System.out.println("getList get word : " + word);
				MarkList(tempList, word);
			}
			StringBuffer ss = new StringBuffer();
			List<MatchedNode> desList = findList(tempList.size(), des, ss, mc);
			if (desList.size() != 0) {
//				System.out.println("for word1 desList.size() and ss is : "
//						+ tempList.size() + "  " + ss);
				MarkList(desList, ss.toString());
			}

			word = mNode.getReEx().getWord2();
			mc = new MarkCount();
			tempList = getList(src, word, mc);
			if (tempList.size() != 0) {
				// System.out.println("tempList.size() is : "+tempList.size());
//				System.out.println("getList get word : " + word);
				MarkList(tempList, word);
			}

			ss = new StringBuffer();
			desList = findList(tempList.size(), des, ss, mc);

			if (desList.size() != 0) {
//				System.out.println("for word2 desList.size() and ss is : "
//						+ tempList.size() + "  " + ss);
				MarkList(desList, ss.toString());
			}
		}
		if (isSuccessed(src, des)) {
			ClearList(src);
			ClearList(des);
//		System.out.println("MatchList: success!");
			return true;
		} else {
			ClearList(src);
			ClearList(des);
//			System.out.println("MatchList: failed at word : " + word);
			return false;
		}
	}

	// get the word map
	public HashMap<String, String> getWordMap(
			TreeMap<String, List<MatchedNode>> src,
			TreeMap<String, List<MatchedNode>> des) {
		HashMap<String, String> map = new HashMap<String, String>();
		HashSet<String> srcWord = getSet(src);
		// if(matchSem(src,des))
		{
			Iterator<String> isrc = src.keySet().iterator();
			Iterator<String> ides = des.keySet().iterator();
			while (isrc.hasNext() && ides.hasNext()) {
				String ss = isrc.next();
				List<MatchedNode> srcMatchNode = src.get(ss);
				List<MatchedNode> desMatchNode = des.get(ss);
				if (srcMatchNode.size() == 1) {
					MatchedNode m1 = srcMatchNode.get(0);
					MatchedNode m2 = desMatchNode.get(0);
					if (!map.containsKey(m1.getReEx().getWord1())) {
						map.put(m1.getReEx().getWord1(), m2.getReEx()
								.getWord1());
					}
					if (!map.containsKey(m1.getReEx().getWord2())) {
						map.put(m1.getReEx().getWord2(), m2.getReEx()
								.getWord2());
					}
				}
			}
			if (map.keySet().size() == srcWord.size()) {
//				System.out.println("get hash map just from the 1 size list<MatchedNode>");
				return map;
			}
			ClearListMark(src);
			ClearListMark(des);
			preMark(src, makeSet(map));
			preMark(des, makeSet(map));

			while (unMatched(map.size(), srcWord.size())) {
				isrc = src.keySet().iterator();
				ides = des.keySet().iterator();
				while (isrc.hasNext() && ides.hasNext()) {
					List<MatchedNode> srcNode = src.get(isrc.next());
					List<MatchedNode> desNode = des.get(ides.next());
					if (srcNode.size() != 1)
						while (check12(srcNode)) {
							List<MatchedNode> tt = get12(srcNode);
							mark12(map, tt, desNode);
						}
				}
				isrc = src.keySet().iterator();
				ides = des.keySet().iterator();
				while ((isrc.hasNext()) && (ides.hasNext())) {
					List<MatchedNode> l1 = src.get(isrc.next());
					List<MatchedNode> l2 = des.get(ides.next());
					if (l1.size() != 1) {
						if (check0(l1)) {
							List<MatchedNode> pp = get0(l1);
							Mark0(map, pp, l2);
							break;
						}
					}
				}
			}
		}
		return map;
	}

	public HashMap<String, String> getWordMap(List<RelationExample> relExRel1,
			List<RelationExample> relExRel2) {
		List<RelationExample> binaryRel1 = getBinaryRels(relExRel1);
		List<RelationExample> binaryRel2 = getBinaryRels(relExRel2);
		TreeMap<String, List<MatchedNode>> map1 = makeMap(binaryRel1, 0, 0);
		TreeMap<String, List<MatchedNode>> map2 = makeMap(binaryRel2, 0, 0);
		return getWordMap(map1, map2);
	}

	public Set<String> makeSet(HashMap<String, String> hm) {
		Iterator<String> is = hm.keySet().iterator();
		Set<String> set = new HashSet<String>();
		while (is.hasNext()) {
			set.add(hm.get(is.next()));
		}
		return set;
	}

}
