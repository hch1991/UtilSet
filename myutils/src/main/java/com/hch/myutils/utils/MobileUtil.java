package com.hch.myutils.utils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

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

    /**
     * @Description: TODO 获取cpu核心数
     * @author : hechuang
     * @param : 
     * @return : 
     * created at 2018/10/24 15:33
     */
    private int getNumCores(){
        // Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter
        {
            @Override
            public boolean accept(File pathname)
            {
                // Check if filename is "cpu", followed by a single digit number
                if (Pattern.matches("cpu[0-9]", pathname.getName()))
                {
                    return true;
                }
                return false;
            }
        }

        try
        {
            // Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            // Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            // Return the number of cores (virtual CPU devices)
            return files.length;
        } catch (Exception e)
        {
            // Default to return 1 core
            return 1;
        }
    }

    /**
     * @Description: TODO 获取手机序列号
     * @author : hechuang
     * @param :
     * @return :
     * created at 2018/10/24 15:53
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
     * @Description: TODO 获取手机mac地址
     * @author : hechuang
     * @param : 
     * @return : 
     * created at 2018/10/24 15:54
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
     * @Description: TODO 获取手机wifi地址
     * @author : hechuang
     * @param : 
     * @return : 
     * created at 2018/10/24 15:54
     */
    private static String getWifiAddress() {
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
     * @Description: TODO 获取手机sdk版本
     * @author : hechuang
     * @param : 
     * @return : 
     * created at 2018/10/24 15:56
     */
    public static int getAndroidSDKVersion(){
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * @Description: TODO 获取当前应用包名
     * @author : hechuang
     * @param : 
     * @return : 
     * created at 2018/10/27 17:51
     */
    
    public static String getPackageName(Context context){
        return context.getPackageName();
    }

    /**
     * @Description: TODO 获取当前应用版本号
     * @author : hechuang
     * @param :
     * @return :
     * created at 2018/10/27 17:52
     */
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }
    /**
     * @Description: TODO 获取当前应用版本名
     * @author : hechuang
     * @param :
     * @return :
     * created at 2018/10/27 17:52
     */
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }
    /**
     * @Description: TODO 获取当前应用包信息
     * @author : hechuang
     * @param :
     * @return :
     * created at 2018/10/27 17:52
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
}
