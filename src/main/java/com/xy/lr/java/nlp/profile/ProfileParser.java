package com.xy.lr.java.nlp.profile;


import com.xy.lr.java.nlp.profile.entity.ProfileExample;
import com.xy.lr.java.nlp.profile.entity.ProfileWithWordSegment;
import com.xy.lr.java.tools.file.FindAllFileOnCatalogue;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Profile 文件处理类，提供多种处理形式
 * */
public class ProfileParser {
	//创建SAXReader的对象saxReader
	private SAXReader saxReader = new SAXReader();
	
	/**
	 * 处理一个 profile 文件
	 * */
	public ProfileExample parseProfile (String fileName ) throws DocumentException {
    	//通过saxReader对象的
    	Document document = saxReader.read( new File( fileName ) );
    	//通过document对象获取根节点orgProfileStore
    	Element ProfileRoot = document.getRootElement();
    	
    	ProfileExample Profile = new ProfileExample();
    	
    	Profile.setProfile(ProfileRoot);
    	
    	return Profile;
	}
	
	/**
	 * 处理一个文件路径下的所有文件
	 * */
	public ArrayList<ProfileExample> parseProfile ( File filePaths ) throws DocumentException {
		ArrayList<ProfileExample> profileExamples = new ArrayList<ProfileExample>();
		
		FindAllFileOnCatalogue fileAFOC = new FindAllFileOnCatalogue();
		//文件路径下的所有文件
		List<File> fileList = fileAFOC.getCatalogueList(filePaths);
		
		profileExamples = parseProfile(fileList);
		
		return profileExamples;
	}
	
	public ArrayList<ProfileWithWordSegment> parserProfileWithWord (File filePaths ) throws DocumentException {
		ArrayList<ProfileWithWordSegment> profile = new ArrayList<ProfileWithWordSegment>();
		
		FindAllFileOnCatalogue fileAFOC = new FindAllFileOnCatalogue();
		//文件路径下的所有文件
		List<File> fileList = fileAFOC.getCatalogueList(filePaths);
		
		for( File file : fileList) {
			profile.add(parserProfileWithWord(file.getAbsolutePath()));
		}
		
		return profile;
	}
	
	public ProfileWithWordSegment parserProfileWithWord ( String fileName ) throws DocumentException {
    	//通过saxReader对象的
    	Document document = saxReader.read( new File( fileName ) );
    	//通过document对象获取根节点orgProfileStore
    	Element ProfileRoot = document.getRootElement();
    	
    	ProfileWithWordSegment Profile = new ProfileWithWordSegment();
    	
    	Profile.setProfile(ProfileRoot);
    	
    	return Profile;
	}
	
	/**
	 * 处理一个list，里面有所有的文件路径
	 * */
	public ArrayList<ProfileExample> parseProfile ( List<File> paths ) throws DocumentException {
		ArrayList<ProfileExample> profileExamples = new ArrayList<ProfileExample>();
		for ( File path : paths ) {
			profileExamples.add( parseProfile( path.getAbsolutePath() ) );
		}
		return profileExamples;
	}
	
	/**
	 * 生成一个新的Profile文件
	 */
	public ProfileExample createNewProfileByLable( String newLable, String data ) {
		return new ProfileExample();
	}
	
	/**
	 * 
	 * @param profile
	 * @param filePath
	 * @param wordSegment
	 */
	public void saveProfileAsFile( ProfileExample profile, String filePath, String wordSegment ) {
		//1.创建document对象，代表整个xml文档
		Document document = DocumentHelper.createDocument();
		//设置生成xml的格式
		OutputFormat format = OutputFormat.createPrettyPrint();
//		format.setEncoding("gbk");
		//保存文件
		profile.saveProfileAsFile(document, format, filePath, wordSegment);	
	}
}
