package com.hch.myutils.utils;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.provider.Settings;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author hechuang
 * @ClassName: M.java
 * @Description: TODO 手机信息工具类
 * @date 2018/10/24 15:26
 */
public class MobileUtil {

    private static BatteryReceiver receiver;
    private static int currentBattery;

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
        //  低于24即为7.0以下执行内容
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
     * created at 2018/10/24 15:54
     * @Description: TODO 获取手机mac地址
     * @author : hechuang
     */
    public static String GetLocalMacAddress() {
        try {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            Field field = bluetoothAdapter.getClass().getDeclaredField("mService");
            // 参数值为true，禁用访问控制检查
            field.setAccessible(true);
            Object bluetoothManagerService = field.get(bluetoothAdapter);
            if (bluetoothManagerService == null) {
                return null;
            }
            Method method = bluetoothManagerService.getClass().getMethod("getAddress");
            Object address = method.invoke(bluetoothManagerService);
            if (address != null && address instanceof String) {

                return (String) address;
            } else {
                return null;
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param :
     * @return :
     * created at 2018/10/24 15:54
     * @Description: TODO 获取手机wifi地址
     * @author : hechuang
     */
    public static String getWifiAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

    /**
     * @param :
     * @return :
     * created at 2019/1/3 15:16
     * @Description: TODO 获取本机蓝牙名称
     * @author : hechuang
     */
    public static String getBlueToothName() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return bluetoothAdapter.getName();
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
    public static void startBatteryReceiver(Context context) {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        receiver = new BatteryReceiver();
        context.registerReceiver(receiver, filter);
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
    public static int getCurrentBattery(Context context) {
        if (receiver == null) {
            startBatteryReceiver(context);
        }
        return currentBattery;
    }

    static class BatteryReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int current = intent.getExtras().getInt("level");// 获得当前电量
            int total = intent.getExtras().getInt("scale");// 获得总电量
            currentBattery = current * 100 / total;
        }
    }
}
