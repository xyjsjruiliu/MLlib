package com.xy.lr.java.fastclustering;

import com.xy.lr.java.fastclustering.algorithm.DensityPeakCluster;
import com.xy.lr.java.fastclustering.datainput.DataReader;
import com.xy.lr.java.fastclustering.datainput.Sample;
import com.xy.lr.java.fastclustering.evaluate.Evaluation;

import java.util.ArrayList;

public class DensityPeakClusteringTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataReader reader = new DataReader();
		//读取训练数据集
//		reader.readData( "data/iris.data", 5, "," );
		reader.readData("data/clustering.txt");
		ArrayList<Sample> samples = reader.getSamples();

		System.out.println(samples.size());
		
		DensityPeakCluster cluster = new DensityPeakCluster(samples);
		//计算任意样本之间的距离
		cluster.calPairDistance();
		//计算截断距离
		double dc = cluster.findDC();
		System.out.println( "截断距离：" + dc );
		//计算局部密度
		cluster.calRho(dc);
		//计算比自身局部密度最大点的距离，并且选择最小值
		cluster.calDelta();
		//聚类
		cluster.clustering(0.1, 1);
		System.out.println("cluster center index list is "+cluster.getCenterList());
		//预测类别
		cluster.predictLabel();
		
		//预测正确率
		Evaluation evaluation = new Evaluation(samples);
		evaluation.precision();
	}

}
