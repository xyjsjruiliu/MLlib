package com.xy.lr.java.kmeans.file;

import com.xy.lr.java.tools.file.JFile;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by xylr on 16-3-30.
 * com.xy.lr.java.kmeans.file
 */
public class LdaToKmeansData {
    public static void main(String[] args) {
        /*new LdaToKmeansData().ldaToKmeans("data/LdaResults/lda_120.theta",
                "data/K-means/input.txt");*/
        new LdaToKmeansData().SplitKmeans("data/K-means/output.csv", "data/K-means/Clustering/");
    }

    /**
     *
     * @param output 聚类结果
     * @param ClusteringPath 输出路径
     */
    private void SplitKmeans(String output, String ClusteringPath) {
        ArrayList<String> lines = JFile.getAllLines(new File(output), "utf-8");

        for (String line : lines) {
            /*System.out.println(line);*/
            int last = line.lastIndexOf(",");
            String id = line.substring(last + 1);

            String data = line.substring(0, line.indexOf(","));

            JFile.appendFile(ClusteringPath + id, data);
//            System.out.println(id + "\t" +data);
        }
    }

    /**
     *
     * @param input lda跑出的结果
     * @param output 聚类结果
     */
    private void ldaToKmeans(String input, String output) {
        ArrayList<String> lines = JFile.getAllLines(new File(input), "utf-8");

        for (String line : lines) {
            String name = line.split(":")[0];
            name = name.substring(name.indexOf("WordSeg_") + 8, name.length() - 4);

            String para = line.split(":")[1];
            para = para.replaceAll("\t", ",");

            JFile.appendFile(output, name + "," + para + 1);

//            System.out.println(name + "," + para + 1);
        }

        System.out.println(lines.size());
    }
}
