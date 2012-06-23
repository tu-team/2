package com.novamente.nlgen.db;


import junit.framework.TestCase;

import java.util.*;


public class DatabaseTests extends TestCase {
	@SuppressWarnings("unchecked")
	public void testDAO(){
		BaseHibernateDAO dao=new BaseHibernateDAO();
		dao.getSession().beginTransaction();
		List<String> words=dao.lookupByProperty(SentenceWord.class, "word", "test");
		for(Object obj:words){
			dao.delete(obj);
		}
		SentenceWord word=new SentenceWord();
		word.setWord("test");
		word.setType(SentenceWord.WORD_TYPE_NORMAL);
		dao.save(word);
		dao.getSession().getTransaction().commit();
		words=dao.lookupByProperty(SentenceWord.class, "word", "test");
		assertEquals(words.size(),1);
	}
	
}
