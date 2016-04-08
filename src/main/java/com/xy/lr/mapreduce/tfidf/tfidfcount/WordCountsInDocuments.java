package com.xy.lr.mapreduce.tfidf.tfidfcount;

/**
 * Created by xylr on 16-3-21.
 */
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class WordCountsInDocuments extends Configured implements Tool {
    private static final String OUTPUT_PATH = "2-word-counts";
    private static final String INPUT_PATH = "1-word-freq";

    public int run(String[] args) throws Exception {

        Configuration conf = getConf();
        Job job = new Job(conf, "Count n");

        job.setJarByClass(WordCountsInDocuments.class);
        job.setMapperClass(WordCountsForDocsMapper.class);
        job.setReducerClass(WordCountsForDocsReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(INPUT_PATH));
        FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH));

        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(),
                new WordCountsInDocuments(), args);
        System.exit(res);
    }

    public static class WordCountsForDocsMapper extends
            Mapper<LongWritable, Text, Text, Text> {

        /*
         * @input: ((term, docid), tf)
         *
         * @output: (term, (docid, tf, 1))
         */
        @Override
        protected void map(LongWritable key, Text value,
                           Context context)
                throws IOException, InterruptedException {
            String[] input = value.toString().split("\\s+");
            String term = input[0];
            String docid = input[1];
            int tf = Integer.parseInt(input[2]);

            context.write(new Text(term), new Text(docid + " " + tf)); // skip 1
        }

    }

    public static class WordCountsForDocsReducer extends
            Reducer<Text, Text, Text, Text> {

        /*
         * @input: (term, [(docid, tf, 1), ....])
         *
         * @output: ((term, docid), (tf, n))
         */
        @Override
        protected void reduce(Text key, Iterable<Text> values,
                              Context context)
                throws IOException, InterruptedException {
            Map<Text, String> counter = new HashMap<Text, String>();
            int n = 0;

            for (Text value : values) {
                String[] docid_tf = value.toString().split("\\s+");
                String docid = docid_tf[0];
                String tf = docid_tf[1];

                counter.put(new Text(key.toString() + " " + docid), tf);
                n++;
            }

            for (Text term_docid : counter.keySet()) {
                String tf = counter.get(term_docid);
                context.write(term_docid, new Text(tf + " " + n));
            }
        }
    }

}
