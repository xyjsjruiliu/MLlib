package com.xy.lr.java.nlp.stopwords;

import com.xy.lr.java.tools.file.JFile;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xylr on 16-3-17.
 * 停用词表
 */
public class StopWords {
    private List<String> stopWordList = new ArrayList<String>();
    private int swSize;


    private static final String url = "jdbc:mysql://localhost:3306/nlp?" +
            "user=root&password=110";
    private static Connection conn = null;

    /**
     * 获取停用词表
     * @return 停用词表
     */
    public List<String> getStopWordLists() {
        return this.stopWordList;
    }

    /**
     * 构造函数
     */
    public StopWords() {
        this.stopWordList = getStopWordList();
        this.swSize = getCount();
    }

    /**
     *
     */
    private int getCount() {
        int count = 0;
        Statement stmt=null;
        try{
            stmt = conn.createStatement();
            String strSQL="select count from sizes";
            ResultSet rs = stmt.executeQuery(strSQL);

            while(rs.next()) {
                count = rs.getInt(1);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 静态构造函数
     */
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("没有找到mysql.jar");
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("mysql 链接失败");
            e.printStackTrace();
        }
    }

    /**
     * 删除一个停用词
     * @param stopword 待删除的停用词
     * @return 删除是否成功
     */

    public boolean deleteStopWord(String stopword) {
        //如果当前停用词表为空，或者待删除停用词不存在，则删除失败
        if(this.stopWordList.isEmpty() || !containStopWord(stopword))
            return false;
        else {
            try {
                Statement stmt = conn.createStatement();

                String sql = "delete from stopword where sw = '" + stopword + "'";
                stmt.execute(sql);

                this.stopWordList.remove(stopword);
            } catch (SQLException e) {
                System.out.println(stopword);
//                e.printStackTrace();
            }
            return true;
        }
    }

    /**
     * 插入新的停用词表
     * @param listsStop 停用词列表
     * @return 插入是否成功
     */
    public boolean insertStopWord(List<String> listsStop) {
        listsStop.removeAll(this.stopWordList);

        if(listsStop.size() == 0)
            return false;
        else {
            for(String list : listsStop) {
                insertStopWord(list);
            }
        }
        return true;
    }

    /**
     * 插入新的停用词
     * @param sw 停用词
     * @return 插入是否成功
     */
    public boolean insertStopWord(String sw) {
        //表示存在该停用词
        if(containStopWord(sw)) {
            System.err.println("该停用词已存在");
            return false;
        }
        else{
            try {
                Statement stmt = conn.createStatement();
                //当前停用词表的大小
                int size = this.swSize;
                this.swSize ++;

                String sql = "insert into stopword values('" + this.swSize + "','" + sw + "')";
                String updateSql = "update sizes set count = " + this.swSize + " where count = " + size;

                //执行
                stmt.execute(sql);
                stmt.execute(updateSql);

                this.stopWordList.add(sw);
            } catch (SQLException e) {
                System.out.println(sw);
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 检查当前词是不是停用词
     * @param sw 待检查的词
     * @return 是否
     */
    public boolean containStopWord(String sw) {
        return this.stopWordList.contains(sw);
    }

    /**
     * 获取停用词表
     * @return 停用词表
     */
    private List<String> getStopWordList() {
        Statement stmt=null;
        try{
            stmt = conn.createStatement();
            String strSQL="select sw from stopword";
            ResultSet rs = stmt.executeQuery(strSQL);

            while(rs.next()) {
                this.stopWordList.add(rs.getString(1));
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return this.stopWordList;
    }

    /**
     * 获取当前停用词表的大小
     * @return 大小
     */
    public int getStopWordListsSize() {
        return this.stopWordList.size();
    }

    /**
     * 获取当前停用词表的大小
     * @return 大小
     */
    private int getStopWordListSize() {
        if(this.stopWordList.size() != 0) {
            return this.stopWordList.size();
        }
        else{
            int number=0;
            Statement stmt=null;
            try{
                stmt = conn.createStatement();
                String strSQL="select count(*) as num from stopword";
                ResultSet rs = stmt.executeQuery(strSQL);

                if(null != rs) {
                    while(rs.next()) {
                        number=rs.getInt("num"); //通过指定别名返回行数
//                    number1=rs.getInt(1); //通过索引返回行数
                    }
                }
            } catch(Exception e) {
                return number;
            }
            return number;
        }
    }

    /**
     * 去除 str 中的所有停用词， 并且按照原格式返回
     * @param str 文章
     * @param sp 分隔符
     * @return 去除停用词之后的str
     */
    public String removeStopWord(String str, String sp) {
        String temp = "";

        for(String stopword : str.split(sp)) {
            if(!containStopWord(stopword))//当前单词是停用词
                temp += stopword + sp;
        }

        temp = temp.substring(0, temp.length() - sp.length());
        return temp;
    }
}
