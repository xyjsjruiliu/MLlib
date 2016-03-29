package com.xy.lr.java.fastclustering.datainput;

/**
 * 训练数据集类
 * @author xylr
 *
 */
public class Sample {
	//属性集
	private double[] attributes;
	//标签
	private String label;
	//预测标签
	private String predictLabel;
	
	/**
	 * 构造函数，输入是属性集、标签
	 * @param attributes 输入属性集
	 * @param label 标签
	 */
	public Sample(double[] attributes, String label) {
		this.attributes = attributes;
		this.label = label;
		this.predictLabel = new String();
	}
	
	/**
	 * 获取属性集合
	 * @return
	 */
	public double[] getAttributes() {
		return attributes;
	}
	
	/**
	 * 获取标签
	 * @return
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * 获取预测标签
	 * @return 获取预测标签
	 */
	public String getPredictLabel() {
		return predictLabel;
	}
	
	/**
	 * 设置预测标签
	 * @param predictLabel 预测标签
	 */
	public void setPredictLabel(String predictLabel) {
		this.predictLabel = predictLabel;
	}
	
}