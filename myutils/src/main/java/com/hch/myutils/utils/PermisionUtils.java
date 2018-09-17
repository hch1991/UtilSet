package com.hch.myutils.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * 检查权限工具类
 *
 * @author hch
 *
 */

public class PermisionUtils {
    /**
     * 检查申请单个权限
     *
     * @author hechuang
     * created at 2018/5/16 10:18
     */
    public static void verifyOnePermission(Activity activity, String checkPermission, int permissionsRequest) {
        // 检查是否有指定权限
        int permission = ActivityCompat.checkSelfPermission(activity, checkPermission);
        //此权限是不是没有授权  PackageManager.PERMISSION_GRANTED表示已授权
        if (permission != PackageManager.PERMISSION_GRANTED) {
            MLog.d("没有权限");
            // We don't have permission so prompt the user
            //没有权限，像用户申请授权
            ActivityCompat.requestPermissions(activity, new String[]{checkPermission},
                    permissionsRequest);
        } else {
            MLog.d("已有权限");
        }

    }

    /**
     * 检查申请多个权限
     *
     * @author : hechuang
     * created at 2018/5/16 10:38
     */
    public static void verifyMorePermission(Activity activity, String[] checkPermission, int permissionsRequest){
        List<String> mPermissionList = new ArrayList<>();
        for (int i = 0; i < checkPermission.length; i++) {
            if (ActivityCompat.checkSelfPermission(activity, checkPermission[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(checkPermission[i]);
            }
        }
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
            MLog.d("已经授权");
        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(activity, permissions, permissionsRequest);
        }
    }
}
