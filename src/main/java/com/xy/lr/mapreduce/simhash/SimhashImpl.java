package com.xy.lr.mapreduce.simhash;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;

/**
 * Created by xylr on 16-4-4.
 * com.xy.lr.mapreduce.simhash
 */
public class SimhashImpl {
  public static void main(String[] args) {
    Configuration conf = new Configuration();
    String[] otherArgs = new String[]{};
    try {
      otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
