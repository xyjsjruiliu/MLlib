package com.xy.lr.java.simhash;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by xylr on 16-3-12.
 */
public class ChineseInfoWordSeg implements IWordSeg {

    /**
     * 中文分词
     * @param doc 文章
     * @return 分词列表
     */
    public List<String> tokens(String doc) {
        List<String> binaryWords = new LinkedList<String>();

        //每个词用空格隔开
        for(String word : doc.split(" ")) {
            StringBuffer bui = new StringBuffer();
            bui.append(word);
            binaryWords.add(bui.toString());
        }
        return binaryWords;
    }

    public List<String> tokens(String doc, Set<String> stopWords) {
        return null;
    }

    public static void main(String[] args) {
        List<String> list = new ChineseInfoWordSeg().tokens("ow sa as fs");

        for(String l : list) {
            System.out.println(l);
        }
    }
}
