package com.xy.lr.java.nlp.profile.entity;

import com.xy.lr.java.nlp.constant.ProfileConstantProperty;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * ProfileRelation 解析如下的一个实例:
 * 
 * <relation type="CePerFAMILY">
 * 	<link>
 * 		<ne id="1449048706190806">孔令荣</ne>
 * 		<source doc="2014-11-01//武汉晚报/1414803989600.xml" id="39" pubtime="20151202173146" 
 * 			realtime="20040905000000"/>
 * 	</link>
 * </relation>
 * */
public class ProfileRelation {
	//关系的类型
	private String relationType;
	
	//关系的链接
	private ArrayList<ProfileRelationLink> profileRelationLinks;

	/**
	 * 构造函数，创建一个新的对象
	 * */
	public ProfileRelation () {
		this.relationType = new String();
		this.profileRelationLinks = new ArrayList<ProfileRelationLink>();
	}
	
	/**
	 * 输出所有的数据信息
	 * */
	public void printlnAll () {
		System.out.println( "\t" + "relationType : " + relationType );
		for (ProfileRelationLink profileRelationLink : profileRelationLinks ){
			profileRelationLink.printlnAll();
		}
	}
	
	/**
	 * 通过一个Element 对象设置ProfileRelation数据
	 * */
	public void setProfileRelation ( Element profileRelation ) {
		//获取relation中的type属性值
		String profileRelationType = profileRelation.attributeValue( ProfileConstantProperty.PROFILE_RELATION_TYPE );
		this.relationType = profileRelationType;
		
		//遍历每一个link
		Iterator profileRelationLinkIt = profileRelation.elementIterator();
		while(profileRelationLinkIt.hasNext()) {
			Element profileRelationLink = ( Element ) profileRelationLinkIt.next();
			
			//发现一个新的profileRelationLink
			ProfileRelationLink relationLink = new ProfileRelationLink();
			relationLink.setProfileRelationLink(profileRelationLink);
			
			this.profileRelationLinks.add(relationLink);
		}
	}
	
	/**
	 * 
	 * @param profileRelation
	 */
	public void addProfileRelation( Element profileRelation ) {
		//设置relation的type
		profileRelation.addAttribute( ProfileConstantProperty.PROFILE_RELATION_TYPE , this.relationType);
		if( this.profileRelationLinks.size() != 0 ) {
			for ( ProfileRelationLink profileRelationLink : this.profileRelationLinks ) {
				Element profileLink = profileRelation.addElement( ProfileConstantProperty.PROFILE_LINK );
				profileRelationLink.addProfileRelationLink(profileLink);
			}
		}
	}
	
	/**
	 * get方法
	 * */
	public String getRelationType() {
		return relationType;
	}

	/**
	 * set方法
	 * */
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	/**
	 * get方法
	 * */
	public ArrayList<ProfileRelationLink> getProfileRelationLinks() {
		return profileRelationLinks;
	}

	/**
	 * set方法
	 * */
	public void setProfileRelationLinks(ArrayList<ProfileRelationLink> profileRelationLinks) {
		this.profileRelationLinks = profileRelationLinks;
	}
}
