package com.xy.lr.java.nlp.profile.entity;


import com.xy.lr.java.nlp.constant.ProfileConstantProperty;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * ProfileExample 解析一个Profile文件
 */
public class ProfileExample {
	// Profile 的 ID
	protected String profileId;

	// Profile 的 类型
	protected String profileType;

	// Profile 的 子类型
	protected String profileSubType;

	// Profile 的 名字
	protected String profileName;

	// sourceDocument
	protected SourceDocument sourceDocument;

	// mergeCount
	protected long mergeCount;

	// mentions
	protected long mentions;

	// Vip
	protected String profileVip;

	// Relations
	protected ArrayList<ProfileRelation> profileRelations;

	/**
	 * 构造函数
	 */
	public ProfileExample() {
		this.profileId = new String();
		this.profileType = new String();
		this.profileSubType = new String();
		this.profileName = new String();
		this.sourceDocument = new SourceDocument();
		this.mergeCount = 0;
		this.mentions = 0;
		this.profileVip = new String();
		this.profileRelations = new ArrayList<ProfileRelation>();
	}

	/**
	 * 设置ID，Type，SubType
	 */
	protected void setProfileIDAndTypeAndSubType(String profileId, String profileType, String profileSubType) {
		this.profileId = profileId;
		this.profileType = profileType;
		this.profileSubType = profileSubType;
	}

	/**
	 * 通过Element 对象设置 Profile中数据
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
	 * 保存 ProfileExample 文件
	 */
	public void saveProfileAsFile(Document document, OutputFormat format, String filePath, String wordSegment) {
		// 2.创建根节点profile
		Element profileRoot = document.addElement(ProfileConstantProperty.PROFILE);
		// 3.向profile节点中添加id, type, subtype属性
		profileRoot.addAttribute(ProfileConstantProperty.PROFILE_ID, this.getProfileId());
		profileRoot.addAttribute(ProfileConstantProperty.PROFILE_TYPE, this.getProfileType());
		profileRoot.addAttribute(ProfileConstantProperty.PROFILE_SUBTYPE, this.getProfileSubType());

		//添加子节点
		addChildElement(profileRoot, wordSegment);
		
		// 保存
		saveProfileDocument(document, format, filePath);
	}

	/**
	 * 
	 * @param profileRoot
	 * @param wordSegment
	 */
	private void addChildElement(Element profileRoot, String wordSegment) {
		// 4.生成子节点及节点内容
		Element profileName = profileRoot.addElement(ProfileConstantProperty.PROFILE_NAME);
		profileName.setText(this.getProfileName());

		Element profileSourceDocument = profileRoot.addElement(ProfileConstantProperty.PROFILE_SOURCE);
		this.sourceDocument.addSourceDocument(profileSourceDocument);

		Element profileSourceWordSegment = profileRoot.addElement(ProfileConstantProperty.PROFILE_SOURCE_WORDSEGMENT);
		profileSourceWordSegment.setText(wordSegment);

		Element profileMergeCount = profileRoot.addElement(ProfileConstantProperty.PROFILE_MERGECOUNT);
		profileMergeCount.setText(String.valueOf(this.mergeCount));

		Element profileMentions = profileRoot.addElement(ProfileConstantProperty.PROFILE_MENTIONS);
		profileMentions.setText(String.valueOf(this.mentions));

		Element profileVip = profileRoot.addElement(ProfileConstantProperty.PROFILE_VIP);
		profileVip.setText(String.valueOf(this.profileVip));

		if (this.profileRelations.size() != 0) {
			for (ProfileRelation profileRelation : this.profileRelations) {
				Element relation = profileRoot.addElement(ProfileConstantProperty.PROFILE_RELATION);
				profileRelation.addProfileRelation(relation);
			}
		}
	}

	/**
	 * 保存定义好的Document
	 * 
	 * @param document
	 * @param format
	 * @param filePath
	 */
	private void saveProfileDocument(Document document, OutputFormat format, String filePath) {
		// 5.生成xml文件
		File file = new File(filePath + ".xml");
		XMLWriter writer;
		try {
			writer = new XMLWriter(new FileOutputStream(file), format);
			// 设置是否转义，默认值是true，代表转义
			writer.setEscapeText(false);
			writer.write(document);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 输出所有的数据
	 */
	public void printlnAll() {
		System.out.println("profileId : " + profileId + " , profileType : " + profileType + " , profileSubType" + " : "
				+ profileSubType);
		System.out.println("\t" + "profileName : " + profileName);
		sourceDocument.printlnAll();
		System.out.println("\t" + "mergeCount : " + mergeCount);
		System.out.println("\t" + "mentions : " + mentions);
		System.out.println("\t" + "profileVip : " + profileVip);
		for (ProfileRelation profileRelation : profileRelations) {
			profileRelation.printlnAll();
		}
	}

	/**
	 * 
	 * @return profileId
	 */
	public String getProfileId() {
		return profileId;
	}

	/**
	 * 
	 * @param profileId
	 */
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	/**
	 * get方法
	 */
	public String getProfileType() {
		return profileType;
	}

	/**
	 * set方法
	 */
	public void setProfileType(String profileType) {
		this.profileType = profileType;
	}

	/**
	 * get方法
	 */
	public String getProfileSubType() {
		return profileSubType;
	}

	/**
	 * set方法
	 */
	public void setProfileSubType(String profileSubType) {
		this.profileSubType = profileSubType;
	}

	/**
	 * get方法
	 */
	public String getProfileName() {
		return profileName;
	}

	/**
	 * set方法
	 */
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	/**
	 * get方法
	 */
	public long getMergeCount() {
		return mergeCount;
	}

	/**
	 * set方法
	 */
	public void setMergeCount(long mergeCount) {
		this.mergeCount = mergeCount;
	}

	/**
	 * get方法
	 */
	public long getMentions() {
		return mentions;
	}

	/**
	 * set方法
	 */
	public void setMentions(long mentions) {
		this.mentions = mentions;
	}

	/**
	 * get方法
	 */
	public String getProfileVip() {
		return profileVip;
	}

	/**
	 * set方法
	 */
	public void setProfileVip(String profileVip) {
		this.profileVip = profileVip;
	}

	/**
	 * get方法
	 */
	public ArrayList<ProfileRelation> getProfileRelations() {
		return profileRelations;
	}

	/**
	 * set方法
	 */
	public void setProfileRelations(ArrayList<ProfileRelation> profileRelations) {
		this.profileRelations = profileRelations;
	}

	/**
	 * get方法
	 */
	public SourceDocument getSourceDocument() {
		return sourceDocument;
	}

	/**
	 * set方法
	 */
	public void setSourceDocument(SourceDocument sourceDocument) {
		this.sourceDocument = sourceDocument;
	}

	/**
	 * main 方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// new ProfileExample().saveProfileAsFile();
	}
}
