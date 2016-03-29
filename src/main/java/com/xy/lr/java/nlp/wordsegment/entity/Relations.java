package com.xy.lr.java.nlp.wordsegment.entity;

import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author xylr
 * 
 * Relations 类处理如下的数据：
 * 
 * <relations>
 * 	<rel id="1" type="CeCoexist_PER_ORG" from="877" to="802" weight="100.000000"/>
 * 	<rel id="2" type="CeCoexist_ORG_PER" from="802" to="877" weight="100.000000"/>
 * </relations>
 * */
public class Relations {
	//关系列表
	private ArrayList<Relation> relations;
	
	/**
	 * 构造函数
	 * */
	public Relations() {
		relations = new ArrayList<Relation>();
	}

	/**
	 * 设置当前Relation
	 * */
	public void setRelations(Element rel) {
		Iterator relationIt = rel.elementIterator();
		while(relationIt.hasNext()) {
			Element relation = (Element) relationIt.next();
			Relation relat = new Relation();
			relat.setRelation(relation);
			
			relations.add(relat);
		}
	}
	
	/**
	 * 输出
	 * */
	public void printAll() {
		for (Relation rel : relations) {
			rel.printAll();
		}
	}
	
	public ArrayList<Relation> getRelations() {
		return relations;
	}

	public void setRelations(ArrayList<Relation> relations) {
		this.relations = relations;
	}
}
