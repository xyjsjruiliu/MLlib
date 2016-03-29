package com.xy.lr.java.nlp.wordsegment.entity;

import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author xylr
 * 
 * Content 类处理如下的数据：
 * 
 * <content>
 * 	<sentence number="1">...</sentence>
 * 	<sentence number="0">...</sentence>
 * </content>
 * */
public class Content {
	//句子
	private ArrayList<Sentence> sentences;
	
	public Content() {
		sentences = new ArrayList<Sentence>();
	}

	/**
	 * 设置Content
	 * */
	public void setContent(Element xmlDocument) {
//		System.out.println(xmlDocument.getName());
		
		Iterator sentenceIt = xmlDocument.elementIterator();
		while(sentenceIt.hasNext()) {
			Element sentence = (Element) sentenceIt.next();
			
			if(!sentence.attributeValue("number").equals("0")){
				//新的句子
				Sentence sent = new Sentence();
				sent.setSentence(sentence);
				
				this.sentences.add(sent);
				
//				System.out.println(sentence.attributeValue("number"));
			}
		}
	}
	
	/**
	 * 输出
	 * */
	public void printAll(){
		for (Sentence sentence : sentences) {
			sentence.printAll();
		}
	}
	
	public ArrayList<Sentence> getSentences() {
		return sentences;
	}

	public void setSentences(ArrayList<Sentence> sentences) {
		this.sentences = sentences;
	}
}
