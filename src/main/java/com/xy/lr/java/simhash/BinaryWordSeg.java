/**
 * 
 */
package com.xy.lr.java.simhash;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 简单中文分词
 * @author xylr
 *
 */
public class BinaryWordSeg implements IWordSeg {

	/**
	 * 简单顺序分词
	 * @param doc 文章
	 * @return 顺序两个字组成一个分词
     */
	public List<String> tokens(String doc) {
		List<String> binaryWords = new LinkedList<String>();
		for(int i = 0; i < doc.length() - 1; i += 1) {
			StringBuilder bui = new StringBuilder();
			//顺序两个字组成一个单词
			bui.append(doc.charAt(i)).append(doc.charAt(i + 1));
			binaryWords.add(bui.toString());
		}
		return binaryWords;
	}

	/**
	 * 去除停用词的分词
	 * @param doc 文章
	 * @param stopWords 停用词表表
     * @return
     */
	public List<String> tokens(String doc, Set<String> stopWords) {
		return null;
	}

	/**
	 * 测试程序
	 * @param args
     */
	public static void main(String[] args) {
		BinaryWordSeg b = new BinaryWordSeg();
		List<String> list = b.tokens("我是中国人");
		for( String str : list ) {
			System.out.println(str);
		}
	}
}