package com.xy.lr.java.tfidf.main;

import com.xy.lr.java.nlp.stopwords.StopWords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xylr on 16-3-21.
 */
public class TFIDF_Document {
    //文章
    /*private String document;*/
    private String word_id;

    //去掉停用词的文章
    private String documentWithOutSW;

    //单词之间的分隔符
    private String sepilt;

    //文章词列表
    /*private List<String> words;*/

    //文章总词数
    private Double wordCount;

    //单词在文章中出现的词数
    private Map<String, Double> wordCountInDocument;

    /*//词频
    private Map<String, Double> tfs;

    //逆向词频
    private Map<String, Double> idfs;

    //词频 * 逆向词频
    private Map<String, Double> tfidfs;*/

    //停用词表
//    private static final StopWords stopWords = new StopWords();

    /**
     * 构造函数
     * @param document 文章
     */
    public TFIDF_Document(String word_id, String document, String sep) {
//        this.document = document;
        this.documentWithOutSW = document;
        this.sepilt = sep;
        this.word_id = word_id;
        /*this.words = new ArrayList<String>();*/

        /*this.tfs = new HashMap<String, Double>();
        this.idfs = new HashMap<String, Double>();
        this.tfidfs = new HashMap<String, Double>();*/
        this.wordCountInDocument = new HashMap<String, Double>();
    }

    /**
     *
     * @return
     */
    public Map<String, Double> getWordCountInDocument() {
        return this.wordCountInDocument;
    }

    /**
     * 初始化文章
     */
    public void deleteStopWordAndCount() {
//        this.documentWithOutSW = stopWords.removeStopWord(document, this.sepilt);
//        this.documentWithOutSW = this.document;
        this.wordCount = (double)this.documentWithOutSW.split(this.sepilt).length;

        setupWords(this.documentWithOutSW);
    }

    /**
     *
     * @return
     */
    public double getWordCount() {
        return this.wordCount;
    }

    /**
     * 获取文章词的个数
     * @param doc 文章
     */
    private void setupWords(String doc) {
        if(doc.split(this.sepilt).length == 0) {
            System.out.println("文章有问题 -->  " + doc);
        }
        else {
            double count = 0;
            for(String word : doc.split(this.sepilt)) {
                /*if(!this.words.contains(word)){
                    this.words.add(word);
                }*/
                if(this.wordCountInDocument.get(word) == null) {
                    this.wordCountInDocument.put(word, 1.0 / this.wordCount);
                }else {
                    this.wordCountInDocument.put(word, this.wordCountInDocument.get(word) + 1.0 / this.wordCount);
                }
            }
        }
    }


    public String getWord_id() {
        return word_id;
    }

    public void setWord_id(String word_id) {
        this.word_id = word_id;
    }

    /**
     * 去除停用词
     * @return 文章
     */
    public String getDocumentWithOutSW() {
        return this.documentWithOutSW;
    }

    /**
     *
     * @param tfs
     */
    /*public void setTfs(Map<String, Double> tfs) {
        this.tfs = tfs;
    }

    *//**
     *
     * @return
     *//*
    public Map<String, Double> getTfs() {
        return this.tfs;
    }

    *//**
     *
     * @return
     *//*
    public Map<String, Double> getIdfs() {
        return this.idfs;
    }

    *//**
     *
     * @param idfs
     *//*
    public void setIdfs(Map<String, Double> idfs) {
        this.idfs = idfs;
    }

    *//**
     *
     * @param tfidfs
     *//*
    public void setTfidfs(Map<String, Double> tfidfs) {
        this.tfidfs = tfidfs;
    }

    *//**
     *
     * @return
     *//*
    public Map<String, Double> getTfidfs() {
        return this.tfidfs;
    }*/

    /**
     *
     * @return
     */
    /*public List<String> getWords() {
        return this.words;
    }*/
}
