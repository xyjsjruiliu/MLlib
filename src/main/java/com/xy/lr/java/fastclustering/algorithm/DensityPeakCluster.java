package com.xy.lr.java.fastclustering.algorithm;



import com.xy.lr.java.fastclustering.datainput.DataReader;
import com.xy.lr.java.fastclustering.datainput.Sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

/**
* 获取
*/

public class DensityPeakCluster {
	/**
	 * 训练数据集
	 */
	private ArrayList<Sample> samples;
	
	/**<index, sample >Map*/
	//private HashMap<Integer, Sample> sampleIndexMap;
	
	/**
	 * 局部密度Map ：<index, densityCount>
	 * */
	private HashMap<Integer, Integer> densityCountMap;
	
	/**
	 * 局部密度由大到小排序的Density list
	 * */
	private ArrayList<Entry<Integer, Integer>> sortedDensityList;

	/**
	 * deltaMap:<index, delta> delta值
	 * */
	private HashMap<Integer, Double> deltaMap;

	/**
	 * 每个样本的最近邻：<sampleIndex, nearestNeighborIndex>
	 * */
	private HashMap<Integer, Integer> nearestNeighborMap;

	/**
	 * 样本对距离：<"index1 index2", distance>
	 */
	private HashMap<String, Double> pairDistanceMap;

	/**
	 * 最大样本距离
	 * */
	private double maxDistance;

	/**
	 * 最小样本距离
	 * */
	private double minDistance;

	/**
	 * 选取的簇中心
	 */
	private ArrayList<Integer> centerList;

	/**
	 * 划分的聚类结果<sampleIndex, clusterIndex>
	 */
	private HashMap<Integer, Integer> clusterMap;

	/**
	 * 构造函数，赋值训练数据集
	 * @param samples 训练数据集
	 */
	public DensityPeakCluster(ArrayList<Sample> samples) {
		this.samples = samples;
//		sampleIndexMap = new HashMap<Integer, Sample>(samples.size());
//		int count = 0;
//		for(Sample s : samples) {
//			sampleIndexMap.put(count++, s);
//		}
	}

	/**
	 * 聚类
	 * @param deltaThreshold delta的阈值
	 * @param rhoThreshold 局部密度的阈值
	 */
	public void clustering(double deltaThreshold, double rhoThreshold) {
		this.centerList = new ArrayList<Integer>();
		this.clusterMap = new HashMap<Integer, Integer>();
		//get centers
		for(Entry<Integer, Double> deltaEntry : deltaMap.entrySet()) {
			if(deltaEntry.getValue() >= deltaThreshold &&
					densityCountMap.get(deltaEntry.getKey()) >= rhoThreshold) {
				centerList.add(deltaEntry.getKey());
				clusterMap.put(deltaEntry.getKey(), deltaEntry.getKey());
			}
		}
		// calculate clusters，注意：一定要按照密度由大到小逐个划分簇（从高局部密度到低局部密度）
		for(Entry<Integer, Integer> candidate : sortedDensityList) {
			if(!centerList.contains(candidate.getKey())) {
				//将最近邻居的类别索引作为该样本的类别索引
				if(clusterMap.containsKey(nearestNeighborMap.get(candidate.getKey()))) {
					clusterMap.put(candidate.getKey(), clusterMap.get(nearestNeighborMap.get(candidate.getKey())));
				} else {
					clusterMap.put(candidate.getKey(), -1);
				}
			}
		}
	}

	/**
	 * 计算比自身局部密度最大点的距离，并且选择最小值
	 */
	public void calDelta() {
		//局部密度由大到小排序
		this.sortedDensityList = new ArrayList<Entry<Integer,Integer>>(this.densityCountMap.entrySet());
		//排序局部密度
		Collections.sort(this.sortedDensityList, new Comparator<Entry<Integer, Integer>>() {
			public int compare(Entry<Integer, Integer> o1,
					Entry<Integer, Integer> o2) {
				if(o1.getValue() > o2.getValue()) return -1;
				else if (o1.getValue() < o2.getValue()) {
					return 1;
				}
				return 0;
			}
		});
		//最临近的点索引
		this.nearestNeighborMap = new HashMap<Integer, Integer>( this.samples.size());
		//delta值
		this.deltaMap = new HashMap<Integer, Double>( this.samples.size());
		//循环遍历已经排序后的数据，计算每个节点的delta值
		for(int i = 0; i < this.sortedDensityList.size(); i++) {
			if(i == 0) {//因为第一条的数据的局部密度最大
				this.nearestNeighborMap.put( this.sortedDensityList.get(i).getKey(), -1);
				this.deltaMap.put(sortedDensityList.get(i).getKey(), this.maxDistance);
			} else {
				//最小的两点之间的距离
				double minDij = Double.MAX_VALUE;
				//最小距离的节点编号
				int index = 0;
				for(int j = 0; j < i; j++) {
					//两个点的距离
					double dis = getDistanceFromIndex(sortedDensityList.get(i).getKey(),
							sortedDensityList.get(j).getKey());
					if(dis < minDij)  {
						index = j;
						minDij = dis;
					}
				}
				nearestNeighborMap.put(sortedDensityList.get(i).getKey(),
						sortedDensityList.get(index).getKey());
				//delta值
				deltaMap.put(sortedDensityList.get(i).getKey(), minDij);
			}
		}

		//输出样本索引+样本局部密度+最近邻索引+delta值
		System.out.println("输出样本索引  样本局部密度  最近邻索引  delta值");
		for(Entry<Integer, Integer> entry : sortedDensityList) {
			System.out.println(entry.getKey()+" "+entry.getValue()+" "+
		nearestNeighborMap.get(entry.getKey())+" "+deltaMap.get(entry.getKey()));
		}
	}

	/**
	 * 根据索引获得两个样本间距离
	 * @param index1
	 * @param index2
	 * @return 样本间的距离
	 */
	private double getDistanceFromIndex(int index1, int index2) {
		if(pairDistanceMap.containsKey(index1+" "+index2)) {
			return pairDistanceMap.get(index1+" "+index2);
		} else {
			return pairDistanceMap.get(index2+" "+index1);
		}
	}

	/**
	 * 计算局部密度
	 * @param dcThreshold 截断距离
	 */
	public void calRho(double dcThreshold) {
		this.densityCountMap = new HashMap<Integer, Integer>(this.samples.size());
		//初始化为0
		for(int i= 0; i < this.samples.size(); i++) {
			this.densityCountMap.put(i, 0);
		}
		//计算局部密度
		for(Entry<String, Double> diss : this.pairDistanceMap.entrySet()) {
			//样本距离小于截断距离
			if(diss.getValue() < dcThreshold) {
				String[] segs = diss.getKey().split(" ");
				int[] indexs = new int[2];
				indexs[0] = Integer.parseInt(segs[0]);
				indexs[1] = Integer.parseInt(segs[1]);
				for(int i = 0; i < indexs.length; i++) {
					//累加局部密度
					this.densityCountMap.put(indexs[i], this.densityCountMap.get(indexs[i]) + 1);
				}
			}
		}
	}

	/**
	 * 计算所有样本每两个样本点的距离，
	 * 并且计算样本中最大的距离和最小的距离
	 */
	public void calPairDistance() {
		//样本对距离
		this.pairDistanceMap = new HashMap<String, Double>();
		//最大样本距离
		this.maxDistance = Double.MIN_VALUE;
		//最小样本距离
		this.minDistance = Double.MAX_VALUE;
		for(int i = 0; i < this.samples.size() - 1; i++) {
			for(int j = i+1; j < this.samples.size(); j++) {
				//样本对之间的距离
				double dis = twoSampleDistance( this.samples.get(i), this.samples.get(j));

				this.pairDistanceMap.put(i+" "+j, dis);
				if(dis > this.maxDistance) this.maxDistance = dis;
				if(dis < this.minDistance) this.minDistance = dis;
			}
		}
	}

	/**
	 * 计算截断距离
	 * @return dc 截断距离
	 */
	public double findDC(){
		double tmpMax = this.maxDistance;
		double tmpMin = this.minDistance;
		//最大和最小样本距离的平均值，初始化截断距离
		double dc = 0.5 * (tmpMax + tmpMin);
		for(int iteration = 0; iteration < 100; iteration ++) {
			int neighbourNum = 0;
			//计算样本距离小于截断距离样本点的个数
			for(Entry<String, Double> dis : this.pairDistanceMap.entrySet()) {
				if(dis.getValue() < dc) neighbourNum += 2;
			}
			//所占的比率
			double neighborPercentage = neighbourNum / Math.pow( this.samples.size(), 2);
			if(neighborPercentage >= 0.01 && neighborPercentage <= 0.02) break;
			if(neighborPercentage > 0.02) {
				tmpMax = dc;
				dc = 0.5 * (tmpMax + tmpMin);
			}
			if(neighborPercentage < 0.01) {
				tmpMin = dc;
				dc = 0.5 * (tmpMax + tmpMin);
			}
			
		}
		return dc;
	}
	
	/**
	 * 计算两个样本的高斯距离
	 * @param sampleA 样本1
	 * @param sampleB 样本2
	 * @return 两个样本的高斯距离
	 */
	private double twoSampleDistance(Sample sampleA, Sample sampleB){
		double[] aData = sampleA.getAttributes();
		double[] bData = sampleB.getAttributes();
		double distance = 0.0;
		for(int i = 0; i < aData.length; i++) {
			distance += Math.pow(aData[i] - bData[i], 2);
		}
		return 1 - Math.exp(distance * (-0.5));
	}
	
	/**
	 * 获取聚类中心
	 * @return 聚类中心节点
	 */
	public ArrayList<Integer> getCenterList() {
		return this.centerList;
	}
	
	/**
	* 构造函数，赋值训练数据集
	*/
	public void predictLabel() {
		for(int i = 0; i < this.samples.size(); i++) {
			//System.out.println(clusterMap.get(i));
			if( this.clusterMap.get(i) != -1)
				this.samples.get(i).setPredictLabel( this.samples.get( this.clusterMap.get(i)).getLabel());
		}
	}
	
	/**
	 * main
	 * @param args 命令参数
	 */
	public static void main(String[] args) {
		DataReader reader = new DataReader();
		reader.readData( "./data/iris.data", 5, "," );
		ArrayList<Sample> samples = reader.getSamples();
		
		DensityPeakCluster cluster = new DensityPeakCluster(samples);
		cluster.calPairDistance();
		double dc = cluster.findDC();
		System.out.println(dc);
		cluster.calRho(dc);
		cluster.calDelta();
		cluster.clustering(0.38, 1);
		System.out.println(cluster.getCenterList());
	}
}
