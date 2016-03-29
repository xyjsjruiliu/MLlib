/**
 * 
 */
package com.xy.lr.java.simhash;

import java.util.List;
import java.util.Set;

/**
 * 文章分词接口
 * @author xylr 2016-01-05
 *
 */
public interface IWordSeg {

	/**
	 * 输入文章进行分词
	 * @param doc 文章
	 * @return 分词结果
     */
	public List<String> tokens(String doc);

	/**
	 * 输入文章进行分词
	 * @param doc 文章
	 * @param stopWords 停用词表
     * @return 去除停用词的分词结果
     */
	public List<String> tokens(String doc, Set<String> stopWords);
}
