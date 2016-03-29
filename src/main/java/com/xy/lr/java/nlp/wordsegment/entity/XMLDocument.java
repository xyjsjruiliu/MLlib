package com.xy.lr.java.nlp.wordsegment.entity;

import org.dom4j.Element;

import java.util.Iterator;

/**
 * @author xylr
 * 
 * Document 类处理如下的数据：
 * 
 * <document id="0">
 * 	<content>
 * 		<sentence number="1">...</sentence>
 * 		<sentence number="0">...</sentence>
 * 	</content>
 * 	<relations>
 * 		<rel id="1" type="CeCoexist_PER_ORG" from="877" to="802" weight="100.000000"/>
 * 		<rel id="2" type="CeCoexist_ORG_PER" from="802" to="877" weight="100.000000"/>
 * 	</relations>
 * 	<normal-forms/>
 * </document>
 * */
public class XMLDocument {
	//文档编号
	private long documentID;
	//文档内容信息
	private Content content;
	//文档内容关系信息
	private Relations relations;
	
	/**
	 * 构造函数
	 * */
	public XMLDocument() {
		this.documentID = 0;
		this.content = new Content();
		this.relations = new Relations();
	}
	
	/**
	 * 设置当前的Document
	 * */
	public void setXMLDocument(Element xmlDocumentRoot) {
//		System.out.println(xmlDocumentRoot.getName() + "\t" + xmlDocumentRoot.attributeValue("id"));
		//设置ID
		this.documentID = Long.valueOf(xmlDocumentRoot.attributeValue("id"));
		
		//遍历Document中的所有数据
		Iterator xmlDocumentIt = xmlDocumentRoot.elementIterator();
		while(xmlDocumentIt.hasNext()) {
			Element xmlDocument = (Element) xmlDocumentIt.next();
//			System.out.println(xmlDocument.getName());
			
			//设置Document的Content
			if(xmlDocument.getName().equals("content")){
				Content content = new Content();
				content.setContent(xmlDocument);
				this.content = content;
			}
			//设置Document的relations
			else if (xmlDocument.getName().equals("relations")){
				Relations relations = new Relations();
				relations.setRelations(xmlDocument);
				this.relations = relations;
			}else if (xmlDocument.getName().equals("normal-forms")){
			}else{
				System.err.println("ERROR!!!");
			}
		}
	}

	/**
	 * 输出所有的信息
	 * */
	public void printAll(){
		this.content.printAll();
		this.relations.printAll();
	}
	
	public long getDocumentID() {
		return documentID;
	}

	public void setDocumentID(long documentID) {
		this.documentID = documentID;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public Relations getRelations() {
		return relations;
	}

	public void setRelations(Relations relations) {
		this.relations = relations;
	}
}