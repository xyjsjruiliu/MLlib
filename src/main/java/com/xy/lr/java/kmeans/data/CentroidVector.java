package com.xy.lr.java.kmeans.data;

import com.xy.lr.java.simhash.ChineseInfoWordSeg;
import com.xy.lr.java.simhash.Simhash;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xylr on 16-3-16.
 */
public class CentroidVector {
    //聚类中心标号
    public int id;
    //聚类中心属性
    public String values;
    //聚类中其他节点
    public List<DataVector> associatedDataPointList;

    /**
     *
     * @param id
     * @param values
     */
    public CentroidVector(int id, String values) {
        this.id = id;
        this.values = values;

        associatedDataPointList = new ArrayList<DataVector>();
    }

    /**
     * 添加其他节点
     * @param dataPoint 其他节点
     */
    public void asoociateDataPoint(DataVector dataPoint) {
        this.associatedDataPointList.add(dataPoint);
    }

    /**
     * 获取新的聚类中心
     * @return 新的聚类中心
     */
    public CentroidVector getAvegrageCentroid() {
//        int[]values = new int[this.values.length];
//
//        for(int i = 0; i < this.values.length; i++) {
//            for(DataPoint dataPoint : associatedDataPointList) {
//                values[i] = values[i] + dataPoint.values[i];
//            }
//
//            values[i] = values[i]/associatedDataPointList.size();
//        }

        CentroidVector avgCentroid = new CentroidVector(1, "");
//        avgCentroid.associatedDataPointList.addAll(this.associatedDataPointList);

        return avgCentroid;
    }

    /**
     * 计算聚类中心之间的距离
     * @param centroid1 聚类中心1
     * @param centriod2 聚类中心2
     * @return 距离
     */
    public static double getDistance(CentroidVector centroid1, CentroidVector centriod2) {
        double distance = 0;

        Simhash simhash = new Simhash(new ChineseInfoWordSeg());

        distance = simhash.hammingDistance(centroid1.values, centriod2.values);

        return distance;
    }
}
