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
      ArrayList<String> lines1 = JFile.getAllLines(sport, "utf-8");
      ArrayList<String> lines2 = JFile.getAllLines(house, "utf-8");
      ArrayList<String> lines3 = JFile.getAllLines(finance, "utf-8");
      ArrayList<String> lines4 = JFile.getAllLines(digi, "utf-8");
      ArrayList<String> lines5 = JFile.getAllLines(tech, "utf-8");
      ArrayList<String> lines6 = JFile.getAllLines(ent, "utf-8");
      ArrayList<String> lines7 = JFile.getAllLines(fashion, "utf-8");
      ArrayList<String> lines8 = JFile.getAllLines(eul, "utf-8");
      ArrayList<String> lines9 = JFile.getAllLines(edu, "utf-8");
//      print(lines);
//      saveID(lines, "auto");
      saveID(lines1, "sport");
      saveID(lines2, "house");
      saveID(lines3, "finance");
      saveID(lines4, "digi");
      saveID(lines5, "tech");
      saveID(lines6, "ent");
      saveID(lines7, "fashion");
      saveID(lines8, "eul");
      saveID(lines9, "edu");
    }

  /**
   *
   * @return
   */
  public Map<String, List<String>> getMaps() {
        return maps;
    }

  private void print(ArrayList<String> list) {
    for (String string : list) {
      System.out.println(string);
    }
  }

  private void saveID(ArrayList<String> list, String i) {
    for (String string : list) {
      String id = string.split("\t")[0];
      JFile.appendFile("data/K-means/resources/" + i, id);
    }
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
