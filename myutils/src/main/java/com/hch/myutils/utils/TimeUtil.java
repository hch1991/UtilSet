package com.hch.myutils.utils;

import java.util.Calendar;

/**
 * @author hechuang
 * @ClassName: TimeUtil.java
 * @Description: TODO
 * @date 2018/10/25 15:08
 */
public class TimeUtil {

    public static boolean isCurrentInTimeScope(int beginHour, int endHour) {
        return isCurrentInTimeScope(beginHour,0,endHour,0);
    }
    public static boolean isCurrentInTimeScope(int beginHour, int beginMin, int endHour, int endMin) {
        return isCurrentInTimeScope(beginHour,beginMin,0,endHour,endMin,0);
    }
    public static boolean isCurrentInTimeScope(int beginHour, int beginMin, int beginSecond, int endHour, int endMin, int endSecond) {
        return isCurrentInTimeScope(beginHour,beginMin,beginSecond,0,endHour,endMin,endSecond,0);
    }
    /**
     * @Description: TODO 判断当前时间是否在指定时间段内
     * @author : hechuang
     * @param : beginHour 开始时
     * @param : beginMin 开始分
     * @param : beginSecond 开始秒
     * @param : beginMilli 开始毫秒
     * @param : endHour 结束时
     * @param : endMin 结束分
     * @param : endSecond 结束秒
     * @param : endMilli 结束毫秒
     * @return :
     * created at 2018/10/25 15:33
     */
    public static boolean isCurrentInTimeScope(int beginHour, int beginMin, int beginSecond, int beginMilli, int endHour, int endMin, int endSecond, int endMilli) {
        long aDayInMillis = 1000 * 60 * 60 * 24;
        long spe1 = getSpeDate(beginHour, beginMin, beginSecond, beginMilli); // 09:30 分时时间
        long spe2 = getSpeDate(endHour, endMin, endSecond, endMilli) + aDayInMillis; // 11:30 分时时间
        return System.currentTimeMillis() > spe1
                && System.currentTimeMillis() < spe2;
    }

    /***
     * 获取指定一天中指定的时间的时间戳
     *
     * @param hour
     * @param minute
     * @param second
     * @param milliSecond
     * @return
     */
    private static long getSpeDate(int hour, int minute, int second,
                                   int milliSecond) {

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY, hour);

        cal.set(Calendar.SECOND, second);

        cal.set(Calendar.MINUTE, minute);

        cal.set(Calendar.MILLISECOND, milliSecond);

        return cal.getTimeInMillis();
    }
}
