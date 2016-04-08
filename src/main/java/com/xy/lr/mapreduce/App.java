package com.xy.lr.mapreduce;

import java.util.StringTokenizer;

/**
 * Created by xylr on 16-4-4.
 * com.xy.lr.mapreduce
 */
public class App {
  public static void main(String[] args) {
    StringTokenizer str = new StringTokenizer("asd ad asd asd");
    while (str.hasMoreTokens()) {
      System.out.println(str.nextToken());
    }
  }
}
