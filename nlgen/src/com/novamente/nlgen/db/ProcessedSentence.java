package com.novamente.nlgen.db;

import java.util.*;
import java.io.*;

public class ProcessedSentence implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Integer id;
	Integer rank;
	ParseInfo info;
	Set<Link> links = new HashSet<Link>(0);
	Set<Relation> relations = new HashSet<Relation>(0);
	List<String> words = new ArrayList<String>(0);

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public ParseInfo getInfo() {
		return info;
	}

	public void setInfo(ParseInfo info) {
		this.info = info;
	}

	public List<String> getWords() {
		return words;
	}

	public void setWords(List<String> words) {
		this.words = words;
	}

	public Set<Link> getLinks() {
		return links;
	}

	public void setLinks(Set<Link> links) {
		this.links = links;
	}

	public Set<Relation> getRelations() {
		return relations;
	}

	public void setRelations(Set<Relation> relations) {
		this.relations = relations;
	}
}
