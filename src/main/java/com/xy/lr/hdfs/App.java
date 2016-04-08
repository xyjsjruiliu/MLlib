package com.xy.lr.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * Created by xylr on 16-4-6.
 * com.xy.lr.hdfs
 */
public class App {
  public static void main(String[] args) throws IOException {
    Configuration conf = new Configuration();
    conf.addResource("core-site.xml");

    String uri = "hdfs://localhost:9000/README.txt";
    FileSystem fileSystem = FileSystem.get(URI.create(uri), conf);

    InputStream inputStream = null;
    try {
      inputStream = fileSystem.open(new Path(uri));
      IOUtils.copyBytes(inputStream, System.out, 4096, false);
    } finally {
      IOUtils.closeStream(inputStream);
    }
  }
}