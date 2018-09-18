package com.hch.myutils.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @ClassName: MyDate
 * @Description: TODO 时间工具类
 * @author hechuang
 * @date 2018/9/18 14:31
 *
 */
public class MyDate {
    public static String getData() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date(System.currentTimeMillis()));
        return date;// 2012年10月03日 23:41:31
    }

    public static String getDateEN() {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = format1.format(new Date(System.currentTimeMillis()));
        return date1;// 2012-10-03 23:41:31
    }

}