package com.xy.lr.java.nlp.profile.entity;

import com.xy.lr.java.nlp.constant.ProfileConstantProperty;
import org.dom4j.Element;

/**
 * SourceDocument 解析如下的一个实例
 * 
 * <source doc="NLPCCraw_fenliehou_utf-8/140/13946.txt" id="1" pubtime="20151201173228" 
 * realtime="20151201000000">【瓯海档案馆添新料】今天，瓯海区档案新馆收到原中国音乐家协会会员林虹、
 * 原温州地理文化研究者林城银先生家属和中国新闻摄影学会会员郑金寿捐赠的档案资料。</source>
 * 
 * SourceDocument sourceDocument = new SourceDocument();
 * sourceDocument.setSourceDoc("NLPCCraw_fenliehou_utf-8/140/13946.txt");
 * sourceDocument.setSourceDocID(1);
 * sourceDocument.setSourceDocPubtime("20151201173228");
 * sourceDocument.setSourceDocRealtime("20151201000000");
 * sourceDocument.setSourceValue("瓯海档案馆添新料】今天，瓯海区档案新馆收到原中国音乐家协会会员林虹、" + 
 * 	"原温州地理文化研究者林城银先生家属和中国新闻摄影学会会员郑金寿捐赠的档案资料。");
 * 
 * */
public class SourceDocument {
	//sourceDoc 原始文件路径
	private String sourceDoc;
	
	//sourceDocID
	private long sourceDocID;
	
	//sourceDocPubtime
	private String sourceDocPubtime;
	
	//sourceDocRealtime
	private String sourceDocRealtime;
	
	//sourceValue
	private String sourceValue;
	
	//sourceVerb
	private String sourceVerb;

	/**
	 * 无参的构造函数
	 * */
	public SourceDocument() {
		this.sourceDoc = new String("");
		this.sourceDocID = 0;
		this.sourceDocPubtime = new String("");
		this.sourceDocRealtime = new String("");
		this.sourceValue = new String("");
	}
	
	/**
	 * 有参的构造函数
	 * */
	public SourceDocument(String sourceDoc, long sourceDocID, 
			String sourceDocPubtime, String sourceDocRealtime, 
			String sourceValue, String sourceVerb) {
		this.sourceDoc = sourceDoc;
		this.sourceDocID = sourceDocID;
		this.sourceDocPubtime = sourceDocPubtime;
		this.sourceDocRealtime = sourceDocRealtime;
		this.sourceValue = sourceValue;
		this.sourceVerb = sourceVerb;
	}
	
	/**
	 * 输出sourceDocument对象中的所有数据
	 * */
	public void printlnAll () {
		System.out.println( "\t" + "sourceDoc : " + sourceDoc + " , sourceDocID : " + sourceDocID + ""
				+ " , sourceDocPubtime : " + sourceDocPubtime + " , sourceDocRealtime : " + ""
						+ " , sourceValue : " + sourceValue + " , sourceVerb : " + sourceVerb);
	}
	
	/**
	 * 输出sourceDocument对象中的所有数据
	 * */
	public void printlnAAll () {
		System.out.println( "\t\t" + "sourceDoc : " + sourceDoc + " , sourceDocID : " + sourceDocID + ""
				+ " , sourceDocPubtime : " + sourceDocPubtime + " , sourceDocRealtime : " + ""
						+ " , sourceValue : " + sourceValue + " , sourceVerb : " + sourceVerb);
	}
	
	/**
	 * 通过一个Element对象解析出SourceDocument数据
	 * */
	public void setSourceDocument (Element element) {
		this.sourceDoc = element.attributeValue( ProfileConstantProperty.PROFILE_SOURCE_DOC );
		this.sourceDocID = Long.valueOf( element.attributeValue(ProfileConstantProperty.PROFILE_SOURCE_ID) );
		this.sourceDocPubtime = element.attributeValue(ProfileConstantProperty.PROFILE_SOURCE_PUBTIME);
		this.sourceDocRealtime = element.attributeValue(ProfileConstantProperty.PROFILE_SOURCE_REALTIME);
		this.sourceValue = element.getStringValue();
		this.sourceVerb = element.attributeValue(ProfileConstantProperty.PROFILE_SOURCE_VERB);
	}
	
	/**
	 * 
	 * @param element
	 */
	public void addSourceDocument( Element element ) {
		element.addAttribute(ProfileConstantProperty.PROFILE_SOURCE_DOC, this.sourceDoc);
		element.addAttribute(ProfileConstantProperty.PROFILE_SOURCE_ID, String.valueOf(this.sourceDocID));
		element.addAttribute(ProfileConstantProperty.PROFILE_SOURCE_PUBTIME, this.sourceDocPubtime);
		element.addAttribute(ProfileConstantProperty.PROFILE_SOURCE_REALTIME, this.sourceDocRealtime);
		element.addAttribute(ProfileConstantProperty.PROFILE_SOURCE_VERB, this.sourceVerb);
		element.setText(this.sourceValue);
	}
	
	/**
	 * get方法
	 * */
	public String getSourceDoc() {
		return sourceDoc;
	}

	/**
	 * set方法
	 * */
	public void setSourceDoc(String sourceDoc) {
		this.sourceDoc = sourceDoc;
	}

	/**
	 * get方法
	 * */
	public long getSourceDocID() {
		return sourceDocID;
	}

	/**
	 * set方法
	 * */
	public void setSourceDocID(long sourceDocID) {
		this.sourceDocID = sourceDocID;
	}

	/**
	 * get方法
	 * */
	public String getSourceDocPubtime() {
		return sourceDocPubtime;
	}

	/**
	 * set方法
	 * */
	public void setSourceDocPubtime(String sourceDocPubtime) {
		this.sourceDocPubtime = sourceDocPubtime;
	}

	/**
	 * get方法
	 * */
	public String getSourceDocRealtime() {
		return sourceDocRealtime;
	}

	/**
	 * set方法
	 * */
	public void setSourceDocRealtime(String sourceDocRealtime) {
		this.sourceDocRealtime = sourceDocRealtime;
	}

	/**
	 * get方法
	 * */
	public String getSourceValue() {
		return sourceValue;
	}

	/**
	 * set方法
	 * */
	public void setSourceValue(String sourceValue) {
		this.sourceValue = sourceValue;
	}
	
	/**
	 * get方法
	 * */
	public String getSourceVerb() {
		return sourceVerb;
	}

	/**
	 * set方法
	 * */
	public void setSourceVerb(String sourceVerb) {
		this.sourceVerb = sourceVerb;
	}

	/**
	 * 测试的main方法
	 * */
	public static void main(String[] args){
		SourceDocument sourceDocument = new SourceDocument();
		
		sourceDocument.setSourceDoc("NLPCCraw_fenliehou_utf-8/140/13946.txt");
		sourceDocument.setSourceDocID(1);
		sourceDocument.setSourceDocPubtime("20151201173228");
		sourceDocument.setSourceDocRealtime("20151201000000");
		sourceDocument.setSourceValue("瓯海档案馆添新料】今天，瓯海区档案新馆收到原中国音乐家协会会员林虹、" 
		+ "原温州地理文化研究者林城银先生家属和中国新闻摄影学会会员郑金寿捐赠的档案资料。");
		sourceDocument.setSourceVerb("到");
	}
}
