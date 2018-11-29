package com.hch.myutils.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author hechuang
 * @ClassName: FileUtil.java
 * @Description: TODO 文件工具类
 * @date 2018/11/12 14:39
 */
public class FileUtil {

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
}
