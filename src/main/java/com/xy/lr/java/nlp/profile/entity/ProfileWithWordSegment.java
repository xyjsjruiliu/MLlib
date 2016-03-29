package com.xy.lr.java.nlp.profile.entity;

import com.xy.lr.java.nlp.constant.ProfileConstantProperty;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;

public class ProfileWithWordSegment extends ProfileExample{
	//sourceDocument的分词结果
	protected String sourceDocumentWordSegment;

	public ProfileWithWordSegment() {
		super();
		this.sourceDocumentWordSegment = new String();
	}
	
	/**
	 * 
	 * @return
	 */
	public String getSourceDocumentWordSegment() {
		return sourceDocumentWordSegment;
	}

	/**
	 * 
	 * @param sourceDocumentWordSegment
	 */
	public void setSourceDocumentWordSegment(String sourceDocumentWordSegment) {
		this.sourceDocumentWordSegment = sourceDocumentWordSegment;
	}
	
	/**
	 * 输出所有的信息
	 */
	public void printlnAll() {
		System.out.println("profileId : " + profileId + " , profileType : " + profileType + " , profileSubType" + " : "
				+ profileSubType);
		System.out.println("\t" + "profileName : " + profileName);
		sourceDocument.printlnAll();
		System.out.println("\t" + "sourceDocumentWordSegment : " + sourceDocumentWordSegment);
		System.out.println("\t" + "mergeCount : " + mergeCount);
		System.out.println("\t" + "mentions : " + mentions);
		System.out.println("\t" + "profileVip : " + profileVip);
		for (ProfileRelation profileRelation : profileRelations) {
			profileRelation.printlnAll();
		}
	}
	
	/**
	 * 通过Element 对象设置 Profile中数据
	 * @param ProfileRoot
	 */
	public void setProfile(Element ProfileRoot) {
		Iterator ProfileIt = ProfileRoot.elementIterator();

		// 设置ID、Type、SubType
		setProfileIDAndTypeAndSubType(ProfileRoot.attributeValue(ProfileConstantProperty.PROFILE_ID),
				ProfileRoot.attributeValue(ProfileConstantProperty.PROFILE_TYPE),
				ProfileRoot.attributeValue(ProfileConstantProperty.PROFILE_SUBTYPE));

		// 遍历迭代器，获取根节点中的信息
		while (ProfileIt.hasNext()) {
			Element profile = (Element) ProfileIt.next();
			if (profile.getName().equals(ProfileConstantProperty.PROFILE_NAME)) {// 添加name属性
				this.profileName = profile.getStringValue();
			}
			if (profile.getName().equals(ProfileConstantProperty.PROFILE_SOURCE)) {// 添加source属性
				SourceDocument sourceDocument = new SourceDocument();
				sourceDocument.setSourceDocument(profile);
				this.sourceDocument = sourceDocument;
			}
			if(profile.getName().equals(ProfileConstantProperty.PROFILE_SOURCE_WORDSEGMENT)) {//添加wordSegment属性
				this.sourceDocumentWordSegment = profile.getStringValue();
			}
			if (profile.getName().equals(ProfileConstantProperty.PROFILE_MERGECOUNT)) {// 添加mergeCount属性
				this.mergeCount = Long.valueOf(profile.getStringValue());
			}
			if (profile.getName().equals(ProfileConstantProperty.PROFILE_MENTIONS)) {// 添加mentions属性
				this.mentions = Long.valueOf(profile.getStringValue());
			}
			if (profile.getName().equals(ProfileConstantProperty.PROFILE_VIP)) {// 添加vip属性
				this.profileVip = profile.getStringValue();
			}
			if (profile.getName().equals(ProfileConstantProperty.PROFILE_RELATION)) {// 添加relation属性
				ProfileRelation profileRelation = new ProfileRelation();
				profileRelation.setProfileRelation(profile);
				profileRelations.add(profileRelation);
			}
		}
	}
	
	/**
	 * 
	 * @param args
	 * @throws DocumentException 
	 */
	public static void main(String[] args){
		SAXReader saxReader = new SAXReader();
		//通过saxReader对象的
    	Document document = null;
		try {
			document = saxReader.read( new File( "data/wordSegment/LocationProfile/1449047717158781.xml" ) );
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//通过document对象获取根节点orgProfileStore
    	Element ProfileRoot = document.getRootElement();
    	
    	ProfileWithWordSegment Profile = new ProfileWithWordSegment();
    	
    	Profile.setProfile(ProfileRoot);
    	
    	Profile.printlnAll();
	}
}
