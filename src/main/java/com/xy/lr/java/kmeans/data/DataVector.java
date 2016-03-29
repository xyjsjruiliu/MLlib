package com.xy.lr.java.kmeans.data;

import com.xy.lr.java.simhash.ChineseInfoWordSeg;
import com.xy.lr.java.simhash.Simhash;

/**
 * Created by xylr on 16-3-16.
 */
public class DataVector {
    //节点编号
    public int id;
    //节点属性
    public String values;
    //节点标签
    public int label;

    /**
     *
     * @param id
     * @param values
     * @param label
     */
    public DataVector(int id, String values, int label) {
        this.id = id;
        this.values = values;
        this.label = label;
    }

    /**
     * 计算节点个聚类中心之间的距离
     * @param dataPoint 节点
     * @param centroid 聚类中心
     * @return 距离
     */
    public static double getDistance(DataVector dataPoint, CentroidVector centroid) {
        double distance = 0;

        Simhash simhash = new Simhash(new ChineseInfoWordSeg());

        distance = simhash.hammingDistance(dataPoint.values, centroid.values);

        return distance;
    }
}
