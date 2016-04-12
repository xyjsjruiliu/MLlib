package com.xy.lr.java.tfidf;

import com.xy.lr.java.tfidf.main.JTF_IDFUtil;
import com.xy.lr.java.tools.time.DateTime;

/**
 * Created by xylr on 16-3-21.
 */
public class App {
    public static void main(String[] args) {
        System.out.println("程序开始：");
        long a = DateTime.CurrentTime();

        /*JTF_IDFUtil.cal_TFIDF("/home/xylr/Working/IdeaProjects/KnowLedgeBase/chineseword/data/",
                "data/TFIDF/tfidfs.txt",
                "data/TFIDF/");*/

//        JTF_IDFUtil.makeVector("data/TFIDF/tfidfs.txt", "data/TFIDF/vector.txt", 20);

//        JTF_IDFUtil.getWords("data/TFIDF/vector.txt");

        JTF_IDFUtil.makeVectors("data/TFIDF/vector.txt","data/TFIDF/words.txt");

        long b = DateTime.CurrentTime();

        System.out.println("程序总共花费时间：" + DateTime.formatTime(b - a));

//        System.out.println(jtf_idf1.getDocumentCount());

//        Map<String, Double> map = jtf_idf1.getWordToDocument();
//
//        for(Map.Entry entry : map.entrySet()) {
//            System.out.println(entry.getKey() + "\t" + entry.getValue());
//        }

//        System.out.println(jtf_idf.getFilePath());
//        System.out.println(jtf_idf.getDocumentCount());

//        for(TFIDF_Document tfidf_document1 : jtf_idf1.getTfidf_documents()) {
//            System.out.println(tfidf_document1.getDocumentWithOutSW() + "document");
//            System.out.println(tfidf_document1.getWordCount());

//            Map<String, Double> map = tfidf_document1.getTfs();
////            System.out.println(map.size());
//
//            System.out.println("TF值-------------");
//            for(Map.Entry<String, Double> entry : map.entrySet()) {
//                System.out.println(entry.getKey() + "\t" + entry.getValue());
//            }
//
//            System.out.println("IDF值-------------");
//
//            Map<String, Double> map1 = tfidf_document1.getIdfs();
//            for (Map.Entry<String, Double> entry : map1.entrySet()) {
//                System.out.println(entry.getKey() + "\t" + entry.getValue());
//            }

//            System.out.println("TFIDF值--------------");
//            Map<String, Double> map2 = tfidf_document1.getTfidfs();
//            for (Map.Entry<String, Double> entry : map2.entrySet()) {
//                System.out.println(entry.getKey() + "\t" + entry.getValue());
//            }

//            for(String str : tfidf_document1.getWords()) {
//                System.out.print(str + ",");
//            }
//            System.out.println();
//        }
//        JTF_IDF jtf_idf = new JTF_IDF();
    }
}
