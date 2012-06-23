package com.novamente.nlgen.parse;

import com.novamente.nlgen.util.*;
import com.novamente.nlgen.match.*;
import java.io.*;
import java.util.*;

class lnf {
	public void read(String fileName) {
		MatcherTool xt = new MatcherTool();
		@SuppressWarnings("unused")
		List<List<TreeMap<String, List<MatchedNode>>>> list = xt
				.loadRelexXML(fileName);
	}

	public void printXMLFile(String fileName) {
		MatcherTool xt = new MatcherTool();
		List<List<TreeMap<String, List<MatchedNode>>>> list = xt
				.loadRelexXML(fileName);
		int semCount = 0;
		for (List<TreeMap<String, List<MatchedNode>>> oneSen : list) {
			for (TreeMap<String, List<MatchedNode>> oneSem : oneSen) {
				System.out.println("semantic relations index : " + semCount++);
				Iterator<String> is = oneSem.keySet().iterator();
				while (is.hasNext()) {
					List<MatchedNode> listMN = oneSem.get(is.next());
					printListMN(listMN);
					System.out.println();
				}
			}
		}
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
					+ mn.getReEx().getWord1() + "  " + mn.getReEx().getWord2());
		}
	}

	public void testMatch() {
		MatcherImpl m = new MatcherImpl();
		TreeMap<String, List<MatchedNode>> tm = CreateTestTM();
		MatchedSubset mRes = m.semRelMatcher("alex.xml", tm);
		printMatchResult(mRes);

		HashMap<String, String> map = m.getWordMap(tm, mRes.getSem());
		MatcherTool xt = new MatcherTool();
		xt.printHashMap(map);
	}

	public TreeMap<String, List<MatchedNode>> CreateTestTM() {
		MatcherTool xt = new MatcherTool();
		List<RelationExample> list = new ArrayList<RelationExample>();
		list.add(new RelationExample(new String("cook"), new String("T"),
				new String("HYP")));
		list.add(new RelationExample(new String("cook"), new String("Leo"),
				new String("_obj")));
		list.add(new RelationExample(new String("cook"), new String("dog"),
				new String("_subj")));
		list.add(new RelationExample(new String("beat"), new String("dog"),
				new String("_subj")));
		list.add(new RelationExample(new String("beat"), new String("US"),
				new String("to")));
		list.add(new RelationExample(new String("beat"), new String("cook"),
				new String("to2")));
		TreeMap<String, List<MatchedNode>> tm = xt.makeMap(list, 0, 0);
		xt.printList(list);
		xt.printMAP(tm);
		return tm;
	}

	public void printMatchResult(MatchedSubset mRes) {
		System.out.println("sentence ID : " + mRes.getSenID());
		System.out.println("semantic ID : " + mRes.getSemIndex());
		MatcherTool xt = new MatcherTool();
		xt.printMAP(mRes.getSem());
	}

	public void search(String sen, String fileName) {
		ParseCorpus pc = new ParseCorpus();
		MatcherTool xt = new MatcherTool();
		MatcherImpl m = new MatcherImpl();
		MatchedSubset re;
		List<List<RelationExample>> lre = pc.getSemanticRelations(sen);
		List<List<RelationExample>> lll = xt.getBinaryRelsAll(lre);
		List<TreeMap<String, List<MatchedNode>>> ltm = xt.makeSearchMap(lll);
		List<List<TreeMap<String, List<MatchedNode>>>> lib = xt
				.loadRelexXML(fileName);
		re = m.semRelMatcher(ltm, lib);
		if (re != null) {
			printMatchResult(re);
		}
		System.out.println("re.getSenID is : " + re.getSenID());
		List<List<LinkNode>> lll1 =MatcherTool.loadLinkXML("syntaxRelation.xml", re
				.getSenID());
		xt.printLLL(lll1);
	}

	public void search(List<RelationExample> rels, String fileName) {
		MatcherTool xt=new MatcherTool();
		MatcherImpl m=new MatcherImpl();
		List<RelationExample> bRels= xt.getBinaryRels(rels);
		TreeMap<String, List<MatchedNode>> relMaps=xt.makeMap(bRels, 0, 0);
		List<List<TreeMap<String, List<MatchedNode>>>> lib = xt.loadRelexXML(fileName);
		MatchedSubset result= m.semRelMatcher(relMaps, lib);
		if(result!=null)
			printMatchResult(result);
	}

	public void search() throws IOException {
		FileReader fr = new FileReader("ff.txt");
		BufferedReader br = new BufferedReader(fr);
		String str = br.readLine();
		if (str == null) {
			System.out.println("search error");
			return;
		}
		// String s = "Alice lives in Berkeley.";
		String file = "alex.xml";
		search(str, file);
	}
}

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		lnf l = new lnf();
		// l.printXMLFile("rel.xml");
		// l.read("alex.xml");
		// l.testMatch();
		l.search();
	}

}
