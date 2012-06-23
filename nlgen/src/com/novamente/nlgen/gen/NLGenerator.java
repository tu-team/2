package com.novamente.nlgen.gen;

import java.util.HashMap;
import java.util.List;

import com.novamente.nlgen.io.RelFileReader;
import com.novamente.nlgen.match.MatchedResult;
import com.novamente.nlgen.match.Matcher;
import com.novamente.nlgen.match.MatcherImpl;
import com.novamente.nlgen.util.Option;
import com.novamente.nlgen.util.OptionParser;
import com.novamente.nlgen.util.RelationExample;
import com.novamente.nlgen.util.WordNode;

/**
 * @author lianlian
 * 
 */
public class NLGenerator {

	String enCoding = "GBK";
	String relExFileName = null;
	String linksFileName = null;
	
	public void setNlgeInfo(String relExFileName, String linksFileName,
			String enCoding) {
		this.relExFileName = relExFileName;
		this.linksFileName = linksFileName;
		this.enCoding = enCoding;		
	}
	
	public synchronized String[] generatorFromString(String relexInput){
		return generatorFromString(relexInput, this.enCoding, this.relExFileName, this.linksFileName);
	}
	
	/**
	 * The rough sketch of NLGen system 1. Read the input of the relationships
	 * of RelEx 2. Classify the relationships into two classes: unary and binary
	 * 3. Match the binary relationships from the database(corpus) 4. Output the
	 * most matched sentence's links and the sentence String 5. Replace the
	 * words by the words in the relationships of RelEx 6. Applying the unary
	 * relationships to refine the generated sentence.
	 * */

	public String[] generator(List<RelationExample> relExRelations, String enCoding,
			String relXML, String linksXML) {
		Matcher m = new MatcherImpl();
		GenerateFromLinks gl = new GenerateFromLinks();
		
		MatchedResult ms = m.getMatchedResult(relExRelations, relXML, linksXML);
		if (ms != null) {
			HashMap<String, WordNode> nodes = gl.getWordNodes(ms.getWordMap(),
					relExRelations);
			List<WordNode[]> nodeList = gl.getOrderedWordList(ms
					.getMatchedLinks(), nodes, ms.getWordMap());
			String[] senCandidates = new String[nodeList.size()];
			System.out.println("found "+senCandidates.length+" similar sentences...");
			for (int i = 0; i < nodeList.size(); i++) {
				String sen = gl.generateSentence(nodeList.get(i));
				senCandidates[i] = sen;
			}
			return senCandidates;
		} else
			return null;
	}

	public String[] generatorFromFile(String relFileName, String enCoding,
			String relXML, String linksXML) throws Exception {
		List<RelationExample> relExRelations = RelFileReader.readRelFromFile(
				relFileName, enCoding);
		return generator(relExRelations, enCoding, relXML, linksXML);
	}
	
	public synchronized String[] generatorFromString(String relexInput, String enCoding,
			String relXML, String linksXML) throws StringIndexOutOfBoundsException {
		List<RelationExample> relExRelations = RelFileReader.readRelFromString(
				relexInput, enCoding);
		return generator(relExRelations, enCoding, relXML, linksXML);
	}
	
/*	public static void main(String[] args) {
		NLGenerator nlg = new NLGenerator();
		String[] sens = nlg.generator("data/testData/1~", "GBK",
				CorpusParsers.relExFileName, CorpusParsers.linksFileName);
		if (sens != null) {
			for (String str : sens) {
				System.out.println(str);
			}
		} else
			System.err.println("cannot match the similar sentence from the corpus...");
	}*/
	
	public static class Options
	{
		//fabricio: mudei isso, pro caso do SErver
		@Option(name = "-fileIn", required = false, usage = "Input File for ParserCorpus (Required)")
		public String inFileName;
		
/*		@Option(name = "-sentence", required = false,usage = "The sentence which needs to be parsed by RelEx, then generate the sentence by the relations parsed by RelEX...")
		public String inputSen;*/

		@Option(name = "-enCoding", required = false,usage = "The encoding of the input file which contails the relEx relations of a sentence...(Default: GBK)")
		public String enCoding;
		
		@Option(name = "-useMySql", required = false, usage = "true or false (not ready at the moment)")
		public String useMySql;
		
		@Option(name = "-relExRelationsFilePath", required = true, usage = "The filename and path of the XML file which contains the relEx relations of the corpus...")
		public String relExFileName;
		
		@Option(name = "-linksFilePath", required = true, usage = "The filename and path of the XML file which contains the links of the corpus...")
		public String linksFileName;
	}
	
	
	public static void main(String[] args) {
		NLGenerator nlg = new NLGenerator();
		OptionParser optParser = new OptionParser(Options.class);
		Options opts = (Options) optParser.parse(args, true);
		System.out.println("Calling with " + optParser.getPassedInOptions());
		String fileIn = opts.inFileName;
		String enCoding = "GBK";
		if(opts.enCoding!=null){
			enCoding=opts.enCoding;
		}
		String relExFileName = opts.relExFileName;
		String linksFileName = opts.linksFileName;
		String[] sens;
		try {
			sens = nlg.generatorFromFile(fileIn, enCoding,
					relExFileName, linksFileName);
			if (sens != null) {
				for (String str : sens) {
					System.out.println(str);
				}
			} else
				System.err.println("cannot match the similar sentence from the corpus...");
			
		} catch (Exception e) {
			System.err.println("ERROR - Relex not well formed. Message: "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
}
