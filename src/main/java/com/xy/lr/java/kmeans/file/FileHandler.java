package com.xy.lr.java.kmeans.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.xy.lr.java.kmeans.data.Centroid;
import com.xy.lr.java.kmeans.data.Constants;
import com.xy.lr.java.kmeans.data.DataPoint;
import com.xy.lr.java.kmeans.data.DataVector;

/**
 * @author xylr
 * 文件帮助类
 */
public class FileHandler {

	public static List<DataVector> readDataVectors(String filePath) throws IOException {
		List<DataVector> dataPoints = new ArrayList<DataVector>();

		BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));

		String line = "";
		//按行读取
		while((line = bufferedReader.readLine()) != null) {
			String[] tokens = line.split("\t");

			try {
				int id = Integer.valueOf(tokens[0]);
				String values = tokens[1];
				dataPoints.add(new DataVector(id, values, 1));
			}
			catch(NumberFormatException e) {
				//e.printStackTrace();
				System.out.println("Ignoring Row- " + tokens);
			}
		}

		bufferedReader.close();

		return dataPoints;
	}
	/**
	 * 读取文件信息
	 * @param filePath 文件路径
	 * @return 节点信息
	 * @throws IOException
     */
	public static List<DataPoint> readDataPoints(String filePath) throws IOException {
		List<DataPoint> dataPoints = new ArrayList<DataPoint>();

		BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));

		String line = "";
		//按行读取
		while((line = bufferedReader.readLine()) != null) {
			String[] tokens = line.split(Constants.delimiter);

			try {//第一行是编号，最后一行是标签
				int id = Integer.parseInt(tokens[0]);
				int label = Integer.parseInt(tokens[tokens.length-1]);
				double[] values = new double[tokens.length-2];

				// First and last columns are id and label respectively
				for(int i = 1; i < tokens.length-1; i++)
					values[i-1] = Double.parseDouble(tokens[i]);
				
				dataPoints.add(new DataPoint(id, values, label));
			}
			catch(NumberFormatException e) {
				//e.printStackTrace();
				System.out.println("Ignoring Row- " + tokens);
			}
		}
		
		bufferedReader.close();
		
		return dataPoints;
	}

	/**
	 * 信息写入
	 * @param filePath 文件输出路径
	 * @param centroids 聚类中心
	 * @throws IOException
     */
	public static void writeDataPoints(String filePath,
									   List<Centroid> centroids) throws IOException {
		FileWriter fileWriter = new FileWriter(filePath);
		
		/*//TEST: Adding headers to data
		for(int i = 0 ; i< 10; i++){ fileWriter.append("A,");};
		fileWriter.append("X,Y\n");*/
		
		for(Centroid centroid : centroids) {
			for(DataPoint dataPoint: centroid.associatedDataPointList) {
				fileWriter.append(String.valueOf(dataPoint.id));
				
				for(double value: dataPoint.values) {
					fileWriter.append(Constants.delimiter);
					fileWriter.append(String.valueOf(value));
				}
				
				fileWriter.append(Constants.delimiter);
				fileWriter.append(String.valueOf(dataPoint.label));
				
				fileWriter.append(Constants.delimiter);
				fileWriter.append(String.valueOf(centroid.id));
				
				fileWriter.append(Constants.newLineSeparator);
			}
		}
		
		fileWriter.close();
	}

	/**
	 * 测试程序
	 * @param args
     */
	public static void main(String[] args) {
		try {
			List<DataPoint> dataPoints = readDataPoints("data/fileHasndler.txt");
			for(DataPoint dataPoint : dataPoints) {
				System.out.println(dataPoint.getId() + "\t" + dataPoint.getLabel() + "\t" + dataPoint.getValues().length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}