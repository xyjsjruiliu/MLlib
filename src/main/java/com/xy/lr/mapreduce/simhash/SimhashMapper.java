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
  private static Simhash simhash = new Simhash(new ChineseInfoWordSeg());

  @Override
  public void map (LongWritable key, Text value, Context context) {
    String eachLine = value.toString();

    try {
      context.write(new Text(""),new Text(simhash.simhash64(eachLine)));
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
