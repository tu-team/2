/**
 * 
 */
package com.novamente.nlgen.parse;

import java.util.ArrayList;
import java.util.Vector;

import com.novamente.nlgen.io.FileTool;

import relex.ParsedSentence;
import relex.RelationExtractor;
//import relex.RelexInfo;
import relex.Sentence;
import relex.algs.SentenceAlgorithmApplier;
import relex.anaphora.Antecedents;
import relex.anaphora.Hobbs;
import relex.concurrent.RelexContext;
import relex.entity.EntityInfo;
import relex.entity.EntityMaintainer;
import relex.output.SimpleView;
import relex.parser.LinkParser;
import relex.tree.PhraseMarkup;
import relex.tree.PhraseTree;

/**
 * @author lianlian
 * 
 * give the interface of the function whose 
 * input is "sentence" and the output is "semantic
 * relationships" of the "sentence"...
 */


public class LoadRelEx {
	
	
	public static final int verbosity = 1;

	public static final int DEFAULT_MAX_PARSES = 100;
	public static final int DEFAULT_MAX_SENTENCE_LENGTH = 1024;
	public static final int DEFAULT_MAX_PARSE_SECONDS = 30;
	public static final int DEFAULT_MAX_PARSE_COST = 1000;
	public static final String DEFAULT_ALGS_FILE = "./data/relex-semantic-algs.txt";
	

	/** The LinkParserClient to be used - this class isn't thread safe! */
	private RelexContext context;

	/** Syntax processing */
	//private LinkParser parser;
	private RelationExtractor parser;

	/** Semantic processing */
	private SentenceAlgorithmApplier sentenceAlgorithmApplier;

	/** Penn tree-bank style phrase structure markup. */
	private PhraseMarkup phraseMarkup;

	/** Anaphora resolution */
	public Antecedents antecedents;
	private Hobbs hobbs;
	public boolean do_anaphora_resolution;

	/* ---------------------------------------------------------- */
	/* Control parameters, etc. */
	/**
	 * Set the max number of parses.
	 * This will NOT reduce processing time; all parses are still computed,
	 * but only this many are returned.
	 */
	public void setMaxParses(int maxParses) {
		//context.getLinkParserClient().setMaxParses(maxParses);
		parser.setMaxParses(maxParses);
	}

	public void setMaxCost(int maxCost) {
		//context.getLinkParserClient().setMaxCost(maxCost);
		parser.setMaxCost(maxCost);
	}

	public void setAllowSkippedWords(boolean allow) {
		//context.getLinkParserClient().setAllowSkippedWords(allow);
		parser.setAllowSkippedWords(allow);
	}

	public void setMaxParseSeconds(int maxParseSeconds) {
		//context.getLinkParserClient().setMaxParseSeconds(maxParseSeconds);
		parser.setMaxParseSeconds(maxParseSeconds);
	}

	/* ---------------------------------------------------------- */

	//public RelexInfo processSentence(String sentence)
	public Sentence processSentence(String sentence)
	{
		return processSentence(sentence, null);
	}

	//public RelexInfo processSentence(String sentence,
	public Sentence processSentence(String sentence,
	                                 EntityMaintainer entityMaintainer)
	{
		starttime = System.currentTimeMillis();
		if (entityMaintainer == null)
		{
			entityMaintainer = new EntityMaintainer(sentence,
		                               new ArrayList<EntityInfo>());
		}

		//RelexInfo ri = parseSentence(sentence, entityMaintainer);
		Sentence ri = parseSentence(sentence, entityMaintainer);

		for (ParsedSentence parse : ri.getParses())
		{
			// Markup feature node graph with entity info,
			// so that the relex algs (next step) can see them.
			entityMaintainer.prepareSentence(parse.getLeft());

			// The actual relation extraction is done here.
			sentenceAlgorithmApplier.applyAlgs(parse, context);

			// Strip out the entity markup, so that when the
			// sentence is printed, we don't print gunk.
			entityMaintainer.repairSentence(parse.getLeft());

			// Also do a Penn tree-bank style phrase structure markup.
			phraseMarkup.markup(parse);

			// Repair the entity-mangled tree-bank string.
			PhraseTree pt = new PhraseTree(parse.getLeft());
			parse.setPhraseString(pt.toString());

			// Assign a simple parse-ranking score, based on LinkGrammar data.
			parse.simpleRankParse();
		}

		// Perform anaphora resolution
		if (do_anaphora_resolution)
		{
			hobbs.addParse(ri);
			hobbs.resolve(ri);
		}
		if (verbosity > 0) reportTime("Relex processing: ");
		return ri;
	}

	/**
	 * Parses a sentence, using the parser. The private ArrayList of
	 * currentParses is filled with the ParsedSentences Uses an optional
	 * EntityMaintainer to work on a converted sentence.
	 */
	//private RelexInfo
	private Sentence
	parseSentence(String sentence, EntityMaintainer entityMaintainer)
	{
		if (entityMaintainer != null) {
			sentence = entityMaintainer.getConvertedSentence();
		}
		if (sentence == null) return null;

		String orig_sentence = entityMaintainer.getOriginalSentence();
		//RelexInfo ri = null;
		Sentence ri = null;
		if (sentence.length() < DEFAULT_MAX_SENTENCE_LENGTH) {
			//ri = parser.parse(sentence, context.getLinkParserClient());
			//ri = parser.parse(sentence, context.getLinkParserClient());
			ri = parser.processSentence(sentence, entityMaintainer);
		} else {
			//ri = new RelexInfo();
			ri = new Sentence();
		}
		ri.setSentence(orig_sentence);
		return ri;
	}

	/* ---------------------------------------------------------- */
	// Provide some basic timing info
	Long starttime;

	private void reportTime(String msg)
	{
		Long now = System.currentTimeMillis();
		Long elapsed = now - starttime;
		starttime = now;
		System.out.println(msg + elapsed + " millseconds");
	}
	
	
	
	/**
	 * @param text: the vector which contains many sentences,
	 * and one line for a sentence...
	 * @return the semantic relationships of all sentences
	 */
	public Vector<String> fileRelationExtrator(Vector<String> text){
		
		Vector<String> relationSet=new Vector<String> ();
		String sentence = null;
		int maxParses = 30;
		int maxParseSeconds = 60;
		
		RelationExtractor re=new RelationExtractor(false);
		re.setAllowSkippedWords(true);
		re.setMaxParses(maxParses);
		re.setMaxParseSeconds(maxParseSeconds);
		
		for(String temp:text){
			if(!temp.equals("END")){
				sentence=temp;
//				System.out.println("\n\nSentence: ["+sentence+"]");
				//RelexInfo ri=re.processSentence(sentence, null);
				Sentence ri=re.processSentence(sentence, null);
				int np = ri.getParses().size();
				if (np > maxParses) np = maxParses;


				// Print output
				int numParses = 0;
				for (ParsedSentence parse: ri.getParses())
				{
					System.out.println(sentence);
					System.out.println("\n====\n");
					System.out.println("Parse " + (numParses+1) +
					             " of " + ri.getParses().size());

					// Print simple parse ranking
					Double confidence = parse.getTruthValue().getConfidence();
					String pconfidence = confidence.toString().substring(0,6);
					System.out.println("Parse confidence: " + pconfidence);
					System.out.println(
						"cost vector = (UNUSED=" + parse.getNumSkippedWords() +
						" DIS=" + parse.getDisjunctCost() +
						" AND=" + parse.getAndCost() +
						" LEN=" + parse.getLinkCost() + ")");
					relationSet.add(SimpleView.printRelations(parse));
					relationSet.add("\n");

					if (++numParses >= maxParses) break;
				}
			}else{
				System.out.println("finish");
			}
		}
		return relationSet;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LoadRelEx lr=new LoadRelEx();
		Vector<String> sentenceSet=FileTool.readLine("data//trivial-corpus.txt", "GBK");
		Vector<String> relationSet=lr.fileRelationExtrator(sentenceSet);
		FileTool.WriteByline(relationSet, "GBK", "data//trivial-corpus-relation.txt");
	}



}
