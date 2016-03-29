package com.xy.lr.java.kmeans.data;

/**
 * @author xylr
 * 每个节点的属性和标签
 */
public class DataPoint {

	//节点编号
	public int id;
	//节点属性
	public double[] values;
	//节点标签
	public int label;

	/**
	 * 构造函数
	 * @param id 编号
	 * @param values 属性
	 * @param label 标签
     */
	public DataPoint(int id, double[]values, int label) {
		this.id = id;
		this.values = values;
		this.label = label;
	}

	/**
	 * 计算两个节点之间的距离
	 * @param dataPoint1 节点1
	 * @param dataPoint2 节点2
     * @return 节点距离
     */
	public static double getDistance(DataPoint dataPoint1, DataPoint dataPoint2) {
		double distance = 0;
		
		for(int i = 0; i < dataPoint1.values.length; i++)
			distance = distance + Math.pow(dataPoint1.values[i] - dataPoint2.values[i], 2);
		
		distance = Math.sqrt(distance);
		
		return distance;
	}

	/**
	 * 计算节点个聚类中心之间的距离
	 * @param dataPoint 节点
	 * @param centroid 聚类中心
     * @return 距离
     */
	public static double getDistance(DataPoint dataPoint, Centroid centroid) {
		double distance = 0;
		
		for(int i = 0; i < dataPoint.values.length; i++)
			distance = distance + Math.pow(dataPoint.values[i] - centroid.values[i], 2);
		
		distance = Math.sqrt(distance);
		
		return distance;
	}

	/**
	 * getter and setter
     */
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

	public int getLabel() {
		return label;
	}

	public void setLabel(int label) {
		this.label = label;
	}
}
