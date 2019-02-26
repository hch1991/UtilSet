package com.hch.myutils.customView;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;

import com.hch.myutils.interfaces.PrinterTextviewListener;

import java.util.Timer;
import java.util.TimerTask;

/**
  * @Description: TODO 打字机效果
  * @author hechuang
  * @param
  * @return    返回类型
  * @create 2019/2/25
  * @throws
  */
public class PrinterTextView extends AppCompatTextView {
    /**
     * TAG
     */
    private static final String TAG = "PrinterTextView";
    /**
     * 默认打字字符
     */
    private final String DEFAULT_INTERVAL_CHAR = "_";
    /**
     * 默认打字间隔时间
     */
    private final int DEFAULT_TIME_DELAY = 50;
    /**
     * 计时器
     */
    private Timer mTimer;
    /**
     * 需要打字的文字
     */
    private String mPrintStr;
    /**
     * 间隔时间
     */
    private int intervalTime = DEFAULT_TIME_DELAY;
    /**
     * 间隔时间
     */
    private String intervalChar = DEFAULT_INTERVAL_CHAR;
    /**
     * 打字进度
     */
    private int printProgress = 0;
    /**
     * 打字进度
     */
    private PrinterTextviewListener mPrinterTextviewListener;


    public PrinterTextView(Context context) {
        super(context);
    }

    public PrinterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PrinterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 设置要打字的文字
     *
     * @param str
     */
    public void setPrintText(String str) {
        setPrintText(str, DEFAULT_TIME_DELAY);
    }

    /**
     * 设置需要打字的文字及打字间隔
     *
     * @param str  打字文字
     * @param time 打字间隔(ms)
     */
    public void setPrintText(String str, int time) {
        setPrintText(str, time, DEFAULT_INTERVAL_CHAR);
    }

    /**
     * 设置需要打字的文字,打字间隔,间隔符号
     *
     * @param str          打字文字
     * @param time         打字间隔(ms)
     * @param intervalChar 间隔符号("_")
     */
    public void setPrintText(String str, int time, String intervalChar) {
        setPrintText(str,time,intervalChar,null);
    }
    /**
     * 设置需要打字的文字,打字间隔,间隔符号
     *
     * @param str          打字文字
     * @param time         打字间隔(ms)
     * @param intervalChar 间隔符号("_")
     * @param printerTextviewListener 开始结束监听
     */
    public void setPrintText(String str, int time, String intervalChar, PrinterTextviewListener printerTextviewListener) {
        if (strIsEmpty(str) || 0 == time || strIsEmpty(intervalChar)) {
            return;
        }
        this.mPrintStr = str;
        this.intervalTime = time;
        this.intervalChar = intervalChar;
        this.mPrinterTextviewListener = printerTextviewListener;
    }

    /**
     * 开始打字
     */
    public void startPrint() {
        // 判空处理
        if (strIsEmpty(mPrintStr)) {
            if (!strIsEmpty(getText().toString())) {
                this.mPrintStr = getText().toString();
            } else {
                return;
            }
        }
        // 重置相关信息
        setText("");
        stopPrint();
        printProgress = 0;
        mTimer = new Timer();
        mTimer.schedule(new PrinterTimeTask(), intervalTime, intervalTime);
        mPrinterTextviewListener.printerOver(true);
    }

    /**
     * 停止打字
     */
    public void stopPrint() {
        if (null != mTimer) {
            mTimer.cancel();
            mTimer = null;
            mPrinterTextviewListener.printerOver(false);
        }
    }

    /**
     * 判断str是否为空
     *
     * @param str
     * @return
     */
    private boolean strIsEmpty(String str) {
        if (null != str && !"".equals(str)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 打字计时器任务
     */
    class PrinterTimeTask extends TimerTask {

        @Override
        public void run() {
            // 需要刷新页面,必须在UI线程,使用post方法
            post(new Runnable() {
                @Override
                public void run() {
                    // 如果未显示完,继续显示
                    if (printProgress < mPrintStr.length()) {
                        printProgress++;
                        // (printProgress & 1) == 1 等价于printProgress%2!=0
                        Log.d("TAG","printProgress:"+printProgress);
                        setText(mPrintStr.substring(0, printProgress) + ((printProgress & 1) == 1 ? intervalChar : ""));
                    } else {
                        // 如果完成打字,显示完整文字
                        setText(mPrintStr);
                        stopPrint();
                    }
                }
            });
        }
    }
}
