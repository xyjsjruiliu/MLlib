package com.xy.lr.java.nlp.wordsegment;

import com.xy.lr.java.nlp.wordsegment.entity.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;

/**
 * @author xylr
 * 
 * XMLParser 类
 * */
public class XMLParser {
	//创建SAXReader的对象saxReader
	private SAXReader saxReader;
	
	private Document document;
	
	public XMLParser () {
		saxReader = new SAXReader();
	}
	
	/**
	 * 给定一个输入路径，解析xml数据，得到XMLDocument对象
	 * */
	public XMLDocument xmlParser(String filePath) throws DocumentException {
		//通过saxReader对象的
		document = saxReader.read( new File( filePath ) );
		//通过document对象获取根节点orgProfileStore
		Element xmlDocumentRoot = document.getRootElement();
		
		XMLDocument xmlDocument = new XMLDocument();
		xmlDocument.setXMLDocument(xmlDocumentRoot);
		
		return xmlDocument;
	}
	
	/**
	 * 输出XMLDocument对象中所有的数据信息
	 * */
	public void printAll(XMLDocument xmlDocument) {
		xmlDocument.printAll();
	}
	
	/**
	 * 获得分词列表
	 * */
	public ChineseWordSegment wordSegment(XMLDocument xmlDocument) {
		//分词的结果
		ChineseWordSegment chineseWordSegment = new ChineseWordSegment();
		
		//得到文档的内容
		Content content = xmlDocument.getContent();
		//得到文档中的所有句子
		ArrayList<Sentence> sentences = content.getSentences();
		//得到所有的Token列表
		ArrayList<Token> tokenList = getTokenList(sentences);
		//获得分词的结果
		chineseWordSegment.setWordSegmentList(tokenList);
		
		return chineseWordSegment;
	}
	
	/**
	 * 获得所有的一级Token列表
	 * */
	private ArrayList<Token> getTokenList(ArrayList<Sentence> sentences) {
		//所有的一级Token列表
		ArrayList<Token> tokenList = new ArrayList<Token>();
		//遍历所有的Sentence
		for(Sentence sentence : sentences) {
			ArrayList<Token> token = sentence.getTokens();
			tokenList.addAll(token);
//			System.out.println( sentence.getSentenceNumber() + "\t" + sentence.getTokens().size());
		}
		
		return tokenList;
	}
}
