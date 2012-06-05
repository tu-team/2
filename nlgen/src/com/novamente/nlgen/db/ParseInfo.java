package com.novamente.nlgen.db;

import java.io.*;
import java.util.*;

public class ParseInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String convertedSentence;
	Integer id;
	@SuppressWarnings("unchecked")
	List processResults = new ArrayList(0);
	String sentence;

	public Integer getId() {
		return id;
	}

	@SuppressWarnings("unchecked")
	public List getProcessResults() {
		return processResults;
	}

	public String getSentence() {
		return sentence;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@SuppressWarnings("unchecked")
	public void setProcessResults(List processResults) {
		this.processResults = processResults;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public String getConvertedSentence() {
		return convertedSentence;
	}

	public void setConvertedSentence(String convertedSentence) {
		this.convertedSentence = convertedSentence;
	}
}
