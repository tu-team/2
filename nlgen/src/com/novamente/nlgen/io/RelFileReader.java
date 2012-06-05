/**
 * 
 */
package com.novamente.nlgen.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.novamente.nlgen.util.RelationExample;

/**
 * @author lianlian
 *
 */
public class RelFileReader {
	
	public static List<RelationExample> readRelFromFile(String relFileName, String enCoding) throws StringIndexOutOfBoundsException{
		Vector<String> vec=FileTool.readLine(relFileName, enCoding);	
		return readRel(vec, enCoding);
	}
	
	public static List<RelationExample> readRelFromString(String relString, String enCoding) throws StringIndexOutOfBoundsException{
		Vector<String> vec=new Vector<String>();
		for(String str : relString.split("\\n")){
			vec.add(str);
		}
		return readRel(vec, enCoding);
	}
	
	public static List<RelationExample> readRel(Vector<String> vec, String enCoding) throws StringIndexOutOfBoundsException {
		List<RelationExample> relations=new ArrayList<RelationExample>();
		
		for(String str: vec){
			str.trim();
			
			int i = str.indexOf("(");
			int j = str.lastIndexOf(",");
			int k = str.indexOf(")");
			if(j == -1) j = k;//if there is no comma, then replace the j value
			
			String w1 = str.substring(i + 1,j).trim();
			String w2  = "";
			if(j != k)//only if there is a comma
				w2 = str.substring(j + 1, k).trim();
			
			String l = str.substring(0, i).trim();
			
			RelationExample rel = new RelationExample(w1, w2, l);
			rel.setBinary(rel.isBinary());
			relations.add(rel);
		}
		return relations;		
	}

}
