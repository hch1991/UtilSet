package com.hch.myutils.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * @ClassName: ToastUtil
 * @Description: TODO Toast工具类
 * @author hechuang
 * @date 2018/9/18 14:32
 *
 */
public class ToastUtil {

    public static void showShortToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
    public static void showServiceShortToast(final Context context, String message) {
        Handler handler=new Handler(Looper.getMainLooper());
        handler.post(new Runnable(){
            public void run(){
                Toast.makeText(context, "Service is off!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void showServiceLongToast(final Context context, String message) {
        Handler handler=new Handler(Looper.getMainLooper());
        handler.post(new Runnable(){
            public void run(){
                Toast.makeText(context, "Service is off!", Toast.LENGTH_LONG).show();
            }
        });
    }

}
