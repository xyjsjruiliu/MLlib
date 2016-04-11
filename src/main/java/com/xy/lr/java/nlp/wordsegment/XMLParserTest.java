package com.xy.lr.java.nlp.wordsegment;

import com.xy.lr.java.nlp.wordsegment.entity.ChineseWordSegment;
import com.xy.lr.java.nlp.wordsegment.entity.XMLDocument;
import org.dom4j.DocumentException;

/**
 * @author xylr
 * 
 * XMLParserTest 测试类
 * */
public class XMLParserTest {
	public static void main(String[] args) throws DocumentException {
		/*SAXReader saxReader = new SAXReader();
		Document document = saxReader.read( new File( "token.xml" ) );

		Element xmlDocumentRoot = document.getRootElement();

		Element element = xmlDocumentRoot.element("fea");

		Iterator xmlDocumentIt = element.elementIterator();
		while(xmlDocumentIt.hasNext()) {
			Element xmlDocument = (Element) xmlDocumentIt.next();

			System.out.println(xmlDocument.getName());
		}*/

//		System.out.println(element.getName());

		/*if(args.length != 2){
			System.err.println("error!");
		}*/

		XMLParser xmlParser = new XMLParser();
		XMLDocument xmlDocument = null;
		try {
			xmlDocument = xmlParser.xmlParser("document.xml");
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		ChineseWordSegment chineseWordSegment = xmlParser.wordSegment(xmlDocument);
		String result = chineseWordSegment.getWordList();

		System.out.println(result);

		/*JFile.appendFile(args[1], result);

		for(int i = 0; i < 2 ; i++) {
			XMLParser xmlParser = new XMLParser();
			XMLDocument xmlDocument = xmlParser.xmlParser("document.xml");

			System.out.println("----------------------------分割线----------------------------");
//		xmlParser.printAll(xmlDocument);

			ChineseWordSegment chineseWordSegment = xmlParser.wordSegment(xmlDocument);
//		chineseWordSegment.printAll();
//			chineseWordSegment.appendChineseWordSegmentToFile("output/wordSegment.txt");
		String result = chineseWordSegment.getWordList();
		System.out.println(result);
		}*/

	}
}
