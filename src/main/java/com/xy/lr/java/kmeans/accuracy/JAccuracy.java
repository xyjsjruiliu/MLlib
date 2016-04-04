package com.xy.lr.java.kmeans.accuracy;

import com.xy.lr.java.tools.file.JFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xy.lr.java.kmeans.accuracy.Constants.*;

/**
 * Created by xylr on 16-3-30.
 * com.xy.lr.java.kmeans.accuracy
 */
public class JAccuracy {
    public Map<String, List<String>> maps;

    public JAccuracy() {
        maps = new HashMap<String, List<String>>();

        File auto = new File(AUTO);
        File sport = new File(SPORT);
        File house = new File(HOUSE);
        File finance = new File(FINANCE);
        File digi = new File(DIGI);
        File tech = new File(TECH);
        File ent = new File(ENT);
        File fashion = new File(FASHION);
        File eul = new File(CUL);
        File edu = new File(EDU);

        ArrayList<String> lines = JFile.getAllLines(auto, "utf-8");
    }

  /**
   *
   * @return
   */
  public Map<String, List<String>> getMaps() {
        return maps;
    }

  /**
   *
   * @param maps
   */
  public void setMaps(Map<String, List<String>> maps) {
        this.maps = maps;
  }
    public static void main(String[] args) {
        Map<String, List<String>> maps = new JAccuracy().getMaps();

        System.out.println(maps.size());

    }
}
