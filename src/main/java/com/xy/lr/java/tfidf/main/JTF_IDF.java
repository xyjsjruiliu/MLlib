package com.xy.lr.java.tfidf.main;

import com.xy.lr.java.math.JMath;
import com.xy.lr.java.tools.file.FindAllFileOnCatalogue;
import com.xy.lr.java.tools.file.JFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xylr on 16-3-21.
 * 计算 tf-idf
 */
public class JTF_IDF {
    //文章列表
    private List<TFIDF_Document> tfidf_documents;

    //文章个数, 文章总数
    private int documentCount;

    //文件路径
//    private String filePath;

    //文档集合中每个词在多少篇 文章中出现过
    private Map<String, Double> wordToDocument;

    /**
     * 计算tf-idf
     */
    /*public void run() {
        initial_TFIDF();
        System.out.println("开始计算 TF");
        cal_tfs();
        System.out.println("结束计算 TF");
        System.out.println("开始计算 IDF");
        cal_idfs();
        System.out.println("结束计算 IDF");
        System.out.println("开始计算 TFIDF");
        cal_tfidfs();
        System.out.println("结束计算 TFIDF");
    }*/

    /**
     * 保存中间数据
     * @param tf tf中间数据
     * @param idf idf中间数据
     */
    public void saveInfo(String tf, String idf) {
        initial_TFIDF();
        System.out.println(this.wordToDocument.size());

        for(Map.Entry<String, Double> entry : this.wordToDocument.entrySet()) {
            JFile.appendFile(idf, entry.getKey() + "\t" + entry.getValue());
        }
        this.wordToDocument.clear();


        for(TFIDF_Document tfidf_document : this.tfidf_documents) {
            StringBuilder str = new StringBuilder();//
            Double count = tfidf_document.getWordCount();//文章总词数

            str.append(tfidf_document.getWord_id() + "\t");

            for(Map.Entry<String, Double> entry : tfidf_document.getWordCountInDocument().entrySet()) {
                str.append(entry.getKey() + "," + entry.getValue() + ";");
            }

            JFile.appendFile(tf, str.toString().substring(0, str.length() - 1));
        }
        this.tfidf_documents.clear();
    }

    /**
     * 计算tf值
     */
    /*private void cal_tfs() {
        for(TFIDF_Document tfidf_document : this.tfidf_documents) {
            Map<String, Double> map = tfidf_document.getTfs();
            //文章总词数
            Double wordCount = tfidf_document.getWordCount();
            ///
            for(Map.Entry<String, Double> entry : tfidf_document.getWordCountInDocument().entrySet()) {
//                System.out.println(entry.getKey() + "\t" + entry.getValue());
                map.put(entry.getKey(), entry.getValue() / wordCount);
            }
//            System.out.println();
//            tfidf_document.setTfs(map);
        }
    }*/

    /**
     * 计算idf值
     */
    /*private void cal_idfs() {
//        int count = 1;
        for(TFIDF_Document tfidf_document : this.tfidf_documents) {
            Map<String, Double> map = tfidf_document.getIdfs();

//            System.out.println(count);

            for(String word : tfidf_document.getWords()) {
                double x = this.wordToDocument.get(word);//包含该词的文档数
                double result = JMath.log(this.documentCount / x + 1);//计算idf

                map.put(word, result);
            }

//            tfidf_document.setIdfs(map);
//            count++;
        }
    }*/

    /**
     * 计算tf-idf值
     */
//    private void cal_tfidfs() {
//        for (TFIDF_Document tfidf_document : this.tfidf_documents) {
//            Map<String, Double> map = tfidf_document.getTfidfs();
//
//            Map<String, Double> tfs = tfidf_document.getTfs();
//            Map<String, Double> idfs = tfidf_document.getIdfs();
//
//            for (Map.Entry<String, Double> entry : tfs.entrySet()) {
//                double tf = entry.getValue();
//                double idf = idfs.get(entry.getKey());
//
//                double tfidf = tf * idf;
////                System.out.println(tfidf);
//
//                map.put(entry.getKey(), tfidf);
//            }
//
////            tfidf_document.setTfidfs(map);
//        }
//    }

    /**
     * 初始化程序
     * 计算idf中间数据
     */
    private void initial_TFIDF() {
        for(TFIDF_Document tfidf_document : this.tfidf_documents) {
            for(String word : tfidf_document.getWordCountInDocument().keySet()) {
                //如果存在
                if(this.wordToDocument.containsKey(word)) {
                    double count = this.wordToDocument.get(word);
                    this.wordToDocument.put(word, count + 1.0);
                }
                //如果不存在
                else {
                    this.wordToDocument.put(word, 1.0);
                }
            }
        }
    }

    /**
     * 构造函数
     * @param filePath 文件路径
     */
    public JTF_IDF(String filePath) {
        System.out.println("构造函数开始！");
        this.wordToDocument = new HashMap<String, Double>();
        //new 对象
        this.tfidf_documents = new ArrayList<TFIDF_Document>();
//        this.filePath = filePath;

        FindAllFileOnCatalogue findAllFileOnCatalogue = new FindAllFileOnCatalogue();
        //获取目录下的所有文件
        List<File> listFiles = findAllFileOnCatalogue.getCatalogueList(new File(filePath));

        int count = 1;
        //遍历文件目录
        for(File file : listFiles) {
            String line = JFile.getAllLines(file).replace("\n", " ");

            TFIDF_Document tfidf_document = new TFIDF_Document(file.getName(), line, " ");
            tfidf_document.deleteStopWordAndCount();

//            System.out.println(count);
            this.tfidf_documents.add(tfidf_document);
            count++;
        }

        this.documentCount = this.tfidf_documents.size();
        System.out.println("构造函数结束！");
    }

    /**
     * 构造函数
     * @param filePath 文件路径
     */
//    public JTF_IDF(File filePath) {
//        this.wordToDocument = new HashMap<String, Double>();
//        //new 对象
//        this.tfidf_documents = new ArrayList<TFIDF_Document>();
////        this.filePath = filePath.getAbsolutePath();
//
//        List<String> lines = JFile.getAllLines(filePath, "utf-8");
//
//        //一行是一个文件
//        for(String line : lines) {
//            TFIDF_Document tfidf_document = new TFIDF_Document(line, " ");
//            tfidf_document.deleteStopWordAndCount();
//
//            this.tfidf_documents.add(tfidf_document);
//        }
//
//        this.documentCount = this.tfidf_documents.size();
//
//        System.out.println("构造函数jies");
//    }

    /**
     *
     * @return
     */
    public List<TFIDF_Document> getTfidf_documents() {
        return tfidf_documents;
    }

    /**
     *
     * @param tfidf_documents
     */
    public void setTfidf_documents(List<TFIDF_Document> tfidf_documents) {
        this.tfidf_documents = tfidf_documents;
    }

    /**
     *
     * @return
     */
    public int getDocumentCount() {
        return documentCount;
    }

    /**
     *
     * @param documentCount
     */
    public void setDocumentCount(int documentCount) {
        this.documentCount = documentCount;
    }

    /**
     *
     * @return
     */
//    public String getFilePath() {
//        return filePath;
//    }

    /**
     *
     * @param filePath
     */
//    public void setFilePath(String filePath) {
//        this.filePath = filePath;
//    }

    /**
     *
     * @return
     */
    public Map<String, Double> getWordToDocument() {
        return this.wordToDocument;
    }
}
