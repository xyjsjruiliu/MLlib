package com.xy.lr.java.nlp.wordsegment.entity;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author xylr
 * 
 * Token's Feature
 * */
public class Feature {
	//属性集合
	private ArrayList<String> features;
	
	/**
	 * 构造函数
	 * */
	public Feature () {
		features = new ArrayList<String>();
	}
	
	
	/**
	 * 判断属性列表中是否含有feature属性
	 * */
	public boolean containsFeature ( String feature ) {
		return features.contains(feature);
	}
	
	/**
	 * 通过一个Element 对象设置Feature数据
	 * */
	public void setFeatures( Element fea ) {
		//遍历每一个feature
		Iterator featureIt = fea.elementIterator();
		while( featureIt.hasNext() ) {
			Element feature = ( Element ) featureIt.next();
			
			String feas = feature.getStringValue();
			features.add(feas);
		}
	}
	
	public void printAll () {
		for ( String f : features ) {
			System.out.println(f);
		}
	}

	public ArrayList<String> getFeatures() {
		return features;
	}

	public void setFeatures(ArrayList<String> features) {
		this.features = features;
	}
	
	public static void main(String[] args) throws DocumentException {
		//创建SAXReader的对象saxReader
		SAXReader saxReader = new SAXReader();
		//通过saxReader对象的
    	Document document = saxReader.read( new File( "data/feature.xml" ) );
    	//通过document对象获取根节点orgProfileStore
    	Element ProfileRoot = document.getRootElement();
    	
    	Feature fea = new Feature();
    	fea.setFeatures(ProfileRoot);
    	fea.printAll();
    	
    	System.out.println(fea.containsFeature("Ne_Noun"));
	}
}
