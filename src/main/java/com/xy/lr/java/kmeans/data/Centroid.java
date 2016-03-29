package com.xy.lr.java.kmeans.data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xylr
 * 聚类中心节点
 */
public class Centroid {

	//聚类中心标号
	public int id;
	//聚类中心属性
	public double[] values;
	//聚类中其他节点
	public List<DataPoint> associatedDataPointList;

	/**
	 * 构造函数
	 * @param id 编号
	 * @param values 属性
     */
	public Centroid(int id, double[] values) {
		this.id = id;
		this.values = values;
		
		this.associatedDataPointList = new ArrayList<DataPoint>();			
	}

	/**
	 * 添加其他节点
	 * @param dataPoint 其他节点
     */
	public void asoociateDataPoint(DataPoint dataPoint) {
		this.associatedDataPointList.add(dataPoint);
	}

	/**
	 * 获取新的聚类中心
	 * @return 新的聚类中心
     */
	public Centroid getAvegrageCentroid() {
		double[]values = new double[this.values.length];

		for(int i = 0; i < this.values.length; i++) {
			for(DataPoint dataPoint : associatedDataPointList) {
				values[i] = values[i] + dataPoint.values[i];
			}
			
			values[i] = values[i]/associatedDataPointList.size();
		}
		
		Centroid avgCentroid = new Centroid(this.id, values);
		avgCentroid.associatedDataPointList.addAll(this.associatedDataPointList);
		
		return avgCentroid;
	}

	/**
	 * 计算聚类中心之间的距离
	 * @param centroid1 聚类中心1
	 * @param centriod2 聚类中心2
     * @return 距离
     */
	public static double getDistance(Centroid centroid1, Centroid centriod2) {
		double distance = 0;
		
		for(int i = 0; i < centroid1.values.length; i++)
			distance = distance + Math.pow(centroid1.values[i] - centriod2.values[i], 2);
		
		distance = Math.sqrt(distance);
		
		return distance;
	}

	/**
	 *
	 * getter and setter
	 *
	 * */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double[] getValues() {
		return values;
	}

	public void setValues(double[] values) {
		this.values = values;
	}

	public List<DataPoint> getAssociatedDataPointList() {
		return associatedDataPointList;
	}

	public void setAssociatedDataPointList(List<DataPoint> associatedDataPointList) {
		this.associatedDataPointList = associatedDataPointList;
	}
}