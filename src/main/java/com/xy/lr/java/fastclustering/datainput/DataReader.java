package com.xy.lr.java.fastclustering.datainput;

import com.xy.lr.java.kmeans.data.Constants;
import com.xy.lr.java.tools.file.JFile;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
* 获取训练数据集
*/

public class DataReader {
	//训练数据集
	private ArrayList<Sample> samples;

	public void readData(String filePath) {
		samples = new ArrayList<Sample>();

		List<String> lines = JFile.getAllLines(new File(Constants.sourceFilePath), "utf-8");

		for (String line : lines) {
			int n = line.indexOf(",");

			String id = line.substring(0, n);
			String w = line.substring(n + 1, line.length() - 2);

			String[] seg = w.split(",");

			//属性集合
			double[] atts = new double[ 10 ];

			for(int i = 0; i< 10; i++) {
				atts[i] = Double.parseDouble(seg[i]);
			}

//			System.out.println(id + "\t" + w);
			samples.add(new Sample(atts, id));
		}
	}

	/**
	 * 读取训练数据集
	 * @param filePath 训练数据集的路径
	 */
	public void readData( String filePath, int number, String splitString ) {
		File f = new File( filePath );
		samples = new ArrayList<Sample>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			//每一行的数据
			String line = null;
			//遍历数据集
			while((line = br.readLine())!=null ) {
				//如果本行数据为空，则不处理
				if(line.length() ==0)
					continue;
				//已“,”作为分隔符
				String[] seg = line.split( splitString );
				//System.out.println(seg.length);
				if(seg.length < number)
					continue;
				//属性的个数
				int numberOfAttr = seg.length - 1;

				//属性集合
				double[] atts = new double[ numberOfAttr ];

				for(int i = 0; i< numberOfAttr; i++) {
					atts[i] = Double.parseDouble(seg[i]);
				}
				//标签
				String label = seg[ numberOfAttr ];
				//添加新的数据集
				samples.add(new Sample(atts, label));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取训练数据集
	 * @return 训练数据集
	 */
	public ArrayList<Sample> getSamples() {
		return this.samples;
	}
	
	/**
	 * main
	 * @param args 参数列表
	 */
	public static void main(String[] args) {
		DataReader reader = new DataReader();
//		reader.readData( "data/clustering.txt", 5, "," );
		reader.readData( "data/clustering.txt");
		System.out.println(reader.getSamples());

	}
}
