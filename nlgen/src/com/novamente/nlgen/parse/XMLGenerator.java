package com.novamente.nlgen.parse;

import java.util.*;
import java.util.regex.Pattern;

import org.linkgrammar.*;

import com.novamente.nlgen.io.FileTool;
import com.novamente.nlgen.util.*;

import java.io.*;

import org.jdom.*;
import org.jdom.output.*;

public class XMLGenerator {

	public ArrayList<String> tokenize(String sen) {
		ArrayList<String> as = new ArrayList<String>();
		String[] st = sen.trim().split(" +");
		as.addAll(Arrays.asList(st));
		ListIterator<String> ls = as.listIterator();
		while (ls.hasNext()) {
			String s = ls.next();
			int len = s.length();
			char c = s.charAt(len - 1);
			String lastChar = String.valueOf(c);
			if (!Pattern.matches("[a-zA-Z]", lastChar)) {
				ls.remove();
				String sWord = s.substring(0, len - 1);
				String sMark = s.substring(len - 1);
				ls.add(sWord);
				ls.add(sMark);
			}
		}
		as.add("RIGHT-WALL");
		as.add(0, "LEFT-WALL");
		return as;
	}

	public void quikOut(String[] st) {
		for (String s : st) {
			System.out.print(s + " ");
		}
		System.out.println();
	}

	public void printArray(ArrayList<String> as) {
		for (String s : as) {
			System.out.print(s + " ");
		}
		System.out.println();
	}

	public ArrayList<String> genPOSArray(ArrayList<String> as) {
		ArrayList<String> al = new ArrayList<String>();
		for (String s : as) {
			if (s.trim().equals("."))
				al.add("NO");
			else {
				int i = s.indexOf(".");
				if (i != -1) {
					String ss = s.substring(i + 1);
					al.add(ss);
				} else {
					al.add("NO");
				}
			}
		}
		return al;
	}

	public ArrayList<String> genWordsArray(ArrayList<String> as) {
		ArrayList<String> al = new ArrayList<String>();
		for (String s : as) {
			if (s.trim().equals(".")) {
				al.add(s);
			} else {
				int i = s.indexOf(".");
				if (i != -1) {
					String ss = s.substring(0, i);
					al.add(ss);
				} else {
					al.add(s);
				}
			}
		}
		return al;
	}

	
	
	/**
	 * @param corpusFileName
	 *            : the path of corpus which needs to be parsed by Link Parser
	 * @param encoding
	 *            : the encoding of the corpus file
	 * @param outputFileName
	 *            : the generated XML file after parsing by Link Parser
	 * 
	 * parsing the corpus by Link Parser, then generate the XML file which
	 * contains all the parsed result by Link Parser
	 */
	public void genSyntaxRelation(String corpusFilename, String encoding,
			String outputFileName) {
		ParseCorpus pc = new ParseCorpus();
		System.out.println("start parsing by Link Parser...");
		Vector<String> sentenceSet = FileTool
				.readLine(corpusFilename, encoding);
		List<List<Linkage>> allLinkages = pc.getLinkagesForCorpus(sentenceSet);
		Element root = new Element("syntax");
		Document doc = new Document(root);
		root.setAttribute("comment",
				"syntactic relationships of each sentence from the corpus");
		int sentenceID = 0;
		// int linkID =0;
		for (List<Linkage> sentenceLinkages : allLinkages) {
			Element sentence = new Element("sentence");
			// ArrayList<String> at =
			// tokenize(sentenceSet.elementAt(sentenceID));
			sentence.setAttribute("ID", Integer.toString(sentenceID));
			sentence.setAttribute("senString", sentenceSet
					.elementAt(sentenceID++));
			root.addContent(sentence);
			int synIdx = 0;
			for (Linkage l : sentenceLinkages) {
				Element synEle = new Element("SyntaxRelations");

				synEle.setAttribute("index", Integer.toString(synIdx++));
				sentence.addContent(synEle);
				List<Link> ll = pc.getLinks(l);
				String[] st = l.getWords();
				// quikOut(st);
				ArrayList<String> as = new ArrayList<String>();
				as.addAll(Arrays.asList(st));
				ArrayList<String> pos = genPOSArray(as);
				ArrayList<String> words = genWordsArray(as);
				// printArray(words);
				// printArray(pos);

				for (Link lk : ll) {

					Element elk = new Element("Links");

					elk.setAttribute("leftLoc", Integer.toString(lk.getLeft()));
					elk.setAttribute("rightLoc", Integer
							.toString(lk.getRight()));
					// elk.setAttribute("leftWord", at.get(lk.getLeft()));
					// elk.setAttribute("rightWord", at.get(lk.getRight()));
					elk.setAttribute("leftWord", words.get(lk.getLeft()));
					elk.setAttribute("rightWord", words.get(lk.getRight()));
					elk.setAttribute("leftPOS", pos.get(lk.getLeft()));
					elk.setAttribute("rightPOS", pos.get(lk.getRight()));
					elk.setAttribute("label", lk.getLabel());
					elk.setAttribute("leftLabel", lk.getLeftLabel());
					elk.setAttribute("rightLabel", lk.getRightLabel());
					synEle.addContent(elk);

				}

			}
		}
		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat()
				.setEncoding("utf-8"));
		try {
			// out.output(doc, System.out);
			FileWriter fw = new FileWriter(outputFileName);
			out.output(doc, fw);
			fw.close();
			System.out.println("finish parsing by Link Parser...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param corpusFileName
	 *            : the path of corpus which needs to be parsed by RelEx
	 * @param encoding
	 *            : the encoding of the corpus file
	 * @param outputFileName
	 *            : the generated XML file after parsing by RelEx
	 * 
	 * parsing the corpus by RelEx, then generate the XML file which
	 * contains all the parsed result by RelEx
	 */
	public void genRelexRelation(String corpusFileName, String encoding,
			String outputFileName) {
		ParseCorpus pc = new ParseCorpus();
		System.out.println("start parsing by RelEx...");
		Vector<String> senSet = FileTool.readLine(corpusFileName, encoding);
		List<List<List<RelationExample>>> allRels = pc
				.getSemRelsFromSet(senSet);
		Element root = new Element("semantic");
		root.setAttribute("comment",
				"semantic relationships of each sentence from the corpus");
		Document doc = new Document(root);
		int sentenceID = 0;

		for (List<List<RelationExample>> oneSentence : allRels) {
			Element sentence = new Element("sentence");
			// ArrayList<String> at =
			// tokenize(sentenceSet.elementAt(sentenceID));
			sentence.setAttribute("ID", Integer.toString(sentenceID));
			sentence.setAttribute("senString", senSet.elementAt(sentenceID++));
			root.addContent(sentence);
			int semIdx = 0;
			for (List<RelationExample> oneSem : oneSentence) {
				Element semEle = new Element("SemanticRelations");

				semEle.setAttribute("index", Integer.toString(semIdx++));
				sentence.addContent(semEle);

				for (RelationExample re : oneSem) {
					if (re.getBinary() == true) {
						Element eRE = new Element("BinaryRelation");
						eRE.setAttribute("leftWord", re.getWord1());
						eRE.setAttribute("rightWord", re.getWord2());
						eRE.setAttribute("label", re.getLabel());
						semEle.addContent(eRE);
					} else {
						WordProperty wp = new WordProperty(re.getLabel(), re
								.getWord1(), re.getWord2());
						Element eRE = new Element("UnaryRelation");
						eRE.setAttribute("label", wp.getLabel());
						eRE.setAttribute("word", wp.getWord());
						eRE.setAttribute("type", wp.getType());
						semEle.addContent(eRE);
					}
				}
			}
		}
		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat()
				.setEncoding(encoding));
		try {
			// out.output(doc, System.out);
			FileWriter fw = new FileWriter(outputFileName);
			out.output(doc, fw);
			fw.close();
			System.out.println("finish parsing by RelEx...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
