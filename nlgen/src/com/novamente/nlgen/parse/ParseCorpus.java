/**
 * 
 */
package com.novamente.nlgen.parse;

import java.util.*;
import org.linkgrammar.*;
import com.novamente.nlgen.util.*;

import relex.*;
import relex.output.SimpleView;

/**
 * parsing corpus with Link Parser and RelEx
 * 
 * @author lianlian
 * 
 */
public class ParseCorpus {

	public LoadLinkParser llp;

	public LoadRelEx lre;

	public ParseCorpus() {
		init();
	}

	public void init() {
		llp = new LoadLinkParser();
		lre = new LoadRelEx();
		llp.init();
	}

	// parse one sentence by RelEx
	public List<List<RelationExample>> getSemanticRelations(String sentence) {
		List<List<RelationExample>> allRelations = new ArrayList<List<RelationExample>>();
		List<String> relationString = new ArrayList<String>();
		int maxParses = 30;
		RelationExtractor re = new RelationExtractor(false);
		re.setMaxParses(maxParses);
		re.setAllowSkippedWords(true);
		//RelexInfo ri = re.processSentence(sentence, null);
		Sentence ri = re.processSentence(sentence, null);
		int np = ri.getParses().size();
		if (np > maxParses)
			np = maxParses;
		for (ParsedSentence parse : ri.getParses()) {
			//fabricio: changed to printRelationsAlt to be similar to embodiment
			relationString.add(SimpleView.printRelationsAlt(parse));
		}
		for (String str : relationString) {
			//System.out.println("Relation: "+relationString);
			String[] aRelString = str.trim().split("\n");
			List<RelationExample> relations = new ArrayList<RelationExample>();
			for (String str1 : aRelString) {
				str1.trim();
				System.out.println("Relation: "+str1);
				
//				int i = str1.indexOf("(");
//				int j = str1.lastIndexOf(",");
//				int k = str1.indexOf(")");
//				
//				RelationExample rel = new RelationExample(str1.substring(i + 1,
//						j).trim(), str1.substring(j + 1, k).trim(), str1
//						.substring(0, i).trim());
				
				int i = str1.indexOf("(");
				int j = str1.lastIndexOf(",");
				int k = str1.indexOf(")");
				if(j == -1) j = k;//if there is no comma, then replace the j value
				
				String w1 = str1.substring(i + 1,j).trim();
				String w2  = "";
				if(j != k)//only if there is a comma
					w2 = str1.substring(j + 1, k).trim();
				
				String l = str1.substring(0, i).trim();				
				
				RelationExample rel = new RelationExample(w1, w2, l);				
				rel.setBinary(rel.isBinary());
				relations.add(rel);
			}
			allRelations.add(relations);
		}
		return allRelations;
	}

	public List<List<List<RelationExample>>> getSemRelsFromSet(
			Vector<String> senSet) {
		List<List<List<RelationExample>>> allRelations = new ArrayList<List<List<RelationExample>>>();
		for (String s : senSet) {
			if (!s.equals("END")) {
				List<List<RelationExample>> oneSentence = getSemanticRelations(s);
				allRelations.add(oneSentence);
			}
		}
		return allRelations;
	}

	// parse one sentence using Link Parser
	public List<List<Link>> getLinks(String sen) {
		List<List<Link>> links = new ArrayList<List<Link>>();
		List<Linkage> linkages = llp.getLinkages(sen);
		for (Linkage l : linkages) {
			List<Link> aLink = llp.getLinks(l);
			links.add(aLink);
		}
		return links;
	}

	public List<Link> getLinks(Linkage lnke) {
		List<Link> lks = lnke.getLinks();
		return lks;
	}

	public List<List<Link>> getAllLinks(List<Linkage> linkages) {
		List<List<Link>> links = new ArrayList<List<Link>>();
		for (Linkage l : linkages) {
			List<Link> al = llp.getLinks(l);
			links.add(al);
		}
		return links;
	}

	public List<Linkage> getLinkages(String sen) {
		List<Linkage> ll = llp.getLinkages(sen);
		return ll;
	}

	/**
	 * parsing corpus by Link Parser
	 * 
	 * @param sen
	 *            :the sentences read from corpus, one element represent one
	 *            sentence
	 * 
	 */
	public List<List<List<Link>>> getLinksForCorpus(Vector<String> sen) {
		List<List<List<Link>>> allLinks = new ArrayList<List<List<Link>>>();
		for (String str : sen) {
			List<List<Link>> links = this.getLinks(str);
			allLinks.add(links);
		}
		return allLinks;
	}

	public List<List<Linkage>> getLinkagesForCorpus(Vector<String> sen) {
		List<List<Linkage>> ll = new ArrayList<List<Linkage>>();
		for (String str : sen) {
			List<Linkage> lnke = this.getLinkages(str);
			ll.add(lnke);
		}
		return ll;
	}

}
