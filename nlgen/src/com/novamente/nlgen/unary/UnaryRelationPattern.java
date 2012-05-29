package com.novamente.nlgen.unary;

/*import java.util.*;

 import com.novamente.nlgen.util.Pair;*/


public class UnaryRelationPattern {
	private String pattern;
//	private List<Pair<String,String>> examples=new LinkedList<Pair<String,String>>();
	private int exampleCount;
	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

/*	public List<Pair<String, String>> getExamples() {
		return examples;
	}

	public void setExamples(List<Pair<String, String>> examples) {
		this.examples = examples;
	}*/

	public int getExampleCount() {
		return exampleCount;
	}

	public void setExampleCount(int exampleCount) {
		this.exampleCount = exampleCount;
	}
	public String toString(){
		return pattern;
	}
}
