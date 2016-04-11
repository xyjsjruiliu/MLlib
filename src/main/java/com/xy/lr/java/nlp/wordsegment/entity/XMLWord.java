package com.xy.lr.java.nlp.wordsegment.entity;

import com.xy.lr.java.nlp.stopwords.StopWords;
import com.xy.lr.java.string.StringUtils;

import java.util.ArrayList;

/**
 * @author xylr
 * 
 * XMLWord 类
 * */
public class XMLWord {
	private StopWords stopWords;
	//每一个分词结果
	private String word;
	
	//分词结果的属性
	private ArrayList<String> features;
	
	/**
	 * 构造函数，初始化
	 * */
	public XMLWord () {
		stopWords = new StopWords();
		this.word = new String();
		this.features = new ArrayList<String>();
	}
	
	/**
	 * 通过给定的Token设置分词
	 * */
	public boolean setXMLWordByToken (Token token) {
		//去除停用词
		if ( token.getFeature().containsFeature("w") || token.getFeature().containsFeature("wyy") ||
				token.getFeature().containsFeature("NEORG_stopword") ){
			return false;
		}
		else {
			if (this.stopWords.containStopWord(token.getTokenData()) || !StringUtils.ChineseMatch(token.getTokenData())){
				return false;
			}
			this.features = token.getFeature().getFeatures();
			this.word = token.getTokenData();

			return true;
		}
	}
	
	/**
	 * 通过给定的TokenList设置分词
	 * */
	public boolean setXMLWordByTokenList ( ArrayList<Token> tokenList, Feature features ) {
		//去除停用词
		if(features.containsFeature("w") || features.containsFeature("wyy") ||
				features.containsFeature("NEORG_stopword")) {
			return false;
		}
		else {
			//遍历tokenList下面的所有词
			String word = new String();
			for (Token token : tokenList) {
//				System.out.println(token.getTokenData());
				word += token.getTokenData();
			}

			if (this.stopWords.containStopWord(word) || !StringUtils.ChineseMatch(word)) {
				return false;
			}
			this.features = features.getFeatures();
			this.word = word;
			return true;
		}
	}
	
	/**
	 * 输出数据
	 * */
	public void printAll() {
		System.out.print("word : " + this.word + ",Features : ");
		for (String feature : features) {
			System.out.print(" " + feature);
		}
	}
	
	/**
	 * get、set方法
	 * */
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public ArrayList<String> getFeatures() {
		return features;
	}

	public void setFeatures(ArrayList<String> features) {
		this.features = features;
	}

	public StopWords getStopWords() {
		return this.stopWords;
	}
	
	/**
	 * 测试
	 * */
	public static void main(String[] args) {
		XMLWord word = new XMLWord();
		word.setWord("分词");
		ArrayList<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		word.setFeatures(list);

		StopWords stopWords = word.getStopWords();

		System.out.println(stopWords.containStopWord("的"));
		
		word.printAll();
	}
}
