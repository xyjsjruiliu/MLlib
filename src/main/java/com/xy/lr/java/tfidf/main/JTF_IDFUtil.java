package com.xy.lr.java.tfidf.main;

import com.xy.lr.java.math.JMath;
import com.xy.lr.java.tools.file.JFile;

import java.io.File;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by xylr on 16-3-23.
 */
public class JTF_IDFUtil {

    public static void makeVectors(String vector, String words) {
        ArrayList<String> vectors = JFile.getAllLines(new File(vector), "utf-8");//输入文件
        ArrayList<String> wordList = JFile.getAllLines(new File(words), "utf-8");//词列表

        Map<String, Double> map = new HashMap<String, Double>();
//        Map<String, Double> tempMap = new HashMap<String, Double>();

        for(String str : wordList) {
            map.put(str, 0.0);
//            tempMap.put(str, 0.0);
        }
        wordList.clear();


        for(String vect : vectors) {
            StringBuilder fileName = new StringBuilder(vect.split("\t")[0]);//文件名
            StringBuilder w = new StringBuilder(vect.split("\t")[1]);//

            for (String s : w.toString().split(";")) {
                map.put(s.split(",")[0], Double.valueOf(s.split(",")[1]));
            }

            StringBuilder stringBuilder = new StringBuilder();
            for (Map.Entry<String, Double> entry : map.entrySet()) {
                stringBuilder.append(entry.getValue() + ",");

                entry.setValue(0.0);
            }

            JFile.appendFile("data/TFIDF/vec.txt", stringBuilder.toString());
//            System.out.println(stringBuilder);
        }
    }

    /**
     *
     * @param inputFile
     */
    public static void getWords(String inputFile) {
        ArrayList<String> lines = JFile.getAllLines(new File(inputFile), "utf-8");//输入文件
        List<String> wordList = new ArrayList<String>();

        for (String line : lines) {
            StringBuilder fileName = new StringBuilder(line.split("\t")[0]);//文件名
            StringBuilder words = new StringBuilder(line.split("\t")[1]);//

            for (String word : words.toString().split(";")) {
                String str = word.split(",")[0];
                if(!wordList.contains(str)) {
                    wordList.add(str);
                }
            }
        }

        JFile.appendFile("data/TFIDF/words.txt", wordList);
        System.out.println(wordList.size());
    }

    /**
     * 利用生成的tfidf文件，生成固定维数的向量
     * @param tfidf 输入文件路径
     * @param outputFile 输出路径
     * @param vectorCount 生成向量的维数
     */
    public static void makeVector(String tfidf, String outputFile, int vectorCount) {
        ArrayList<String> lines = JFile.getAllLines(new File(tfidf), "utf-8");

        for (String line : lines) {
//            System.out.println(line);

            StringBuilder fileName = new StringBuilder(line.split("\t")[0]);//文件名
            StringBuilder words = new StringBuilder(line.split("\t")[1]);//

            StringBuilder stringBuilder = new StringBuilder();

            if (words.toString().split(";").length < vectorCount){
                System.out.println(line);
            }
            else {
                Map<Double, String> map = new TreeMap<Double, String>(new Comparator<Double>() {
                    public int compare(Double o1, Double o2) {
                        if(o1 > o2)
                            return -1;
                        else if(o1 < o2)
                            return 1;
                        return 0;
                    }
                });
                for (String word : words.toString().split(";")) {
                    map.put(Double.valueOf(word.split(",")[1]), word.split(",")[0]);
                }

                int i = 0;
                for(Map.Entry<Double, String> entry : map.entrySet()) {
                    if(i >= vectorCount)
                        break;
                    else{
                        stringBuilder.append(entry.getValue() + "," + entry.getKey() + ";");
                    }
                    i++;
                }
                JFile.appendFile(outputFile, fileName + "\t" + stringBuilder.substring(0, stringBuilder.length() - 1));
            }
        }
    }

    /**
     * 计算tf
     * @param file
     * @param output
     */
    public void cal_tf(String file, String output) {
        ArrayList<String> lines = JFile.getAllLines(new File(file), "utf-8");

        for(String line : lines) {
            String filename = line.split("\t")[0];
            Double count = Double.valueOf(line.split("\t")[1]);

            String l = line.split("\t")[2];
            String ll = l.substring(0, l.length() - 1);

            StringBuilder builder = new StringBuilder();

            for(String str : ll.split(";")) {
                if(str.split(",").length != 2){
                }else {
                    String word = str.split(",")[0];
                    Double num = Double.valueOf(str.split(",")[1]);

                    double a = num / count;

                    builder.append(word + "," + a + ";");
                }
            }

            JFile.appendFile(output, filename + "\t" + builder.substring(0, builder.length() - 1));
        }
        lines.clear();
    }

    /**
     * 计算tf-idf
     * @param fileNametfs
     * @param fileNameidf
     * @param output
     */
    public void cal_tfidfs(String fileNametfs, String fileNameidf, String output) {
        ArrayList<String> idfs = JFile.getAllLines(new File(fileNameidf), "utf-8");//idf
        ArrayList<String> tfs = JFile.getAllLines(new File(fileNametfs), "utf-8");//tfs

        Map<String, Double> map = new HashMap<String, Double>();

        for(String str : idfs) {
            map.put(str.split("\t")[0], Double.valueOf(str.split("\t")[1]));
        }

        for(String line : tfs) {
            String filename = line.split("\t")[0];
            String words = line.split("\t")[1];

            StringBuilder builder = new StringBuilder();
            for(String word : words.split(";")) {
                String w = word.split(",")[0];

                Double dou = map.get(w);

                double a;
                if(dou == null){
                    System.out.println(filename + "\t" + w);
                    a = 0;
                }else{
                    a = JMath.log(tfs.size() / dou + 1) * Double.valueOf(word.split(",")[1]);
                }
                if(a == 0){
                }else {
                    builder.append(w + "," + a + ";");
                }
            }

            JFile.appendFile(output, filename + "\t" + builder.substring(0, builder.length() - 1));
        }
        tfs.clear();
        idfs.clear();
        map.clear();
    }

    /**
     * 计算总体tf-idf数据
     * @param inputFile 输入路径
     * @param outputFile 输出路径
     * @param tempPath 中间临时路径
     */
    public static void cal_TFIDF(String inputFile, String outputFile, String tempPath) {
        JTF_IDF jtf_idf1 = new JTF_IDF(inputFile);

        //保存中间数据
        jtf_idf1.saveInfo(tempPath + "tf.txt", tempPath + "idf.txt");

        new JTF_IDFUtil().cal_tfidfs(tempPath + "tf.txt",tempPath + "idf.txt", outputFile);
    }

    public static void main(String[] args) {
//        new JTF_IDFUtil().cal_tf("data/TFIDF/tf.txt");

//        new JTF_IDFUtil().cal_idf("data/TFIDF/tfs.txt", "data/TFIDF/idf.txt");

//        new JTF_IDFUtil().cal_tfidfs("data/TFIDF/tfs.txt","data/TFIDF/idf.txt", "data/TFIDF/tfidfs.txt");
    }

//    public void cal_idf(String fileNametfs, String fileNameidf, String output) {
//        ArrayList<String> idfs = JFile.getAllLines(new File(fileNameidf), "utf-8");//idf
//        ArrayList<String> tfs = JFile.getAllLines(new File(fileNametfs), "utf-8");//tfs
//
//        Map<String, Double> map = new HashMap<String, Double>();
//
//        for(String str : idfs) {
//            map.put(str.split("\t")[0], Double.valueOf(str.split("\t")[1]));
//        }
//
//        for(String line : tfs) {
//            String filename = line.split("\t")[0];
//            String words = line.split("\t")[1];
//
//            StringBuilder builder = new StringBuilder();
//            for(String word : words.split(";")) {
//                String w = word.split(",")[0];
//
//                Double dou = map.get(w);
//                if(dou == null){
//                    System.out.println(filename + "\t" + w);
//                }else{
//                    double a = JMath.log(tfs.size() / dou + 1);
//                    builder.append(w + "," + a + ";");
//                }
//            }
//
//            JFile.appendFile("data/TFIDF/idfs.txt", filename + "\t" + builder.substring(0, builder.length() - 1));
//        }
//    }
}
