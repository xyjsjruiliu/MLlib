package com.xy.lr.mapreduce.simhash;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by xylr on 16-4-4.
 * com.xy.lr.mapreduce.simhash
 */
public class SimhashReducer extends Reducer<Text, Text, Text, Text> {
  @Override
  public void reduce(Text key, Iterable<Text> Values, Context context) {
    //遍历
    for (Text text : Values) {
      String value = text.toString();
      //文章编号
      String documentID = value.split(":")[0];
      //文章simhash值
      String documentSimhash = value.split(":")[1];
      try {
        context.write(new Text(documentID), new Text(documentSimhash));
      } catch (IOException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}