package com.hch.myutils.utils;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import static android.os.Build.VERSION.SDK_INT;

/**
 * @author hechuang
 * @ClassName: FileUtil.java
 * @Description: TODO 文件工具类
 * @date 2018/11/12 14:39
 */
public class FileUtil {

    private static final int SUCCESS = 1;
    private static final int FAILED = 0;
    private static FileOperateCallback callback;
    private static volatile boolean isSuccess;
    private static String errorStr;

    /**
     * @param :
     * @return :
     * created at 2018/11/13 10:27
     * @Description: TODO 创建指定文件
     * @author : hechuang
     */

    public static boolean createFile(File fileName) {
        if (!fileName.exists()) {
            try {
                fileName.createNewFile();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
    /**
     * @Description: TODO 根据路径创建文件夹
     * @author : hechuang
     * @param : 
     * @return : 
     * created at 2018/11/28 11:50
     */
    
    public static boolean createDir(String dirPath) {
        File file = new File(dirPath);
        try {
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @param :
     * @return :
     * created at 2018/11/13 10:26
     * @Description: TODO 读文件
     * @author : hechuang
     */

    public static String readTxtFile(File fileName) throws IOException {
        String result = "";
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        fileReader = new FileReader(fileName);
        bufferedReader = new BufferedReader(fileReader);

        String read = null;
        int count = 0;
        while ((read = bufferedReader.readLine()) != null) {
            result = result + read + "\r\n";
            count++;
        }

        if (bufferedReader != null) {
            bufferedReader.close();
        }

        if (fileReader != null) {
            fileReader.close();
        }

        return result;
    }

    /**
     * @param : content 写入的内容
     * @param : fileName 文件位置
     * @return :
     * created at 2018/11/13 10:26
     * @Description: TODO 写文件
     * @author : hechuang
     */

    public static boolean writeTxtFile(String content, File fileName) throws UnsupportedEncodingException, IOException {
        FileOutputStream o = null;
        o = new FileOutputStream(fileName);
        o.write(content.getBytes("UTF-8"));
        o.close();
        return true;
    }

    /**
     * @Description: TODO 根据路径删除文件或文件夹
     * @param :文件路径
     * @return :
     * created at 2019/1/11
     * @author : hechuang
     */
    public static boolean deldeteFileByPath(String filePath){
        File file = new File(filePath);
        if (!file.exists()) {
            MLog.d("删除文件失败:" + filePath + "不存在！");
            return false;
        } else {
            if (file.isFile())
                return deleteSingleFile(filePath);
            else
                return deleteDirectory(filePath);
        }
    }

    /**
     * @Description: TODO 删除单个文件
     * @param : 文件路径+文件名
     * @return :
     * created at 2019/1/11
     * @author : hechuang
     */
    private static boolean deleteSingleFile(String filePath$Name) {
        File file = new File(filePath$Name);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                MLog.d("deleteSingleFile: 删除单个文件" + filePath$Name + "成功！");
                return true;
            } else {
                MLog.d("删除单个文件" + filePath$Name + "失败！");
                return false;
            }
        } else {
            MLog.d( "删除单个文件失败：" + filePath$Name + "不存在！");
            return false;
        }
    }
    /**
     * @Description: TODO  删除指定文件夹
     * @param :文件夹路径
     * @return :
     * created at 2019/1/11
     * @author : hechuang
     */
    private static boolean deleteDirectory(String filePath) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator))
            filePath = filePath + File.separator;
        File dirFile = new File(filePath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            MLog.d( "删除目录失败：" + filePath + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (File file : files) {
            // 删除子文件
            if (file.isFile()) {
                flag = deleteSingleFile(file.getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (file.isDirectory()) {
                flag = deleteDirectory(file
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            MLog.d( "删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            MLog.d( "deleteDirectory: 删除目录" + filePath + "成功！");
            return true;
        } else {
            MLog.d( "删除目录：" + filePath + "失败！");
            return false;
        }
    }

    /**
     * 复制单个文件
     *
     * @param oldPath$Name String 原文件路径+文件名 如：data/user/0/com.test/files/abc.txt
     * @param newPath$Name String 复制后路径+文件名 如：data/user/0/com.test/cache/abc.txt
     * @return <code>true</code> if and only if the file was copied;
     * <code>false</code> otherwise
     */
    public static boolean copyFile(String oldPath$Name, String newPath$Name) {
        try {
            File oldFile = new File(oldPath$Name);
            File newFile = new File(newPath$Name);
        if (!oldFile.exists() || !oldFile.isFile() || !oldFile.canRead()) {
            return false;
        }
        if(newFile.exists()){
            newFile.delete();
        }
            FileInputStream fileInputStream = new FileInputStream(oldPath$Name);    //读入原文件
            FileOutputStream fileOutputStream = new FileOutputStream(newPath$Name);
            byte[] buffer = new byte[1024];
            int byteRead;
            while ((byteRead = fileInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, byteRead);
            }
            fileInputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 复制文件夹及其中的文件
     *
     * @param oldPath String 原文件夹路径 如：data/user/0/com.test/files
     * @param newPath String 复制后的路径 如：data/user/0/com.test/cache
     * @return <code>true</code> if and only if the directory and files were copied;
     * <code>false</code> otherwise
     */
    public static boolean copyFolder(String oldPath, String newPath) {
        try {
            File newFile = new File(newPath);
            if (!newFile.exists()) {
                if (!newFile.mkdirs()) {
                    MLog.d( "copyFolder: cannot create directory.");
                    return false;
                }
            }
            File oldFile = new File(oldPath);
            String[] files = oldFile.list();
            File temp;
            for (String file : files) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file);
                } else {
                    temp = new File(oldPath + File.separator + file);
                }

                if (temp.isDirectory()) {   //如果是子文件夹
                    copyFolder(oldPath + "/" + file, newPath + "/" + file);
                } else if (!temp.exists()) {
                    MLog.d( "copyFolder:  oldFile not exist.");
                    return false;
                } else if (!temp.isFile()) {
                    MLog.d( "copyFolder:  oldFile not file.");
                    return false;
                } else if (!temp.canRead()) {
                    MLog.d( "copyFolder:  oldFile cannot read.");
                    return false;
                } else {
                    FileInputStream fileInputStream = new FileInputStream(temp);
                    FileOutputStream fileOutputStream = new FileOutputStream(newPath + "/" + temp.getName());
                    byte[] buffer = new byte[1024];
                    int byteRead;
                    while ((byteRead = fileInputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, byteRead);
                    }
                    fileInputStream.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (callback != null) {
                if (msg.what == SUCCESS) {
                    callback.onSuccess();
                }
                if (msg.what == FAILED) {
                    callback.onFailed(msg.obj.toString());
                }
            }
        }
    };
    /**
      * @Description: TODO 复制assets文件到sd卡
      * @author hechuang
      * @param
      * @return    返回类型
      * @create 2019/7/30
      * @throws
      */
    public static void copyAssetsToSD(final Context context, final String srcPath, final String sdPath, final FileOperateCallback fileOperateCallback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                copyAssetsToDst(context, srcPath, sdPath);
                if (isSuccess)
                    handler.obtainMessage(SUCCESS).sendToTarget();
                else
                    handler.obtainMessage(FAILED, errorStr).sendToTarget();
                if(fileOperateCallback != null){
                    callback = fileOperateCallback;
                }
            }
        }).start();
    }

    /**
      * @Description: TODO 开始复制文件
      * @author hechuang
      * @param
      * @return    返回类型
      * @create 2019/7/30
      * @throws
      */
    private static void copyAssetsToDst(Context context, String srcPath, String dstPath) {
        try {
            String fileNames[] = context.getAssets().list(srcPath);
            if (fileNames.length > 0) {
                File file = new File(Environment.getExternalStorageDirectory(), dstPath);
                if (!file.exists()) file.mkdirs();
                for (String fileName : fileNames) {
                    if (!srcPath.equals("")) { // assets 文件夹下的目录
                        copyAssetsToDst(context, srcPath + File.separator + fileName, dstPath + File.separator + fileName);
                    } else { // assets 文件夹
                        copyAssetsToDst(context, fileName, dstPath + File.separator + fileName);
                    }
                }
            } else {
                File outFile = new File(Environment.getExternalStorageDirectory(), dstPath);
                InputStream is = context.getAssets().open(srcPath);
                FileOutputStream fos = new FileOutputStream(outFile);
                byte[] buffer = new byte[1024];
                int byteCount;
                while ((byteCount = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteCount);
                }
                fos.flush();
                is.close();
                fos.close();
            }
            isSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
            errorStr = e.getMessage();
            isSuccess = false;
        }
    }
    /**
      * @Description: TODO 成功与失败的回调
      * @author hechuang
      * @param
      * @return    返回类型
      * @create 2019/7/30
      * @throws
      */
    public interface FileOperateCallback {
        void onSuccess();

        void onFailed(String error);
    }
    /**
      * @Description: TODO 刷新系统媒体库
      * @author hechuang
      * @param  path 文件路径， ①目前没有找到刷新多张图片方式，只有弄一张调一次  ②如果要刷新整个存储空间的媒体库，则在path参数处传入Environment.getExternalStorageDirectory().toString();
      * @return    返回类型
      * @create 2019/8/16
      * @throws
      */
    public static void updateMedia(final Context context, String path){

        if(SDK_INT >= Build.VERSION_CODES.KITKAT){//当大于等于Android 4.4时
            MediaScannerConnection.scanFile(context, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                @Override
                public void onScanCompleted(String path, Uri uri) {
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    mediaScanIntent.setData(uri);
                    context.sendBroadcast(mediaScanIntent);
                }
            });

        }else{//Andrtoid4.4以下版本
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,Uri.fromFile((new File(path).getParentFile()))));
        }

    }

}
