package com.xy.lr.java.nlp.wordsegment.entity;

import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author xylr
 * 
 * Sentence 类处理如下的数据：
 * 
 * <sentence number="1">
 * 	<tok id="1" type="atom" soff="0" eoff="2">...</tok>
 * 	<tok id="108" type="group">...</tok>
 * 	<tok id="4" type="atom" soff="9" eoff="11">...</tok>
 * 	<tok id="95" type="group">...</tok>
 * 	<tok id="109" type="group">...</tok>
 * 	<tok id="11" type="atom" soff="22" eoff="24">...</tok>
 * 	<tok id="110" type="group">...</tok>
 * 	<tok id="111" type="group">...</tok>
 * 	<tok id="16" type="atom" soff="37" eoff="39">...</tok>
 * 	<tok id="112" type="group">...</tok>	
 * 	<tok id="113" type="group">...</tok>
 * 	<tok id="21" type="atom" soff="52" eoff="54">...</tok>
 * 	<tok id="22" type="atom" soff="55" eoff="57">...</tok>
 * 	<tok id="96" type="group">...</tok>
 * 	<tok id="114" type="group">...</tok>
 * 	<tok id="29" type="atom" soff="68" eoff="70">...</tok>
 * 	<tok id="115" type="group">...</tok>
 * 	<tok id="32" type="atom" soff="77" eoff="79">...</tok>
 * 	<tok id="116" type="group">...</tok>
 * 	<tok id="35" type="atom" soff="86" eoff="88">...</tok>
 * 	<tok id="117" type="group">...</tok>
 * 	<tok id="38" type="atom" soff="95" eoff="97">...</tok>
 * 	<tok id="39" type="atom" soff="98" eoff="100">...</tok>
 * 	<tok id="118" type="group">...</tok>
 * 	<tok id="42" type="atom" soff="107" eoff="109">...</tok>
 * 	<tok id="119" type="group">...</tok>
 * 	<tok id="45" type="atom" soff="116" eoff="118">...</tok>
 * 	<tok id="120" type="group">...</tok>
 * 	<tok id="138" type="group">...</tok>
 * 	<tok id="59" type="atom" soff="150" eoff="152">...</tok>
 * 	<tok id="139" type="group">...</tok>
 * 	<tok id="71" type="atom" soff="178" eoff="180">...</tok>
 * 	<tok id="72" type="atom" soff="181" eoff="183">...</tok>
 * 	<tok id="123" type="group">...</tok>
 * 	<tok id="75" type="atom" soff="190" eoff="192">...</tok>
 * 	<tok id="124" type="group">...</tok>
 * 	<tok id="125" type="group">...</tok>
 * 	<tok id="126" type="group">...</tok>
 * 	<tok id="127" type="group">...</tok>
 * 	<tok id="128" type="group">...</tok>
 * 	<tok id="86" type="atom" soff="223" eoff="225">...</tok>
 * 	<tok id="87" type="atom" soff="226" eoff="228">...</tok>
 * 	<tok id="88" type="atom" soff="229" eoff="231">...</tok>
 * 	<tok id="89" type="atom" soff="232" eoff="234">...</tok>
 * 	<tok id="129" type="group">...</tok>
 * 	<tok id="92" type="atom" soff="241" eoff="243">...</tok>
 * </sentence>
 * */
public class Sentence {
	//句子的编号
	private long sentenceNumber;
	
	//句子里面包含的Token
	private ArrayList<Token> tokens;
	
	/**
	 * 构造函数
	 * */
	public Sentence() {
		this.sentenceNumber = 0;
		this.tokens = new ArrayList<Token>();
	}
	
	/**
	 * 设置句子
	 * */
	public void setSentence(Element xmlSentence) {
		this.sentenceNumber = Long.valueOf(xmlSentence.attributeValue("number"));
		
		//遍历每一个Token
		Iterator tokenListIt = xmlSentence.elementIterator();
		while(tokenListIt.hasNext()) {
			//Sentence的每一个Token
			Element tokenList = (Element) tokenListIt.next();
			
			//生成一个新的Token
			Token token = new Token();
			token.setTokens(tokenList);
			
			tokens.add(token);
		}
	}

	/**
	 * 输出
	 * */
	public void printAll() {
		System.out.println(this.sentenceNumber);
		for(Token t : tokens) {
			t.printAll();
		}
	}
	
	public long getSentenceNumber() {
		return sentenceNumber;
	}

	public void setSentenceNumber(long sentenceNumber) {
		this.sentenceNumber = sentenceNumber;
	}

	public ArrayList<Token> getTokens() {
		return tokens;
	}

	public void setTokens(ArrayList<Token> tokens) {
		this.tokens = tokens;
	}
}