package com.xy.lr.java.kmeans.accuracy;

import com.xy.lr.java.kmeans.data.Centroid;
import com.xy.lr.java.kmeans.data.DataPoint;
import com.xy.lr.java.kmeans.data.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.List;



/**
 * @author fan
 * 文件帮助类
 */
public class FileHandler {
	
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
		
		/*//TEST: Adding headers to data
		for(int i = 0 ; i< 10; i++){ fileWriter.append("A,");};
		fileWriter.append("X,Y\n");*/
		int i=1;
		for(Centroid centroid : centroids) {
			FileWriter fileWriter = new FileWriter(filePath+i+".txt");
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
			i++;
			fileWriter.close();
		}
	}
	/**
	 * 将标准聚类中每项的id抽取出来
	 * @param src 源文件夹
	 * @param dest 目的文件夹
	 */
    public static void overWrite(String src,String dest){
    	File srcFile=new File(src);
    	if(srcFile.isDirectory()){
    		File[] files=srcFile.listFiles();
    		for (File file : files) {
				extractFile(file,dest);
			}
    	}
    }
    /**
     * 将一个文件中的内容写到另一个文件中去
     * @param file 源文件
     * @param dest 目的文件
     */
    private static void extractFile(File file,String dest){
    	File destFile=new File(dest,file.getName());
    	try {
			BufferedReader in=new BufferedReader(new FileReader(file));
			BufferedWriter out=new BufferedWriter(new FileWriter(destFile));
			String str=null;
			while(null !=(str=in.readLine())){
				String id=str.split(",")[0];
			//String id=str.split("\t")[0];
				out.write(id);
				out.newLine();
			}
			out.flush();
			out.close();
			in.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();	
		   } catch (IOException e) {
			e.printStackTrace();
		 }
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