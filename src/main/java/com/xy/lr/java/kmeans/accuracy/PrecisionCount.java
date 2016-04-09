package com.xy.lr.java.kmeans.accuracy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrecisionCount {

	public static void main(String[] args) throws IOException {
		PrecisionCount totalcount=new PrecisionCount();
//		double precision=totalcount.count("data/standard", "data/result");
		double precision=totalcount.count("data/K-means/resources/",
						"data/K-means/Clustering/");
		System.out.println("准确率"+precision);
	}
	/**
	 * 统计准确率
	 * @param result 聚类结果
	 * @param standard 标准文件
	 * @return
	 * @throws IOException
	 */
	public double count(String result,String standard) throws IOException{
		double correctSum=0.0;
		double precision=0.0;
		File srcFile=new File(result);
    	if(srcFile.isDirectory()){
    		File[] files=srcFile.listFiles();
    		for (File file : files) {
    			correctSum+=countCluster(file,standard);
    			System.out.println("正确数"+correctSum);
    		}
    		 precision=correctSum/47958;
    		}
		return precision;
	}
	/**
	 * 计算聚类结果中与标准文件重合度最高的
	 * @param file 聚类文件
	 * @param standard 标准文件集
	 * @return
	 * @throws IOException
	 */
	private int countCluster(File file,String standard) throws IOException{
		List<String> tempList=new ArrayList<String>();
		tempList=toArrayList(file);   
		File[] files=new File(standard).listFiles();
		int totalMax=Integer.MIN_VALUE;
		for (File file2 : files) {
			List<String> destlist=new ArrayList<String>();
			destlist=toArrayList(file2);
			int countResult=correct(tempList,destlist);
			if(countResult>totalMax){
				totalMax=countResult;
			}
		}
		return totalMax;
	}
	/**
	 * 将文件中的内容存放到list
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private List<String> toArrayList(File file) throws IOException {
		List<String> list=new ArrayList<String>();
		BufferedReader in=new BufferedReader(new FileReader(file));
		String id=null;
		while(null !=(id=in.readLine())){
			list.add(id);
		}
		in.close();
		return list;
	}
	/**
	 * 求两个集合的交集
	 * @param list 聚类结果
	 * @param destlist 标准结果
	 * @return 交集数
	 */
	private int correct(List<String> list, List<String> destlist) {
		int correctness=0;
		for (String string : list) {
			if(destlist.contains(string)){
				correctness++;
			}
		}
		return correctness;	
	}
	
}
