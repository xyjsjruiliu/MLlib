package com.xy.lr.mapreduce.simhash;

import com.xy.lr.java.simhash.ChineseInfoWordSeg;
import com.xy.lr.java.simhash.Simhash;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by xylr on 16-4-4.
 * com.xy.lr.mapreduce.simhash
 */
public class SimhashMapper extends
        Mapper<LongWritable, Text, Text, Text> {
  //计算文本simhash值
  private static Simhash simhash = new Simhash(new ChineseInfoWordSeg());

  @Override
  public void map (LongWritable key, Text value, Context context) {
    String eachLine = value.toString();
    if (eachLine.split("\t").length != 2) {

    } else {
      //文章编号
      String documentID = eachLine.split("\t")[0];
      //文章分词结果
      String documentWordSeg = eachLine.split("\t")[1];
      try {
        context.write(new Text("Simhash"),new Text(documentID + ":" + simhash.simhash64(documentWordSeg)));
      } catch (IOException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
