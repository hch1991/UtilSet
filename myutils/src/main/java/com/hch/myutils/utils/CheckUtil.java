package com.hch.myutils.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.hch.myutils.interfaces.PermissionsCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
/**
 * @ClassName: CheckUtil
 * @Description: TODO 检查工具类
 * @author hechuang
 * @date 2018/9/18 14:29
 *
 */
public class CheckUtil {

    //检查string是否是json格式
    public static boolean isGoodJson(String json) {
        if (json.equals("") && json.length() >= 0) {
            return false;
        }
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonParseException e) {
            return false;
        }
    }

    //根据包名检查apk是否安装
    public static boolean checkAppInstallByPackageNames(Context mContext, String packageName) {
        PackageManager pm = mContext.getPackageManager(); //获取本地的所有APP包名
        List<PackageInfo> packList = pm.getInstalledPackages(0);
        // 循环
        for (int i = 0; i < packList.size(); i++) {
            // 比对
            if (((PackageInfo) packList.get(i)).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }

    /**
     *  检查文件是否存在
     * @ClassName SDCardUtil
     *@author HeChuang
     *@time 2016/11/21 14:17
     */
    public static boolean fileIsExists(String filePath, String fileName){
        File fp = new File(filePath);
        if(!fp.exists()){
            fp.mkdir();
        }
        File fn = new File(filePath+fileName);
        if (fp.exists()) {
            return true;
        }
        return false;
    }

    /**
     * 检查申请单个权限
     *
     * @author hechuang
     * created at 2018/5/16 10:18
     */
    public static void verifyOnePermission(Activity activity, String checkPermission, int permissionsRequest, PermissionsCallback permissionsCallback) {
        // 检查是否有指定权限
        int permission = ActivityCompat.checkSelfPermission(activity, checkPermission);
        //此权限是不是没有授权  PackageManager.PERMISSION_GRANTED表示已授权
        if (permission != PackageManager.PERMISSION_GRANTED) {
            MLog.d("没有权限");
            // We don't have permission so prompt the user
            //没有权限，像用户申请授权
            ActivityCompat.requestPermissions(activity, new String[]{checkPermission},
                    permissionsRequest);
            permissionsCallback.hasPermissions(false);
        } else {
            MLog.d("已有权限");
            permissionsCallback.hasPermissions(true);
        }

    }

    /**
     * 检查申请多个权限
     *
     * @author : hechuang
     * created at 2018/5/16 10:38
     */
    public static void verifyMorePermission(Activity activity, String[] checkPermission, int permissionsRequest, PermissionsCallback permissionsCallback){
        List<String> mPermissionList = new ArrayList<>();
        for (int i = 0; i < checkPermission.length; i++) {
            if (ActivityCompat.checkSelfPermission(activity, checkPermission[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(checkPermission[i]);
            }
        }
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
            MLog.d("已经授权");
            permissionsCallback.hasPermissions(true);
        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(activity, permissions, permissionsRequest);
            permissionsCallback.hasPermissions(false);
        }
    }


}
