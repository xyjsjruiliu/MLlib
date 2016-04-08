package com.xy.lr.mapreduce.simhash;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;

/**
 * Created by xylr on 16-4-4.
 * com.xy.lr.mapreduce.simhash
 */
public class SimhashImpl {
  public static void main(String[] args) {
    //配置文件
    Configuration conf = new Configuration();
    //参数列表
    String[] otherArgs = null;
    try {
      otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (otherArgs == null) {
      System.err.println("内部错误\nUsage: SimhashImpl <in> <out>");
      System.exit(2);
    }

    if(otherArgs.length != 2){
      System.err.println("Usage: SimhashImpl <in> <out>");
      System.exit(2);
    }
    Job job = null;
    try {
      job = new Job(conf, "SimhashImpl");
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (job != null) {//
      job.setJarByClass(SimhashImpl.class);

      //maper and reducer
      job.setMapperClass(SimhashMapper.class);
      job.setReducerClass(SimhashReducer.class);

      //map output key and value
      job.setMapOutputKeyClass(Text.class);
      job.setMapOutputValueClass(Text.class);

      try {
        FileInputFormat.setInputPaths(job, new Path(otherArgs[0]));
      } catch (IOException e) {
        System.err.println("MapReduce 输入文件异常");
        e.printStackTrace();
      }
      FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));

      try {
        System.exit( job.waitForCompletion(true) ? 0 : 1 );
      } catch (IOException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
  }
}
