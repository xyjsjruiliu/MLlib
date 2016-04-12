package com.xy.lr.java.kmeans.data;

import com.xy.lr.java.tools.file.JFile;

import java.io.File;
import java.util.List;

/**
 * Created by xylr on 16-3-18.
 */
public class DataProcessor {
    public static void main(String[] args) {
        List<String> fileList = JFile.getAllLines(new File("/home/xylr/Working/" +
                "workspace/MLlib/data/K-means/output.csv"), "utf-8");

        for(String file : fileList) {
            int number = file.lastIndexOf(",");

            String clustring = file.substring(number + 1);

            String num = file.substring(0, file.indexOf(","));

//            System.out.println(clustring);

            JFile.appendFile("data/Clustering/Clustering_" + clustring + ".txt", num);
        }
//        new DataProcessor().f1();
    }

    public void f1() {
        List<String> fileList = JFile.getAllLines(new File("/home/xylr/Working/" +
                "workspace/MLlib/data/LdaResults/lda_120.theta"), "utf-8");

        System.out.println(fileList.size());

        for(String file : fileList) {
            String temp = file.replaceAll("\t", ",");

            temp = temp + "1";


            JFile.appendFile("data/clustering.txt", temp);
//            System.out.println(temp);
        }
    }
}
