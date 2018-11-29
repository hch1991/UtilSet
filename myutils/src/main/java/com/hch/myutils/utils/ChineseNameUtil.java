package com.hch.myutils.utils;

import android.content.Context;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hechuang
 * @ClassName: ChineseNameUtil.java
 * @Description: TODO
 * @date 2018/11/29 11:32
 */
public class ChineseNameUtil {

    private List<String> surNameList;
    private List<String> boyNameList;
    private List<String> girlNameList;
    private List<String> allNameList;

    private static ChineseNameUtil instance;

    private ChineseNameUtil(Context context) {
        surNameList = getProvince(context, "SurName.xml");
        boyNameList = getProvince(context, "BoyName.xml");
        girlNameList = getProvince(context, "GirlName.xml");
        allNameList = new ArrayList<>();
        allNameList.addAll(boyNameList);
        allNameList.addAll(girlNameList);
    }

    public static synchronized ChineseNameUtil getInstance(Context context) {
        if (instance == null) {
            instance = new ChineseNameUtil(context);
        }
        return instance;
    }

    /**
     * @param : sex  0、代表是女  1代表是男  其他代表是未知男女或不男不女
     * @return :
     * created at 2018/11/29 14:48
     * @Description: TODO 根据性别返回对应生成的姓名
     * @author : hechuang
     */

    public String getName(int sex) {
        String generateName = "";
        if (sex == 0) {
            String sur = surNameList.get((int) (Math.random() * surNameList.size()));
            String name = girlNameList.get((int) (Math.random() * girlNameList.size()));
            generateName = sur + name;
        } else if (sex == 1) {
            String sur = surNameList.get((int) (Math.random() * surNameList.size()));
            String name = boyNameList.get((int) (Math.random() * boyNameList.size()));
            generateName = sur + name;
        } else {
            String sur = surNameList.get((int) (Math.random() * surNameList.size()));
            String name = allNameList.get((int) (Math.random() * allNameList.size()));
            generateName = sur + name;
        }
        return generateName;
    }
    
    /**
     * @Description: TODO pull解析xml 从xml中读取list列表出来
     * @author : hechuang
     * @param : 
     * @return : 
     * created at 2018/11/29 15:00
     */
    
    private List<String> getProvince(Context context, String fileName) {
        List<String> resultList = new ArrayList<String>();
        //标记是否读取下一个节点的内容
        boolean nextRead = false;
        try {
            XmlPullParser xrpCity = Xml.newPullParser();
            xrpCity.setInput(context.getAssets().open(fileName), "UTF-8");
            while (xrpCity.getEventType() != XmlPullParser.END_DOCUMENT) {
                //假设是開始标签
                if (xrpCity.getEventType() == XmlPullParser.START_TAG) {
                    //获取标签名称
                    String name = xrpCity.getName();
                    //推断标签名称是否等于friend
                    if ("name".equals(name)) {
                        xrpCity.next();
                        resultList.add(xrpCity.getText());
                    }
                }
                //下一个标签
                xrpCity.next();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            resultList = null;
        }
        return resultList;
    }
}
