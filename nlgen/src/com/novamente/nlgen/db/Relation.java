package com.novamente.nlgen.db;

import java.io.*;
import java.util.*;

public class Relation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public final Integer RELATION_TYPE_UNARY = 0;
	public final Integer RELATION_TYPE_BINARY = 1;
	Integer id;
	String label;
	Integer type = 1;
	SentenceWord word1;
	SentenceWord word2;
	String POS1;
	String POS2;
	ProcessedSentence sentence;
	List<Link> links = new ArrayList<Link>(0);

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public SentenceWord getWord1() {
		return word1;
	}

	public void setWord1(SentenceWord word1) {
		this.word1 = word1;
	}

	public SentenceWord getWord2() {
		return word2;
	}

	public void setWord2(SentenceWord word2) {
		this.word2 = word2;
	}

	public String getPOS1() {
		return POS1;
	}

	public void setPOS1(String pos1) {
		POS1 = pos1;
	}

	public String getPOS2() {
		return POS2;
	}

	public void setPOS2(String pos2) {
		POS2 = pos2;
	}

	public ProcessedSentence getSentence() {
		return sentence;
	}

	public void setSentence(ProcessedSentence sentence) {
		this.sentence = sentence;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(this.label);
		buf.append("(");
		buf.append(this.word1.getWord());
		if (this.type.equals(this.RELATION_TYPE_BINARY)) {
			try {
				buf.append(",");
				buf.append(this.word2.getWord());
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		buf.append(")");
		return new String(buf);
	}
}
