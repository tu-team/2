package com.novamente.nlgen.unary;


import java.util.HashSet;
//import java.util.List;
import java.util.Set;

/*import com.novamente.nlgen.gen.WordNode;
import com.novamente.nlgen.match.RelationExample;*/

import com.novamente.nlgen.util.WordNode;;

public abstract class AbstractAlgorithm {

	String name;

	public abstract WordNode apply(WordNode node, WordNode preNode);

	String description;
	String include;
	String exclude;
	String type;
	Set<String> includeSet = new HashSet<String>();
	Set<String> excludeSet = new HashSet<String>();

	public AbstractAlgorithm() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInclude() {
		return include;
	}

	public void setInclude(String include) {
		this.include = include;
		String[] includeArr=include.split(",");
		includeSet.clear();
		for(String item:includeArr){
			includeSet.add(item.trim());
		}
	}

	public String getExclude() {
		return exclude;
	}

	public void setExclude(String exclude) {
		this.exclude = exclude;
		String[] excludeArr=exclude.split(",");
		excludeSet.clear();
		for(String item:excludeArr){
			excludeSet.add(item.trim());
		}
	}

	public boolean canApply(WordNode word) {
		if(word.getOriginWord().equals("is")){
		}
/*		List<RelationExample> temp =word.getUnaryRelations();
		System.out.println("***********");
		for(RelationExample re:temp){
			System.out.println("label: "+re.getLabel());
		}
		System.out.println("***********");*/
		for(String inc:includeSet){
			if(!word.containsRelation(inc)){
				return false;
			}
		}
		for(String exc:excludeSet){
			if(word.containsRelation(exc)){
				return false;
			}
		}
		return true;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}