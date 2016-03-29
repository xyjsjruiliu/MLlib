package com.xy.lr.java.kmeans.core;

import com.xy.lr.java.kmeans.data.*;
import com.xy.lr.java.kmeans.file.FileHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xylr
 * Main function
 */
public class Operation {

	public static void main(String[] args) {
		List<DataPoint> dataPoints;
		try {
			//读取数据
			dataPoints = FileHandler.readDataPoints(Constants.sourceFilePath);
			//k-means聚类
			K_Means k_means = new K_Means();
			//得到最终的聚类中心
			List<Centroid> centriods = k_means.k_Means_Clustering(dataPoints, Constants.centriodCount, Constants.threshold);
			//保存数据
			FileHandler.writeDataPoints(Constants.destinationFilePath, centriods);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

/*
	DIFFERENT MAIN FOR V-FOLD VALIDATION
	public static void main(String[] args) {
		List<DataPoint> dataPoints;
		try 
		{
			dataPoints = FileHandler.readDataPoints(Constants.sourceFilePath);
		
		K_Means k_means = new K_Means();
		int testDataLength = dataPoints.size()/10;
		for(int i = 0; i< 10; i++)
		{
			int startPointTestData = testDataLength * i;
			int endPointTestData = testDataLength * (i+1);
			List<DataPoint> testDataPoints = dataPoints.subList(startPointTestData, endPointTestData);

			List<DataPoint> trainingDataPoints = new ArrayList<DataPoint>();
			trainingDataPoints.addAll(dataPoints.subList(0, startPointTestData));
			trainingDataPoints.addAll(dataPoints.subList(endPointTestData, dataPoints.size()));

			List<Centroid> centroids = k_means.k_Means_Clustering(trainingDataPoints, Constants.centriodCount, Constants.threshold);

			for(Centroid centroid: centroids)
			{
				centroid.associatedDataPointList.clear();
			}

			for(DataPoint testDataPoint: testDataPoints)
			{
				double minCentroidDistance = Integer.MAX_VALUE;
				Centroid minDistanceCentroid = null;

				for(Centroid centroid: centroids)
				{
					double distance = DataPoint.getDistance(testDataPoint, centroid);
					if(distance < minCentroidDistance)
					{
						minCentroidDistance = distance;
						minDistanceCentroid = centroid;
					}
				}

				minDistanceCentroid.asoociateDataPoint(testDataPoint);
			}

			FileHandler.writeDataPoints(Constants.destinationFilePath + i + ".csv", centroids);
		}

		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
*/
}
