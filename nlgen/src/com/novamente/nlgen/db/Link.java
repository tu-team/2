package com.novamente.nlgen.db;
import java.io.*;
public class Link implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Integer id;
	ProcessedSentence sentence;
	String label;
	SentenceWord leftWord;
	SentenceWord rightWord;
	String leftPOS;
	String rightPOS;
	Relation relation;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public ProcessedSentence getSentence() {
		return sentence;
	}
	public void setSentence(ProcessedSentence sentence) {
		this.sentence = sentence;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public SentenceWord getLeftWord() {
		return leftWord;
	}
	public void setLeftWord(SentenceWord leftWord) {
		this.leftWord = leftWord;
	}
	public SentenceWord getRightWord() {
		return rightWord;
	}
	public void setRightWord(SentenceWord rightWord) {
		this.rightWord = rightWord;
	}
	public String getLeftPOS() {
		return leftPOS;
	}
	public void setLeftPOS(String leftPOS) {
		this.leftPOS = leftPOS;
	}
	public String getRightPOS() {
		return rightPOS;
	}
	public void setRightPOS(String rightPOS) {
		this.rightPOS = rightPOS;
	}
	public Relation getRelation() {
		return relation;
	}
	public void setRelation(Relation relation) {
		this.relation = relation;
	}
}
