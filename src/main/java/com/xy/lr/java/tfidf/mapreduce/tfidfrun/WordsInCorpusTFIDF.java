package com.xy.lr.java.tfidf.mapreduce.tfidfrun;

/**
 * Created by xylr on 16-3-21.
 */
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class WordsInCorpusTFIDF extends Configured implements Tool {
    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new WordsInCorpusTFIDF(),
                args);
        System.exit(res);
    }

    public int run(String[] args) throws Exception {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);

        // count document count(N)
        FileStatus[] listStatus = fs.listStatus(new Path("shakespeare"));
        conf.setInt("N", listStatus.length);

        Job job = new Job(conf, "TF-IDF of Words in Corpus");

        job.setJarByClass(WordsInCorpusTFIDF.class);
        job.setMapperClass(WordsInCorpusTFIDFMapper.class);
        job.setReducerClass(WordsInCorpusTFIDFReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        TextInputFormat.addInputPath(job, new Path("2-word-counts"));
        TextOutputFormat.setOutputPath(job, new Path("3-tf-idf"));

        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static class WordsInCorpusTFIDFMapper extends
            Mapper<LongWritable, Text, Text, Text> {
        private static int N;

        @Override
        protected void setup(
                Context context)
                throws IOException, InterruptedException {
            Configuration conf = context.getConfiguration();
            N = conf.getInt("N", 0);
        }

        @Override
        protected void map(LongWritable key, Text value,
                           Context context)
                throws IOException, InterruptedException {
            String[] fields = value.toString().split("\\s+");
            String term = fields[0];
            String docid = fields[1];
            int tf = Integer.parseInt(fields[2]);
            int n = Integer.parseInt(fields[3]);

            double idf = Math.log(N / n);
            double tfidf = tf * idf;

            context.write(new Text(term + "@" + docid),
                    new Text(String.valueOf(tfidf)));
        }

    }

    public static class WordsInCorpusTFIDFReducer extends
            Reducer<Text, Text, Text, Text> {

        /*
         * The identity function
         */
        @Override
        protected void reduce(Text key, Iterable<Text> values,
                              Context context)
                throws IOException, InterruptedException {
            for (Text value : values)
                context.write(key, value);
        }
    }
}
