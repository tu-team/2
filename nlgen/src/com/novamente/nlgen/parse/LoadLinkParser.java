/**
 * author lianlian
 */
package com.novamente.nlgen.parse;

import java.util.List;

import org.linkgrammar.*;

/**
 * This class provides the functions to load Link Parser.
 */
public class LoadLinkParser {
	public static LGConfig config;

	public void init() {
		config = new LGConfig();
		LGService.init();
	}

	public List<Linkage> getLinkages(String sen) {
		ParseResult pr = LGService.parse(config, sen);
		return pr.getLinkages();
	}

	public List<Link> getLinks(Linkage l) {
		return l.getLinks();
	}

	public String getLinkString(String sen) {
		LGService.parse(config, sen);
		return LinkGrammar.getLinkString();
	}

	public String[] getWordsAndPOS(Linkage l) {
		return l.getWords();
	}

	public String[] getWords(Linkage l) {
		String[] words = l.getWords();
		for (int i = 0; i < words.length; i++) {
			if (words[i].contains(".")) {
				words[i] = words[i].substring(0, words[i].indexOf("."));
			}
		}
		return l.getWords();
	}

	public void test() {
		LGConfig config = new LGConfig();
		LGService.init();
		ParseResult pr = LGService.parse(config,
				"Mike saw the girl with the telescope");
		List<Linkage> results = pr.getLinkages();
		for (Linkage l : results) {
			List<Link> links = l.getLinks();
			String[] words = getWordsAndPOS(l);
			for(String temp:words){
				System.out.println(temp);
			}
			for (Link lk : links) {
				System.out.println(lk.getLeft() + " " + words[lk.getLeft()]
						+ " " + lk.getRight() + " " + words[lk.getRight()]
						+ " " + lk.getLabel());
				System.out.println(lk.getLabel() + " " + lk.getRightLabel());
			}
			System.out.println("\n");
		}
		System.out.println(LinkGrammar.getLinkString());
	}

	// Just for test
	public static void main(String[] args) {
		LoadLinkParser lp = new LoadLinkParser();
		lp.test();
	}

}
