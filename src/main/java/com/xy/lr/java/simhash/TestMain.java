package com.xy.lr.java.simhash;

import com.xy.lr.java.tools.time.DateTime;

/**
 * Created by xylr on 16-3-15.
 */
public class TestMain {
    public static void main(String[] args) {
        DateTime dateTime = new DateTime();
        Simhash simhash = new Simhash(new ChineseInfoWordSeg());

        String a = simhash.simhash64("the cat sat on the mat");
        String b = simhash.simhash64("the cat sat on the mat");
        String c = simhash.simhash64("we all scream for ice cream");

        System.out.println(simhash.hammingDistance(a, b));
        System.out.println(simhash.hammingDistance(a, c));
        System.out.println(simhash.hammingDistance(b, c));

        DateTime dateTime1 = new DateTime();
    }
}
