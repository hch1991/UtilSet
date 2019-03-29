package com.hch.myutils.utils;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.BatteryManager;
import android.provider.Settings;
import android.text.format.Formatter;

import com.hch.myutils.interfaces.BatteryChangeListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static android.content.Context.BATTERY_SERVICE;

/**
 * @author hechuang
 * @ClassName: M.java
 * @Description: TODO 手机信息工具类
 * @date 2018/10/24 15:26
 */
public class MobileUtil {

    private static BatteryReceiver receiver;
    private static int currentBattery = 100;
    private static BatteryChangeListener mBatteryChangeListener;

    /**
     * @param :
     * @return :
     * created at 2018/10/24 15:33
     * @Description: TODO 获取cpu核心数
     * @author : hechuang
     */
    public static int getNumCores() {
        // Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                // Check if filename is "cpu", followed by a single digit number
                if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
            // Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            // Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            // Return the number of cores (virtual CPU devices)
            return files.length;
        } catch (Exception e) {
            // Default to return 1 core
            return 1;
        }
    }

    /**
     * @param :
     * @return :
     * created at 2018/10/24 15:53
     * @Description: TODO 获取手机序列号
     * @author : hechuang
     */
    public static String getSerialNumber() {
        String serial = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "ro.serialno");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serial;
    }

    /**
     * @param :
     * @return :
     * created at 2019/1/9
     * @Description: TODO 获取本机开发者调试模式开关
     * @author : hechuang
     */
    public static boolean getDebugMode(Context context) {
        return (Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.ADB_ENABLED, 0) > 0);
    }

    /**
     * @param :
     * @return :
     * created at 2018/10/24 15:56
     * @Description: TODO 获取手机sdk版本
     * @author : hechuang
     */
    public static int getAndroidSDKVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * @param :
     * @return :
     * created at 2018/10/27 17:51
     * @Description: TODO 获取当前应用包名
     * @author : hechuang
     */

    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    /**
     * @param :
     * @return :
     * created at 2018/10/27 17:52
     * @Description: TODO 获取当前应用版本号
     * @author : hechuang
     */
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    /**
     * @param :
     * @return :
     * created at 2018/10/27 17:52
     * @Description: TODO 获取当前应用版本名
     * @author : hechuang
     */
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    /**
     * @param :
     * @return :
     * created at 2019/1/3 15:17
     * @Description: TODO 获取固件版本
     * @author : hechuang
     */

    public static String getFrameworkVersion() {
        return android.os.Build.ID;
    }

    /**
     * @param :
     * @return :
     * created at 2018/10/27 17:52
     * @Description: TODO 获取当前应用包信息
     * @author : hechuang
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    /**
     * @param :
     * @return :
     * created at 2018/11/22 17:14
     * @Description: TODO 根据包名打开指定apk
     * @author : hechuang
     */

    public static void doStartApplicationWithPackageName(Context context, String packagename) {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = context.getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            context.startActivity(intent);
        }
    }

    /**
     * @param :
     * @return :
     * created at 2019/1/8
     * @Description: TODO 开启手机电量监听
     * @author : hechuang
     */
    public static void startBatteryReceiver(Context context,BatteryChangeListener batteryChangeListener) {
        mBatteryChangeListener = batteryChangeListener;
        if(receiver == null){
            IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            receiver = new BatteryReceiver();
            context.registerReceiver(receiver, filter);
        }
    }

    /**
     * @param :
     * @return :
     * created at 2019/1/14
     * @Description: TODO 关闭手机电量监听
     * @author : hechuang
     */
    public static void stopBatteryReceiver(Context context) {
        if (receiver != null) {
            context.unregisterReceiver(receiver);
            mBatteryChangeListener = null;
            receiver = null;
        }
    }

    /**
     * @param :
     * @return :
     * created at 2019/1/14
     * @Description: TODO 获取当前电量
     * @author : hechuang
     */
    public static int getCurrentBattery(Context context, BatteryChangeListener batteryChangeListener) {
        if (batteryChangeListener != null) {
            startBatteryReceiver(context,batteryChangeListener);
        }
        BatteryManager batteryManager = (BatteryManager) context.getSystemService(BATTERY_SERVICE);
        currentBattery = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        return currentBattery;
    }

    /**
     * * 获取android当前可用运行内存大小
     * * @param context
     * *
     */
    public static String getAvailMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
// mi.availMem; 当前系统的可用内存
        return Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化
    }


    /**
     * * 获取android总运行内存大小
     * * @param context
     * *
     */
    public static long getTotalMemory(Context context) {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                MLog.d(str2);
                MLog.d(num);
            }
            // 获得系统总内存，单位是KB
            int i = Integer.valueOf(arrayOfString[1]).intValue();
            //int值乘以1024转换为long类型
            initial_memory = new Long((long) i * 1024);
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return initial_memory;
    }
    /**
      * @Description: TODO 获取android总运行内存大小(格式化)
      * @author hechuang
      * @param
      * @return    返回类型
      * @create 2019/3/29
      * @throws
      */
    public static String getTotalMemoryFormat(Context context) {
        return Formatter.formatFileSize(context, getTotalMemory(context));// Byte转换为KB或者MB，内存大小规格化
    }

    /**
     * 获取电池容量 mAh
     * <p>
     * 源头文件:frameworks/base/core/res\res/xml/power_profile.xml
     * <p>
     * Java 反射文件：frameworks\base\core\java\com\android\internal\os\PowerProfile.java
     */
    public static String getBatteryCapacity(Context context) {
        Object mPowerProfile;
        double batteryCapacity = 0;
        final String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";
        try {
            mPowerProfile = Class.forName(POWER_PROFILE_CLASS).getConstructor(Context.class).newInstance(context);
            batteryCapacity = (double) Class.forName(POWER_PROFILE_CLASS).getMethod("getBatteryCapacity").invoke(mPowerProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(batteryCapacity + " mAh");
    }

    /**
      * @Description: TODO 获取手机型号
      * @author hechuang
      * @param
      * @return    返回类型
      * @create 2019/3/12
      * @throws
      */
    public static String[] getCpuInfo() {
        String str1 = "/proc/cpuinfo";
        String str2="";
        String[] cpuInfo={"",""};
        String[] arrayOfString;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (int i = 2; i < arrayOfString.length; i++) {
                cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
            }
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            cpuInfo[1] += arrayOfString[2];
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return cpuInfo;
    }

    static class BatteryReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int current = intent.getExtras().getInt("level");// 获得当前电量
            int total = intent.getExtras().getInt("scale");// 获得总电量
            currentBattery = current * 100 / total;
            mBatteryChangeListener.BatteryChange(currentBattery);
        }
    }
}
