package com.xy.lr.java.nlp.wordsegment.entity;

import java.io.*;
import java.util.ArrayList;

/**
 * @author xylr
 * 
 * ChineseWordSegmentation 类
 * */
public class ChineseWordSegment {
	//分词结果
	private ArrayList<XMLWord> xmlWords;
	
	/**
	 * 构造函数，初始化
	 * */
	public ChineseWordSegment() {
		this.xmlWords = new ArrayList<XMLWord>();
	}

	public void appendChineseWordSegmentToFile (String fileName, String filePath) {
		try {
			//打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(filePath, true);
			writer.write(fileName + "\t" +getWordList() + "\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 将分词的结果保存到文件当中
	 * */
	public void saveChineseWordSegmentToFile (String filePath) {
		BufferedWriter writer = null;
		FileOutputStream writerStream = null;
		try {
			//打开文件输出流
			writerStream = new FileOutputStream(filePath);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			writer = new BufferedWriter(
				     new OutputStreamWriter(writerStream, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			String temp = getWordList();
			
			writer.write(temp);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * wordList
	 * */
	public String getWordList() {
		String temp = new String();
		for (XMLWord xmlWord : xmlWords) {
			if(!xmlWord.getWord().equals("")) {
				temp += xmlWord.getWord() + " ";
			}
		}
//		System.out.println(temp);
		if(temp.length() <= 0){
			temp = temp;
		}else{
			temp = temp.substring(0, temp.length() - 1);
		}
		
		return temp;
	}
	
	/**
	 * 根据一级的Token列表，得到分词结果
	 * */
	public void setWordSegmentList ( ArrayList<Token> tokenList ) {
		for ( Token token : tokenList ) {
			//如果Token的类型是Atom
			if ( token.getTokenType().equals("atom") ) {
				//设置分词结果
				setWordSegmentByAtomToken(token);
//				System.out.println( token.getTokenType() + "\t" + token.getTokenData() );
			}
			//如果Token的类型是Group
			else if( token.getTokenType().equals("group") ) {
				setWordSegmentByGroupToken(token);
			}
		}
	}
	
	/**
	 * 从Atom类型的Token中抽取出分词结果
	 * */
	private void setWordSegmentByAtomToken ( Token atomToken ) {
		XMLWord xmlword = new XMLWord();
		//设置新的分词结果
		boolean answer = xmlword.setXMLWordByToken(atomToken);
		//去除停用词的操作
		if( answer == true ) {
			xmlWords.add(xmlword);
		}
		else {
			xmlword = null;
		}
	}
	
	/**
	 * 从Group类型的Token中抽取出分词结果
	 * */
	private void setWordSegmentByGroupToken ( Token groupToken ) {
		XMLWord xmlword = new XMLWord();
		//TokenList
		ArrayList<Token> tokenList = groupToken.getTokens();
		//Feature
		Feature features = groupToken.getFeature();
		//检查Group里面是否还有其他的groupToken
		boolean flag = checkGroupToken(tokenList);
		
		//表示这个Group类型的Token里面没有Group类型的Token
		if(!flag) {
			xmlword.setXMLWordByTokenList(tokenList, features);
			xmlWords.add(xmlword);
		}
		//表示这个Group类型的Token里面有Group类型的Token
		else {
			recursionGroupToken(tokenList, xmlword);
		}
	}
	
	/**
	 * 检查Group里面是否还有其他的groupToken
	 * */
	private boolean checkGroupToken(ArrayList<Token> tokenList) {
		boolean flag = false;
		//遍历groupToken里面的所有TokenList
		for (Token token : tokenList) {
			if(token.getTokenType().equals("group")){
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * 递归处理Group类型的Token里面的GroupToken
	 * */
	private void recursionGroupToken(ArrayList<Token> tokenList, XMLWord xmlword) {
		for (Token token : tokenList) {
			//发现Group类型的Token，递归遍历
			if(token.getTokenType().equals("group")){
				setWordSegmentByGroupToken(token);
			}
			//发现Atom类型的Token
			else {
				xmlword.setXMLWordByToken(token);
				xmlWords.add(xmlword);
			}
		}
	}
	
	/**
	 * 输出结果
	 * */
	public void printAll () {
		for (XMLWord xmlWord : xmlWords) {
			xmlWord.printAll();
			System.out.println();
		}
	}

	public ArrayList<XMLWord> getXmlWords() {
		return xmlWords;
	}

	public void setXmlWords(ArrayList<XMLWord> xmlWords) {
		this.xmlWords = xmlWords;
	}
}
