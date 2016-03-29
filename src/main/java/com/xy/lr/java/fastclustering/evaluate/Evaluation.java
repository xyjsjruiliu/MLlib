package com.xy.lr.java.fastclustering.evaluate;



import com.xy.lr.java.fastclustering.datainput.Sample;

import java.util.ArrayList;

/**
* 评估类
*/
public class Evaluation {
	//预测数据集
	private ArrayList<Sample> samples;
	
	/**
	 * 
	 * @param samples 预测数据集
	 */
	public Evaluation(ArrayList<Sample> samples){
		this.samples = samples;
	}
	
	/**
	 * 预测准确率
	 */
	public void precision(){
		//计数器
		int count = 0;
		//遍历预测数据集
		for(Sample sample : samples) {
			//如果预测正确，计数器+1
			if(sample.getPredictLabel() != null && 
					sample.getLabel().equals(sample.getPredictLabel())) {
				count ++;
			}
		}
		System.out.println("precison is "+count * 1.0 / samples.size());
	}
}
