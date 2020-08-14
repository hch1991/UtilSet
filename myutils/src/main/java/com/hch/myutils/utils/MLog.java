package com.hch.myutils.utils;

import android.util.Log;


/**
 * @ClassName: MLog
 * @Description: TODO log工具.
 * @author hechuang
 * @date 2018/9/18 14:31
 *
 */
public class MLog
{

    static String className;//类名
    static String methodName;//方法名
    static int lineNumber;//行数

    /**  日志log. */
    private static final String TAG = "TAG";
    /** 是否打印log. */
    private static final boolean DEBUG = true;

    //======================================= log.d =========================================//
    public static void d(String tag, String debug)
    {
        if (DEBUG && debug != null)
        {
            printLog(1,tag, createLog(debug));
        }
    }
    public static void d(String tag, String debug, Throwable e)
    {
        if (DEBUG)
        {
            printLog(1,tag, createLog(debug), e);
        }
    }
    public static void d(String msg)
    {
        if (DEBUG)
        {
            printLog(1, createLog(msg));
        }
    }

    //======================================= log.i =========================================//
    public static void i(String tag, String info)
    {
        if (DEBUG)
        {
            printLog(2,tag, info);
        }
    }
    public static void i(String tag, String debug, Throwable e)
    {
        if (DEBUG)
        {
            printLog(2,tag, debug, e);
        }
    }
    public static void i(String msg)
    {
        if (DEBUG)
        {
            printLog(2, msg);
        }
    }

    //======================================= log.w =========================================//
    public static void w(String tag, String info)
    {
        if (DEBUG)
        {
            printLog(3,tag, info);
        }
    }
    public static void w(String tag, String debug, Throwable e)
    {
        if (DEBUG)
        {
            printLog(3,tag, debug, e);
        }
    }
    public static void w(String msg)
    {
        if (DEBUG)
        {
            printLog(3, msg);
        }
    }
    //======================================= log.e =========================================//
    public static void e(String tag, String err)
    {
        if (DEBUG)
        {
            printLog(4,tag, createLog(err));
        }
    }
    public static void e(String tag, String debug, Throwable e)
    {
        if (DEBUG)
        {
            printLog(4,tag, createLog(debug),e);
        }
    }
    public static void e(String msg)
    {
        if (DEBUG)
        {
            printLog(4, createLog(msg));
        }
    }

    // ===================================== log.exception =======================================//
    public static void logException(Throwable e)
    {
        if (DEBUG)
        {
            e.printStackTrace();
        }
    }

    private static void printLog(int type,String msg){
        getMethodNames(new Throwable().getStackTrace());
        switch (type){
            case 1:
                Log.d(TAG, createLog(msg));
                break;
            case 2:
                Log.i(TAG, createLog(msg));
                break;
            case 3:
                Log.w(TAG, createLog(msg));
                break;
            case 4:
                Log.e(TAG, createLog(msg));
                break;
        }

    }
    private static void printLog(int type,String tag,String msg){
        getMethodNames(new Throwable().getStackTrace());
        switch (type){
            case 1:
                Log.d(tag, createLog(msg));
                break;
            case 2:
                Log.i(tag, createLog(msg));
                break;
            case 3:
                Log.w(tag, createLog(msg));
                break;
            case 4:
                Log.e(tag, createLog(msg));
                break;
        }
    }
    private static void printLog(int type,String tag,String msg,Throwable e){
        getMethodNames(new Throwable().getStackTrace());
        switch (type){
            case 1:
                Log.d(tag, createLog(msg),e);
                break;
            case 2:
                Log.i(tag, createLog(msg),e);
                break;
            case 3:
                Log.w(tag, createLog(msg),e);
                break;
            case 4:
                Log.e(tag, createLog(msg),e);
                break;
        }
    }

    private static String createLog(String log ) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(methodName);
        buffer.append("(").append(className).append(":").append(lineNumber).append(")");
        buffer.append(log);
        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements){
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }
}
