package com.xy.lr.java.kmeans.core;

import java.util.ArrayList;
import java.util.List;

import com.xy.lr.java.kmeans.data.Centroid;
import com.xy.lr.java.kmeans.data.CentroidVector;
import com.xy.lr.java.kmeans.data.DataPoint;
import com.xy.lr.java.kmeans.data.DataVector;

/**
 * @author xylr
 * k-means聚类
 */
public class K_Means {

	/**
	 * 初始化聚类
	 * @param centroidCount 聚类中心个数
	 * @param dataPoints 数据集
     * @return 初始化聚类中心
     */
	private List<Centroid> intializeCentroids(int centroidCount,
													List<DataPoint> dataPoints) {
		List<Centroid> centriods = new ArrayList<Centroid>();
		
		for(int i = 0; i < centroidCount; i++)
			centriods.add(new Centroid(i+1, dataPoints.get(i).values));
		
		return centriods;
	}

	/**
	 * 聚类
	 * @param dataPoints 数据集
	 * @param centroidCount 聚类中心个数
	 * @param threshold 阈值
     * @return 聚类中心
     */
	public List<Centroid> k_Means_Clustering(List<DataPoint> dataPoints,
											 int centroidCount, double threshold) {
		//初始化聚类中心
		List<Centroid> centroids = this.intializeCentroids(centroidCount, dataPoints);
		//新的聚类中心
		List<Centroid> newCentroids = new ArrayList<Centroid>();

		//以下两个值用于计算阈值
		double systemDisplacement = 0;
		double newSystemDisplacement  = 0;
		
		do{
			newCentroids.clear();//清除上一步的聚类中心结果
			newSystemDisplacement = 0;
			
			for(Centroid centroid : centroids)//清除聚类中心其他的节点
				centroid.associatedDataPointList.clear();

			//对于每个实体
			for(DataPoint dataPoint : dataPoints) {
				double minDistance = Double.MAX_VALUE;
				Centroid minDistanceCentroid = null;

				//计算到所有聚类中心的距离， 选择距离最近的
				for(Centroid centroid : centroids) {
					double distance = DataPoint.getDistance(dataPoint, centroid);//点到聚类中心的距离
					if(distance < minDistance) {//当前距离小于最小距离，赋值新的
						minDistanceCentroid = centroid;
					    minDistance = distance;
					}		
				}

				//将其他节点分配到聚类中心
				minDistanceCentroid.asoociateDataPoint(dataPoint);
			}

			//计算新的聚类中心
			for(Centroid centroid: centroids) {
				Centroid newCentroid = centroid.getAvegrageCentroid();//新的聚类中心
				newCentroids.add(newCentroid);
				//上一轮中心与这一轮中心之间的距离，累加
				newSystemDisplacement = newSystemDisplacement + Centroid.getDistance(centroid, newCentroid);
			}
			
			centroids.clear();//清空聚类中心
			centroids.addAll(newCentroids);
		}
		while(Math.abs(newSystemDisplacement - systemDisplacement) > threshold);
		
		return centroids;	
	}
}