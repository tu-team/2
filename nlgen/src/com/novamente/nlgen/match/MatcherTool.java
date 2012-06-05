package com.novamente.nlgen.match;

import java.util.*;

import org.jdom.*;
import org.jdom.input.*;

import com.novamente.nlgen.util.*;

public class MatcherTool {

	// load the relationships of RelEx from the corpus
	@SuppressWarnings("unchecked")
	public List<List<TreeMap<String, List<MatchedNode>>>> loadRelexXML(
			String fileName) {
		SAXBuilder sb = new SAXBuilder();
		List<List<TreeMap<String, List<MatchedNode>>>> lAll = new ArrayList<List<TreeMap<String, List<MatchedNode>>>>();
		int senID = 0;
		int semID = 0;
		try {
			Document doc = sb.build(fileName);
			Element root = doc.getRootElement();

			String str1 = root.getAttributeValue("comment");
			System.out.println("loading " + str1);

			List<Element> listSentence = root.getChildren("sentence");

			for (Element eSentence : listSentence) {
				semID = 0;
				List<Element> listSem = eSentence.getChildren();
				List<TreeMap<String, List<MatchedNode>>> listTree = new ArrayList<TreeMap<String, List<MatchedNode>>>();
				for (Element eOneSem : listSem) {
					List<Element> listBinary = eOneSem
							.getChildren("BinaryRelation");
					List<RelationExample> listRel = new ArrayList<RelationExample>();
					for (Element e : listBinary) {
						listRel.add(new RelationExample(e.getAttribute(
								"leftWord").getValue(), e.getAttribute(
								"rightWord").getValue(), e
								.getAttribute("label").getValue()));
					}
					// printList(listRel);
					TreeMap<String, List<MatchedNode>> tm = makeMap(listRel,
							senID, semID);
					// printMAP(tm);
					semID++;
					listTree.add(tm);
				}
				senID++;
				lAll.add(listTree);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lAll;
	}

	// load the relationships of Link Parser from the corpus
	@SuppressWarnings("unchecked")
	public static List<List<LinkNode>> loadLinkXML(String fileName, int senID) {
		List<List<LinkNode>> lll = new ArrayList<List<LinkNode>>();
		SAXBuilder sb = new SAXBuilder();
		try {
			Document doc = sb.build(fileName);
			Element root = doc.getRootElement();
			List<Element> le = root.getChildren("sentence");
			String str1 = root.getAttributeValue("comment");
			System.out.println("loading " + str1);
			for (Element e : le) {
				if (e.getAttribute("ID").getValue().equals(
						Integer.toString(senID))) {
					List<Element> le2 = e.getChildren();
					for (Element e2 : le2) {
						List<LinkNode> ll = new ArrayList<LinkNode>();
						List<Element> le3 = e2.getChildren();
						for (Element e3 : le3) {
							String label = e3.getAttribute("label").getValue();
							String leftWord = e3.getAttribute("leftWord")
									.getValue();
							String rightWord = e3.getAttribute("rightWord")
									.getValue();
							LinkNode ln = new LinkNode(leftWord, rightWord,
									label);
							ln.setLeftLoc(Integer.parseInt(e3.getAttribute(
									"leftLoc").getValue()));
							ln.setRightLoc(Integer.parseInt(e3.getAttribute(
									"rightLoc").getValue()));
							ll.add(ln);
							//System.out.println("ll added : "+label+":"+leftWord
							// +":"+rightWord);
						}
						lll.add(ll);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("loading links finish");
		return lll;
	}

	// for test
	public void printLLL(List<List<LinkNode>> lll) {
		int syntaxIndex = 0;
		for (List<LinkNode> ll : lll) {
			System.out.println("syntax relation index : " + syntaxIndex++);
			for (LinkNode l : ll) {
				System.out.println("label:word1:word2: " + l.getLabel() + ":"
						+ l.getLWord() + ":" + l.getRWord());
			}
		}
	}

	// extract the binary relationships from the relationSet of a sentence
	// parsed by RelEx
	public List<RelationExample> getBinaryRels(List<RelationExample> lre) {
		List<RelationExample> ll = new ArrayList<RelationExample>();
		for (RelationExample re : lre) {
			if (re.isBinary()) {
				ll.add(re);
			}
		}
		return ll;
	}

	public List<List<RelationExample>> getBinaryRelsAll(
			List<List<RelationExample>> lre) {
		List<List<RelationExample>> llr = new ArrayList<List<RelationExample>>();
		for (List<RelationExample> lr : lre) {
			List<RelationExample> lr2 = getBinaryRels(lr);
			llr.add(lr2);
		}
		return llr;
	}

	// format the relationships of a sentence parsed by RelEx
	// "binaryRelations" is the parameter of the binary relationships extracted
	// by the
	// function "getBinaryRels"
	public TreeMap<String, List<MatchedNode>> makeMap(
			List<RelationExample> binaryRelations, int senID, int semID) {
		TreeMap<String, List<MatchedNode>> tm = new TreeMap<String, List<MatchedNode>>();
		for (RelationExample re : binaryRelations) {
			String label = re.getLabel();
			String word1 = re.getWord1();
			String word2 = re.getWord2();
			if (tm.containsKey(label)) {
				List<MatchedNode> lm = tm.get(label);
				MatchedNode mn = new MatchedNode(word1, word2, label);
				mn.setSenID(senID);
				mn.setSemIndex(semID);
				lm.add(mn);
			} else {
				List<MatchedNode> lm = new ArrayList<MatchedNode>();
				MatchedNode mn = new MatchedNode(word1, word2, label);
				mn.setSenID(senID);
				mn.setSemIndex(semID);
				lm.add(mn);
				tm.put(label, lm);
			}
		}
		return tm;
	}

	// format the relationships of different parsed results
	public List<TreeMap<String, List<MatchedNode>>> makeSearchMap(
			List<List<RelationExample>> list) {
		int senID = 0;
		int semID = 0;
		List<TreeMap<String, List<MatchedNode>>> ltm = new ArrayList<TreeMap<String, List<MatchedNode>>>();
		for (List<RelationExample> lm : list) {
			TreeMap<String, List<MatchedNode>> tm = makeMap(lm, senID, semID++);
			ltm.add(tm);
		}
		return ltm;
	}

	public void printList(List<RelationExample> list) {
		System.out.println("one sem group's relation example");
		for (RelationExample e : list) {
			System.out.println(e.getLabel() + " " + e.getWord1() + " "
					+ e.getWord2());
		}
	}

	public void printMAP(TreeMap<String, List<MatchedNode>> tm) {
		System.out.println("semantic relations :");
		Iterator<String> is = tm.keySet().iterator();
		while (is.hasNext()) {
			List<MatchedNode> listMN = tm.get(is.next());
			printListMN(listMN);
		}
		System.out.println();
	}

	public void printListMN(List<MatchedNode> list) {
		int i = 0;
		for (MatchedNode mn : list) {
			if (i == 0) {
				i++;
				System.out.print("senID : " + mn.getSenID() + " semID : "
						+ mn.getSemIndex() + "  ");
			}
			System.out.print(mn.getReEx().getLabel() + "  "
					+ mn.getReEx().getWord1() + "  " + mn.getReEx().getWord2()
					+ "  ");
		}
		System.out.println();
	}

	public void printHashMap(HashMap<String, String> map) {
		Iterator<String> is = map.keySet().iterator();
		while (is.hasNext()) {
			String s = is.next();
			System.out.println(s + "   " + map.get(s));
		}
	}

	public List<MatchedNode> get0(List<MatchedNode> list) {
		List<MatchedNode> lm = new ArrayList<MatchedNode>();
		for (MatchedNode m : list) {
			if (m.getMark() == 0) {
				lm.add(m);
			}
		}
		return lm;
	}

	public void Mark0(HashMap<String, String> map, List<MatchedNode> lm,
			List<MatchedNode> des) {
		List<MatchedNode> m1 = pickMark0(lm);
		List<MatchedNode> m2 = pickMark0(des);
		for (MatchedNode m : m1) {
			String word = m.getReEx().getWord1();
			MarkCount mc = new MarkCount();
			List<MatchedNode> lm1 = getList(m1, word, mc);
			if (lm1.size() != 0) {
				MarkList(lm1, word);
			}
			StringBuffer ss = new StringBuffer();
			List<MatchedNode> ldes = findList(lm1.size(), m2, ss, mc);
			if (ldes.size() != 0) {
				MarkList(m2, ss.toString());
				map.put(word, ss.toString());
			}

			word = m.getReEx().getWord2();
			mc = new MarkCount();
			lm1 = getList(m1, word, mc);
			if (lm1.size() != 0) {
				MarkList(lm1, word);
			}
			ss = new StringBuffer();
			ldes = findList(lm1.size(), m2, ss, mc);
			if (ldes.size() != 0) {
				MarkList(ldes, ss.toString());
				map.put(word, ss.toString());
			}
		}
	}

	public List<MatchedNode> pickMark0(List<MatchedNode> list) {
		List<MatchedNode> lm = new ArrayList<MatchedNode>();
		for (MatchedNode m : list) {
			if (m.getMark() == 0) {
				lm.add(m);
			}
		}
		return lm;
	}

	public void mark12(HashMap<String, String> map, List<MatchedNode> lm,
			List<MatchedNode> des) {
		for (MatchedNode m : lm) {
			String word;
			if (m.getMark() == 1) {
				word = m.getReEx().getWord1();
			} else {
				word = m.getReEx().getWord2();
			}
			String toWord;
			if (map.containsKey(word)) {
				toWord = map.get(word);
			} else {
//				System.out.println("error in mark12: map did not contain key");
				return;
			}
			List<MatchedNode> m1 = pickNode(lm, word);
			List<MatchedNode> m2 = pickNode(des, toWord);
			for (MatchedNode mm : m1) {
				for (MatchedNode mp : m2) {
					if ((mm.getMark() == 1) && (mp.getMark() == 1)) {
						map.put(mm.getReEx().getWord2(), mp.getReEx()
								.getWord2());
						mm.setMark(3);
						mp.setMark(3);
					}
					if ((mm.getMark() == 2) && (mp.getMark() == 2)) {
						map.put(mm.getReEx().getWord1(), mp.getReEx()
								.getWord1());
						mm.setMark(3);
						mp.setMark(3);
					}
				}
			}
		}
	}

	public List<MatchedNode> pickNode(List<MatchedNode> list, String word) {
		List<MatchedNode> lm = new ArrayList<MatchedNode>();
		for (MatchedNode m : list) {
			if (m.getReEx().getWord1().equals(word)
					|| m.getReEx().getWord2().equals(word)) {
				lm.add(m);
			}
		}
		return lm;
	}

	public List<MatchedNode> get12(List<MatchedNode> list) {
		List<MatchedNode> lm = new ArrayList<MatchedNode>();
		for (MatchedNode m : list) {
			if ((m.getMark() == 1) || (m.getMark() == 2)) {
				lm.add(m);
			}
		}
		return lm;
	}

	public boolean check0(List<MatchedNode> list) {
		for (MatchedNode m : list) {
			if (m.getMark() == 0)
				return true;
		}
		return false;
	}

	public boolean check12(List<MatchedNode> list) {
		for (MatchedNode m : list) {
			if (m.getMark() == 1 || m.getMark() == 2)
				return true;
		}
		return false;
	}

	public boolean unMatched(int size1, int size2) {
		if (size1 == size2)
			return false;
		else
			return true;
	}

	public boolean checkUnmatch(List<MatchedNode> list) {
		for (MatchedNode m : list) {
			if (m.getMark() != 3)
				return true;
		}
		return false;
	}

	public void preMark(TreeMap<String, List<MatchedNode>> tm, Set<String> set) {
		if (set == null || tm == null)
			return;
		Iterator<String> iis = tm.keySet().iterator();

		while (iis.hasNext()) {
			List<MatchedNode> list = tm.get(iis.next());
			for (MatchedNode m : list) {
				if (set.contains(m.getReEx().getWord1())
						&& set.contains(m.getReEx().getWord2())) {
					m.setMark(3);
				} else if (set.contains(m.getReEx().getWord1())) {
					m.setMark(1);
				} else if (set.contains(m.getReEx().getWord2())) {
					m.setMark(2);
				} else {
					m.setMark(0);
				}
			}

		}
	}

	public HashSet<String> getSet(TreeMap<String, List<MatchedNode>> tm) {
		HashSet<String> set = new HashSet<String>();
		Iterator<String> it = tm.keySet().iterator();
		while (it.hasNext()) {
			List<MatchedNode> list = tm.get(it.next());
			for (MatchedNode m : list) {
				if (!set.contains(m.getReEx().getWord1())) {
					set.add(m.getReEx().getWord1());
				}
				if (!set.contains(m.getReEx().getWord2())) {
					set.add(m.getReEx().getWord2());
				}
			}
		}
		return set;
	}

	public void ClearList(List<MatchedNode> list) {
		for (MatchedNode m : list) {
			m.setMark(0);
		}
	}

	public boolean isSuccessed(List<MatchedNode> src, List<MatchedNode> des) {
		for (MatchedNode m : src) {
			if (m.getMark() != 3) {
//				System.out.println("isSuccess error:  src mark is : "+ m.getMark());
				return false;
			}
//			System.out.println("isSuccess :  src mark is : " + m.getMark());
		}
		for (MatchedNode m : des) {
			if (m.getMark() != 3) {
//				System.out.println("isSuccess error:  des mark is : "+ m.getMark());
				return false;
			}
//			System.out.println("isSuccess :  des mark is : " + m.getMark());
		}
		return true;
	}

	public void MarkList(List<MatchedNode> list, String word) {
		for (MatchedNode m : list) {
			if (m.getMark() != 3) {
				if (m.getReEx().getWord1().equals(word) && m.getMark() != 1) {
					if (m.getMark() == 2)
						m.setMark(3);
					else
						m.setMark(1);
				}
				if (m.getReEx().getWord2().equals(word) && m.getMark() != 2) {
					if (m.getMark() == 1)
						m.setMark(3);
					else
						m.setMark(2);
				}
			}
		}
	}

	public List<MatchedNode> getList(List<MatchedNode> list, String word,
			MarkCount mc) {
		List<MatchedNode> lm = new ArrayList<MatchedNode>();
		for (MatchedNode mn : list) {
			if (mn.getReEx().getWord1().equals(word) && (mn.getMark() != 3)
					&& (mn.getMark() != 1)) {
				if (mn.getMark() == 2)
					mn.setMark(3);
				else
					mn.setMark(1);
				lm.add(mn);
				mc.SetWord1Count(mc.GetWord1Count() + 1);
			}
			if (mn.getReEx().getWord2().equals(word) && (mn.getMark() != 3)
					&& (mn.getMark() != 2)) {
				if (mn.getMark() == 1)
					mn.setMark(3);
				else
					mn.setMark(2);
				lm.add(mn);
				mc.SetWord2Coutn(mc.GetWord2Count() + 1);
			}
		}
		return lm;
	}

	public List<MatchedNode> findList(int size, List<MatchedNode> list,
			StringBuffer str, MarkCount mc) {
		TreeMap<String, FindMark> tm = new TreeMap<String, FindMark>();
		List<MatchedNode> l = new ArrayList<MatchedNode>();

//		System.out.println("findList input list : ");
//		MatcherTool xt = new MatcherTool();
//		xt.printListMN(list);

		for (MatchedNode mn : list) {
			if (mn.getMark() != 3) {
				if (mn.getMark() != 1) {
					if (tm.containsKey(mn.getReEx().getWord1())) {
						FindMark fm = tm.get(mn.getReEx().getWord1());
						fm.SetValue(0, fm.GetValue(0) + 1);
						fm.SetValue(1, fm.GetValue(1) + 1);
						tm.put(mn.getReEx().getWord1(), fm);
					} else {
						FindMark fm = new FindMark();
						fm.SetValue(0, fm.GetValue(0) + 1);
						fm.SetValue(1, fm.GetValue(1) + 1);
						tm.put(mn.getReEx().getWord1(), fm);
					}
				}
				if (mn.getMark() != 2) {
					if (tm.containsKey(mn.getReEx().getWord2())) {
						FindMark fm = tm.get(mn.getReEx().getWord2());
						fm.SetValue(0, fm.GetValue(0) + 1);
						fm.SetValue(2, fm.GetValue(2) + 1);
						tm.put(mn.getReEx().getWord2(), fm);
					} else {
						FindMark fm = new FindMark();
						fm.SetValue(0, fm.GetValue(0) + 1);
						fm.SetValue(2, fm.GetValue(2) + 1);
						tm.put(mn.getReEx().getWord2(), fm);
					}
				}
			}
		}
		String word = null;
		Iterator<String> is = tm.keySet().iterator();
		while (is.hasNext()) {
			String ss = is.next();
			FindMark ff = tm.get(ss);
			if ((ff.GetValue(0) == size)
					&& (ff.GetValue(1) == mc.GetWord1Count())
					&& (ff.GetValue(2) == mc.GetWord2Count())) {
				word = ss;
				break;
			}
		}
		for (MatchedNode m : list) {
			if (m.getMark() != 3) {
				if (m.getReEx().getWord1().equals(word) && (m.getMark() != 1)) {
					l.add(m);
				}
				if (m.getReEx().getWord2().equals(word) && (m.getMark() != 2)) {
					l.add(m);
				}
			}
		}
		str.append(word);

	//	System.out.println("findList result l:");
//		xt.printListMN(l);
		return l;
	}

	public boolean CheckMutual(TreeMap<String, List<MatchedNode>> src,
			TreeMap<String, List<MatchedNode>> des) {
		int size = src.keySet().size();
		ArrayList<String> lSRC = new ArrayList<String>();
		lSRC.addAll(src.keySet());
		ArrayList<String> lDES = new ArrayList<String>();
		lDES.addAll(des.keySet());
		for (int i = 0; i < size - 1; i++) {
			for (int j = i + 1; j < size; j++) {
				if (!(CheckList(src, des, lSRC.get(i), lDES.get(j)))) {
//					System.out.println("label: " + lSRC.get(i) + " label : "+ lDES.get(j) + " Mutual check failed!");
					return false;
				} else {
					ClearListMark(des, lSRC.get(i), lDES.get(j));
				}
			}
		}
//	 System.out.println("match succeed!~~~~~~");
		return true;
	}

	public void ClearListMark(TreeMap<String, List<MatchedNode>> tm,
			String str1, String str2) {
		List<MatchedNode> list = tm.get(str1);
		for (MatchedNode m : list) {
			m.setMark(0);
		}
		list = tm.get(str2);
		for (MatchedNode m : list) {
			m.setMark(0);
		}
	}

	public void ClearListMark(TreeMap<String, List<MatchedNode>> tm) {

	}

	public boolean CheckList(TreeMap<String, List<MatchedNode>> src,
			TreeMap<String, List<MatchedNode>> des, String str1, String str2) {
		List<MatchedNode> src1 = src.get(str1);
		List<MatchedNode> src2 = src.get(str2);

		List<MatchedNode> des1 = des.get(str1);
		List<MatchedNode> des2 = des.get(str2);

		for (MatchedNode m1 : src1) {
			String m1w1 = m1.getReEx().getWord1();
			String m1w2 = m1.getReEx().getWord2();
			for (MatchedNode m2 : src2) {
				String m2w1 = m2.getReEx().getWord1();
				String m2w2 = m2.getReEx().getWord2();
				if (m1w1.equals(m2w1))
					if (!checkComponent(0, des1, des2)) {
//						System.out.println("fail: m1w1 m1w2 m2w1 m2w2 0 : "
//							+ m1w1 + "  " + m1w2 + "  " + m2w1 + "  "+ m2w2);
						return false;
					}
				if (m1w2.equals(m2w2))
					if (!checkComponent(1, des1, des2)) {
//						System.out.println("fail: m1w1 m1w2 m2w1 m2w2 1 : "
//								+ m1w1 + "  " + m1w2 + "  " + m2w1 + "  "+ m2w2);
						return false;
					}
				if (m1w1.equals(m2w2))
					if (!checkComponent(2, des1, des2)) {
//						System.out.println("fail: m1w1 m1w2 m2w1 m2w2 2 : "
//								+ m1w1 + "  " + m1w2 + "  " + m2w1 + "  "+ m2w2);
						return false;
					}
				if (m1w2.equals(m2w1))
					if (!checkComponent(3, des1, des2)) {
//						System.out.println("fail: m1w1 m1w2 m2w1 m2w2 3 : "
//								+ m1w1 + "  " + m1w2 + "  " + m2w1 + "  "+ m2w2);
						return false;
					}
			}
		}
		return true;
	}

	public boolean checkComponent(int flag, List<MatchedNode> des1,
			List<MatchedNode> des2) {
		for (MatchedNode m1 : des1) {
			String m1w1 = m1.getReEx().getWord1();
			String m1w2 = m1.getReEx().getWord2();
			for (MatchedNode m2 : des2) {
				String m2w1 = m2.getReEx().getWord1();
				String m2w2 = m2.getReEx().getWord2();

				if (flag == 0) {
					if (m1.getMark() != 3 && m2.getMark() != 3) {
						if (m1w1.equals(m2w1)
								&& ((m1.getMark() != 1) || (m2.getMark() != 1))) {
							// if(m1.getMark()==2)
							// m1.setMark(3);
							// else
							m1.setMark(1);

							// if(m2.getMark()==2)
							// m2.setMark(3);
							// else
							m2.setMark(1);
							return true;
						}
					}
				} else if (flag == 1) {
					if (m1.getMark() != 3 && m2.getMark() != 3) {
						if (m1w2.equals(m2w2)
								&& ((m1.getMark() != 2) || (m2.getMark() != 2))) {
							// if(m1.getMark()==1)
							// m1.setMark(3);
							// else
							m1.setMark(2);

							// if(m2.getMark()==1)
							// m2.setMark(3);
							// else
							m2.setMark(2);
							return true;
						}
					}
				} else if (flag == 2) {
					if (m1.getMark() != 3 && m2.getMark() != 3) {
						if (m1w1.equals(m2w2)
								&& ((m1.getMark() != 1) || (m2.getMark() != 2))) {
							// if(m1.getMark()==2)
							// m1.setMark(3);
							// else
							m1.setMark(1);

							// if(m2.getMark()==1)
							// m2.setMark(3);
							// else
							m2.setMark(2);
							return true;
						}
					}
				} else {
					if (m1.getMark() != 3 && m2.getMark() != 3) {
						if (m1w2.equals(m2w1)
								&& ((m1.getMark() != 2) || (m2.getMark() != 1))) {
							// if(m1.getMark()==1)
							// m1.setMark(3);
							// else
							m1.setMark(2);

							// if(m2.getMark()==2)
							// m2.setMark(3);
							// else
							m2.setMark(1);
							return true;
						}
					}
				}
			}
		}
//		System.out.println("checkComponent failed!");
		return false;
	}

}
