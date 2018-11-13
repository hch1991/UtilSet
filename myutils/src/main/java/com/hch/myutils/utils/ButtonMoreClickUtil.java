package com.hch.myutils.utils;

/**
 * 防多次点击
 * Created by wyq on 2017/10/17.
 */
public class ButtonMoreClickUtil {
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            MLog.d("多次点击");
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
