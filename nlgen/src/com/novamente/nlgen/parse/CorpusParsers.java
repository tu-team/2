/**
 * 
 */
package com.novamente.nlgen.parse;

import com.novamente.nlgen.util.*;

/**
 * @author lianlian
 * parsing the corpus by the Link Parser and RelEx, then generate the
 * XML files
 */
public class CorpusParsers {
	
	public static String corpusFileName="data/trivial-corpus.txt";
	
	public static String corpusFileEncoding="utf-8";
	
	public static String linksFileName="data/syntaxRelation.xml";
	
	public static String relExFileName="data/semantic-rel-corpus.xml";

/*	
 * just for test
	public static void main(String[] args) {
		XMLGenerator h = new XMLGenerator();
		h.genSyntaxRelation(CorpusParsers.corpusFileName, CorpusParsers.corpusFileEncoding, CorpusParsers.linksFileName);
		h.genRelexRelation(CorpusParsers.corpusFileName, CorpusParsers.corpusFileEncoding,
				CorpusParsers.relExFileName);
	}*/
	
	/**
	 * @param args
	 */
	public static class Options
	{
		@Option(name = "-fileIn", required = true, usage = "The path and name of the file which contains several sentences")
		public String inFileName;
		
		@Option(name = "-fileOut", required = true,usage = "The path and name of the file which will be generated with relations")
		public String outFileName;

		@Option(name = "-enCoding", required = false,usage = "The encoding of the XML file which will be generated (Default: UTF-8)")
		public String enCoding;
		
		@Option(name = "-func", required = true, usage = "Functions which are used to parse ParserCorpus (Required: 'Semantic' or 'Syntax')")
		public String method;
	}
	public static void main(String[] args) {
		XMLGenerator xml = new XMLGenerator();
		OptionParser optParser = new OptionParser(Options.class);
		Options opts = (Options) optParser.parse(args, true);
		System.out.println("Calling with " + optParser.getPassedInOptions());
		String fileIn = opts.inFileName;
		String enCoding="UTF-8";
		if(opts.enCoding!=null){
			enCoding=opts.enCoding;
		}
		String fileOut = opts.outFileName;
		if(opts.method.equals("Syntax"))
		{
			System.out.println("now generating syntax relations xml file......");
			xml.genSyntaxRelation(fileIn, enCoding,fileOut);
		}
		else if(opts.method.equals("Semantic"))
		{
			System.out.println("now generating semantic relations xml file......");
			xml.genRelexRelation(fileIn, enCoding,fileOut);
		}else{
			System.out.println("unrecognized parameters of '-func'...");
		}
	}

}
