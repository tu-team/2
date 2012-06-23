package com.novamente.nlgen.util;

import java.util.ArrayList;
import java.util.List;

/*
 * author lianlian
 * 
 * This class is defined for the RelEx relationships of one sentence
 */

public class RelationSets {

	List<RelationExample> unaryRelations;
	List<RelationExample> binaryRelations;
	
	public RelationSets(){
		unaryRelations=new ArrayList<RelationExample>();
		binaryRelations=new ArrayList<RelationExample>();
	}

	public List<RelationExample> getUnaryRelations() {
		return unaryRelations;
	}

	public void setUnaryRelations(List<RelationExample> unaryRelations) {
		this.unaryRelations = unaryRelations;
	}

	public List<RelationExample> getBinaryRelations() {
		return binaryRelations;
	}

	public void setBinaryRelations(List<RelationExample> binaryRelations) {
		this.binaryRelations = binaryRelations;
	}
	
	public RelationSets filterRelations(List<RelationExample> relations){
		RelationSets rs=new RelationSets();
		for(RelationExample re: relations){
			if(re.binary)
				binaryRelations.add(re);
			else
				unaryRelations.add(re);
		}
		return rs;
	}

}
