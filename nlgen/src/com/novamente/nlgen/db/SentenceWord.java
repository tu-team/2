package com.novamente.nlgen.db;

import java.io.*;
import java.util.*;

public class SentenceWord implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Integer id;
	String word;
	Integer type = 0;
	Set<Link> leftLinks = new HashSet<Link>(0);
	Set<Link> rightLinks = new HashSet<Link>(0);
	public static final Integer WORD_TYPE_NORMAL = 0;
	public static final Integer WORD_TYPE_PERSON = 1;
	public static final Integer WORD_TYPE_ORGNIZATION = 2;
	public static final Integer WORD_TYPE_LOCATION = 4;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof SentenceWord)) {
			return false;
		} else {
			SentenceWord des = (SentenceWord) obj;
			return this.getWord().equals(des.getWord());
		}
	}

	public Set<Link> getLeftLinks() {
		return leftLinks;
	}

	public void setLeftLinks(Set<Link> leftLinks) {
		this.leftLinks = leftLinks;
	}

	public Set<Link> getRightLinks() {
		return rightLinks;
	}

	public void setRightLinks(Set<Link> rightLinks) {
		this.rightLinks = rightLinks;
	}
}
