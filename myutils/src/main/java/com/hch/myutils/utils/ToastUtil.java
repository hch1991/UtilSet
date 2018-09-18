package com.hch.myutils.utils;

import android.content.Context;
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
}
