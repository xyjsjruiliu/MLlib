package com.xy.lr.java.nlp.wordsegment.entity;

import org.dom4j.Element;

/**
 * @author xylr
 * 
 * Relation 类处理如下的数据：
 * 
 * <rel id="1" type="CeCoexist_PER_ORG" from="877" to="802" weight="100.000000"/>
 * */
public class Relation {
	//关系编号
	private long relationID;
	
	//关系类型
	private String relationType;
	
	//关系From
	private long relationFrom;
	
	//关系To
	private long relationTo;
	
	//关系的权重
	private double relationWeight;
	
	/**
	 * 构造函数
	 * */
	public Relation() {
		relationID = 0L;
		relationType = new String();
		relationFrom = 0L;
		relationTo = 0L;
		relationWeight = 0.0;
	}
	
	/**
	 * 设置当前关系
	 * */
	public void setRelation(Element rel) {
		this.relationID = Long.valueOf(rel.attributeValue("id"));
		this.relationType = rel.attributeValue("type");
		this.relationFrom = Long.valueOf(rel.attributeValue("from"));
		this.relationTo = Long.valueOf(rel.attributeValue("to"));
		this.relationWeight = Double.valueOf(rel.attributeValue("weight"));
	}
	
	/**
	 * 输出
	 * */
	public void printAll(){
		System.out.println("relationID : " + relationID + ",relationType : " + relationType + ","
				+ "relationFrom : " + relationFrom + ",relationTo : " + relationTo + ",relationWeight : " 
				+ relationWeight);
	}

	public long getRelationID() {
		return relationID;
	}

	public void setRelationID(long relationID) {
		this.relationID = relationID;
	}

	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	public long getRelationFrom() {
		return relationFrom;
	}

	public void setRelationFrom(long relationFrom) {
		this.relationFrom = relationFrom;
	}

	public long getRelationTo() {
		return relationTo;
	}

	public void setRelationTo(long relationTo) {
		this.relationTo = relationTo;
	}

	public double getRelationWeight() {
		return relationWeight;
	}

	public void setRelationWeight(double relationWeight) {
		this.relationWeight = relationWeight;
	}
}
