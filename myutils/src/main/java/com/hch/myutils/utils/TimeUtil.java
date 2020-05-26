package com.hch.myutils.utils;

import android.os.Handler;

import com.hch.myutils.interfaces.CountDownTimeListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author hechuang
 * @ClassName: TimeUtil.java
 * @Description: TODO
 * @date 2018/10/25 15:08
 */
public class TimeUtil {

    private static TimeUtil timeUtil = null;

    // 双重检查
    public static TimeUtil getInstance() {
        if (timeUtil == null) {
            synchronized (WifiAdminUtil.class) {
                if (timeUtil == null) {
                    timeUtil = new TimeUtil();
                }
            }
        }
        return timeUtil;
    }

    // 构造器
    private TimeUtil() {
    }

    private boolean isStart = false;

    public boolean isCurrentInTimeScope(int beginHour, int endHour) {
        return isCurrentInTimeScope(beginHour, 0, endHour, 0);
    }

    public boolean isCurrentInTimeScope(int beginHour, int beginMin, int endHour, int endMin) {
        return isCurrentInTimeScope(beginHour, beginMin, 0, endHour, endMin, 0);
    }

    public boolean isCurrentInTimeScope(int beginHour, int beginMin, int beginSecond, int endHour, int endMin, int endSecond) {
        return isCurrentInTimeScope(beginHour, beginMin, beginSecond, 0, endHour, endMin, endSecond, 0);
    }

    /**
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
     * @Description: TODO 判断当前时间是否在指定时间段内
     * @author : hechuang
     */
    public boolean isCurrentInTimeScope(int beginHour, int beginMin, int beginSecond, int beginMilli, int endHour, int endMin, int endSecond, int endMilli) {
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
    public long getSpeDate(int hour, int minute, int second,
                           int milliSecond) {

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY, hour);

        cal.set(Calendar.SECOND, second);

        cal.set(Calendar.MINUTE, minute);

        cal.set(Calendar.MILLISECOND, milliSecond);

        return cal.getTimeInMillis();
    }

    private int countDownNum;
    private CountDownTimeListener mCountDownTimeListener;

    /**
     * @param
     * @return 返回类型
     * @throws
     * @Description: TODO 开启倒计时 重复调用则是重置计时器
     * @author hechuang
     * @create 2019/2/22
     */
    public void startCountDown(int timeNum, CountDownTimeListener countDownTimeListener) {
        countDownNum = timeNum;
        if (!isStart) {
            mCountDownTimeListener = countDownTimeListener;
            handler.postDelayed(runnable, 1000);
        }
    }

    /**
     * @param
     * @return 返回类型
     * @throws
     * @Description: TODO 停止倒计时
     * @author hechuang
     * @create 2019/3/29
     */
    public void stopCountDown() {
        if (isStart) {
            handler.removeCallbacks(runnable);
        }
        isStart = false;
    }


    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            countDownNum--;
            if (countDownNum > -1) {
                isStart = true;
                mCountDownTimeListener.surplusTime(countDownNum);
                handler.postDelayed(this, 1000);
            } else {
                isStart = false;
                handler.removeCallbacks(this);
            }
        }
    };

    /**
     * @param
     * @return 返回类型
     * @throws
     * @Description: TODO 获取定时器是否开启
     * @author hechuang
     * @create 2019/3/29
     */
    public boolean isStart() {
        return isStart;
    }

    /**
     * 时间戳转换成日期格式字符串
     * @param seconds 精确到秒的字符串
     * @return
     */
    public static String timeStamp2Date(String seconds,String format) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds+"000")));
    }
    /**
     * 日期格式字符串转换成时间戳
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date_str,String format){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime()/1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
