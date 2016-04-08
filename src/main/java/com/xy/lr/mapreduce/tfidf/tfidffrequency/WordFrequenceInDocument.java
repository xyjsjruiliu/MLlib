package com.xy.lr.mapreduce.tfidf.tfidffrequency;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * Created by xylr on 16-3-21.
 */
public class WordFrequenceInDocument extends Configured implements Tool {
    private static final String OUTPUT_PATH = "1-word-freq";

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(),
                new WordFrequenceInDocument(), args);
        System.exit(res);
    }

    public int run(String[] args) throws Exception {
        Configuration conf = getConf();
        Job job = new Job(conf, "Word Frequence In Document");

        job.setJarByClass(WordFrequenceInDocument.class);
        job.setMapperClass(WordFrequenceInDocMapper.class);
        job.setReducerClass(WordFrequenceInDocReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // FileInputFormat.addInputPath(job, new Path(args[0]));
        FileInputFormat.addInputPath(job, new Path("shakespeare"));
        FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH));


        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static class WordFrequenceInDocMapper extends
            Mapper<LongWritable, Text, Text, IntWritable> {
        public static final IntWritable ONE = new IntWritable(1);
        private static final Pattern PATTERN = Pattern.compile("\\w+");

        /*
         * @input: (docid, contents)
         *
         * @output: ((term, docid), 1)
         */
        @Override
        protected void map(LongWritable key, Text value,
                           Context context)
                throws IOException, InterruptedException {
            FileSplit fileSplit = (FileSplit) context.getInputSplit();
            String docid = fileSplit.getPath().getName();

            Matcher m = PATTERN.matcher(value.toString());
            while (m.find()) {
                String term = m.group().toLowerCase();
                context.write(new Text(term + " " + docid), ONE);
            }
        }

    }

    public static class WordFrequenceInDocReducer extends
            Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable tf = new IntWritable();

        /*
         * @input: ((term, docid), [1,....])
         *
         * @output: ((term, docid), tf)
         */
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values,
                              Context context)
                throws IOException, InterruptedException {
            int sum = 0;

            for (IntWritable val : values)
                sum += val.get();

            tf.set(sum);
            context.write(key, tf);
        }

    }

}
