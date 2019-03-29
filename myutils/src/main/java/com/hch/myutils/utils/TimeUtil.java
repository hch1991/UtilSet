package com.hch.myutils.utils;

import android.os.Handler;

import com.hch.myutils.interfaces.CountDownTimeListener;

import java.util.Calendar;

/**
 * @author hechuang
 * @ClassName: TimeUtil.java
 * @Description: TODO
 * @date 2018/10/25 15:08
 */
public class TimeUtil {

    private static boolean isStart = false;

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
    public static long getSpeDate(int hour, int minute, int second,
                                   int milliSecond) {

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY, hour);

        cal.set(Calendar.SECOND, second);

        cal.set(Calendar.MINUTE, minute);

        cal.set(Calendar.MILLISECOND, milliSecond);

        return cal.getTimeInMillis();
    }

    private static int countDownNum;
    private static CountDownTimeListener mCountDownTimeListener;

    /**
      * @Description: TODO 开启倒计时 重复调用则是重置计时器
      * @author hechuang
      * @param
      * @return    返回类型
      * @create 2019/2/22
      * @throws
      */
    public static void startCountDown(int timeNum, CountDownTimeListener countDownTimeListener){
        countDownNum = timeNum;
        if(!isStart){
            mCountDownTimeListener = countDownTimeListener;
            handler.postDelayed(runnable, 1000);
        }
    }
    /**
      * @Description: TODO 停止倒计时
      * @author hechuang
      * @param
      * @return    返回类型
      * @create 2019/3/29
      * @throws
      */
    public static  void stopCountDown(){
        isStart = false;
        handler.removeCallbacks(runnable);
    }


    private static Handler handler = new Handler();
    private static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            countDownNum--;
            if(countDownNum > -1){
                isStart = true;
                mCountDownTimeListener.surplusTime(countDownNum);
                handler.postDelayed(this, 1000);
            }else{
                isStart = false;
                handler.removeCallbacks(this);
            }
        }
    };

    /**
      * @Description: TODO 获取定时器是否开启
      * @author hechuang
      * @param
      * @return    返回类型
      * @create 2019/3/29
      * @throws
      */
    public static boolean isStart(){
        return isStart;
    }
}
