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
 * Token 类处理如下的数据：
 * <tok id="138" type="group">
 * 	<foot/>
 * 	<tok id="48" type="atom" soff="125" eoff="127">...</tok>
 * 	<tok id="97" type="group">...</tok>
 * 	<tok id="121" type="group">...</tok>
 * 	<tok id="106" type="group">...</tok>
 * 	<tok id="57" type="atom" soff="144" eoff="146">...</tok>
 * 	<lead/>
 * 	<tok id="58" type="atom" soff="147" eoff="149">
 * 		》
 * 		<norm/>	
 * 		<tok-normal-forms/>
 * 		<fea>
 * 			<f>w</f>
 * 			<f>wsy</f>
 * 		</fea>
 * 	</tok>
 * 	<norm/>
 * 	<tok-normal-forms/>
 * 	<fea>
 * 		<f>Ne_Noun</f>
 * 		<f>NePublication</f>
 * 		<f>NEORG_stopword</f>
 * 	</fea>
 * </tok>
 * */
public class Token {
	//Token的类型（atom、group）
	private String tokenType;
	//Token's ID
	private long tokenID;
	
	//当Token的Type是atom时，下面两个属性才有值
	private long tokenSoff;
	private long tokenEoff;
	
	//实际Token的值
	private String tokenData;
	
	private ArrayList<Token> tokens;
	
	//Token的属性
	private Feature feature;
	
	/**
	 * 构造函数，初始化
	 * */
	public Token () {
		tokenType = new String();	
		tokenID = 0;
		
		tokenSoff = 0;
		tokenEoff = 0;
		
		tokenData = new String();
		
		tokens = new ArrayList<Token>();
		
		feature = new Feature();
	}
	
	/**
	 * 设置一个新的Token
	 * */
	public void setTokens ( Element t ) {
		//设置Token的id和type
		this.tokenID = Long.valueOf(t.attributeValue("id"));
		this.tokenType = t.attributeValue("type");
		
		//如果Token的类型是Atom
		if ( this.tokenType.equals("atom") ) {
			solveAtomToken(t);
		}
		//如果Token的类型是Group
		else if ( this.tokenType.equals("group") ) {
			solveGroupToken(this, t);
		}
	}
	
	/**
	 * 设置Atom类型的Token
	 * */
	private void solveAtomToken ( Element tok ) {
//		System.out.println("--------------------------solveAtomToken--------------------------");
		
		//设置只有在Atom类型的Token中才有的值
		this.tokenSoff = Long.valueOf( tok.attributeValue("soff") );
		this.tokenEoff = Long.valueOf( tok.attributeValue("eoff") );
		//设置Token中实际的值
		this.tokenData = tok.getTextTrim();
		
		//遍历Atom类型的Token
		Iterator toksIt = tok.elementIterator();
		while( toksIt.hasNext() ) {
			Element toks = ( Element ) toksIt.next();
//			System.out.println(toks.getName());
			//设置Feature
			if( toks.getName().equals("fea") ) {
				Feature fea = new Feature();
				fea.setFeatures(toks);
				this.feature = fea;
			}	
		}
	}
	
	/**
	 * 设置Group类型的Token
	 * */
	private void solveGroupToken ( Token tok, Element t ) {
		//遍历Group类型的Token
		Iterator tokenIt = t.elementIterator();
		while( tokenIt.hasNext() ) {
			Element token = (Element) tokenIt.next();
//			System.out.println(token.getName());
			//类型为Group的Token中发现新的Token
			if(token.getName().equals("tok")){
				Token tokenss = new Token();
				tokenss.setTokens(token);
				this.tokens.add(tokenss);
			}
			//设置Group类型的Feature
			if(token.getName().equals("fea")) {
				Feature fea = new Feature();
				fea.setFeatures(token);
				tok.setFeature(fea);
			}
		}
	}
	
	/**
	 * 输出所有的数据
	 * */
	public void printAll () {
		System.out.println("tokenID : " + tokenID + ",tokenType : " + tokenType + ",tokenSoff : " + tokenSoff +
				",tokenEoff : " + tokenEoff);
		System.out.println("\t\ttokenData : " + tokenData );
		for (Token tok : tokens) {
			tok.printAll();
		}
		feature.printAll();
	}
	
	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public long getTokenID() {
		return tokenID;
	}

	public void setTokenID(long tokenID) {
		this.tokenID = tokenID;
	}

	public long getTokenSoff() {
		return tokenSoff;
	}

	public void setTokenSoff(long tokenSoff) {
		this.tokenSoff = tokenSoff;
	}

	public long getTokenEoff() {
		return tokenEoff;
	}

	public void setTokenEoff(long tokenEoff) {
		this.tokenEoff = tokenEoff;
	}

	public String getTokenData() {
		return tokenData;
	}

	public void setTokenData(String tokenData) {
		this.tokenData = tokenData;
	}

	public ArrayList<Token> getTokens() {
		return tokens;
	}

	public void setTokens(ArrayList<Token> tokens) {
		this.tokens = tokens;
	}

	public Feature getFeature() {
		return feature;
	}

	public void setFeature(Feature feature) {
		this.feature = feature;
	}

	/**
	 * 测试
	 * */
	public static void main(String[] args) throws DocumentException{
		Token token = new Token();
		
		//创建SAXReader的对象saxReader
		SAXReader saxReader = new SAXReader();
		//通过saxReader对象的
		Document document = saxReader.read( new File( "data/token.xml" ) );
		//通过document对象获取根节点orgProfileStore
		Element xmlRoot = document.getRootElement();
		
		token.setTokens(xmlRoot);
		
		token.printAll();
	}
}