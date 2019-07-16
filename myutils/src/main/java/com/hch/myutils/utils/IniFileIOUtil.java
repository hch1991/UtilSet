package com.hch.myutils.utils;

import android.widget.Advanceable;

import org.dtools.ini.BasicIniFile;
import org.dtools.ini.IniFile;
import org.dtools.ini.IniFileReader;
import org.dtools.ini.IniFileWriter;
import org.dtools.ini.IniItem;
import org.dtools.ini.IniSection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hechuang
 * @Description: TODO ini文件读写类
 * @create 2019/7/16  10:23
 */
public class IniFileIOUtil {

    /**
     * 从ini配置文档中读取变量的值
     *
     * @param filepath         配置文档的路径
     * @param group      要获取的变量所在段名称
     * @param key     要获取的变量名称
     * @param default_value 变量名称不存在时的默认值
     * @return 变量的值
     * @throws IOException 抛出文档操作可能出现的io异常
     */
    public static String ini_read(String group, String key, String default_value, String filepath)
    {
        IniFile iniFile=new BasicIniFile();
        File file=new File(filepath);
        IniFileReader rad=new IniFileReader(iniFile, file);
        try {
            //读取item
            rad.read();
            IniSection iniSection=iniFile.getSection(group);
            if(iniSection==null)
                return default_value;
            IniItem iniItem=iniSection.getItem(key);
            if (iniItem==null) {
                return default_value;
            }
            return iniItem.getValue();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 修改ini配置文档中变量的值  保存ini文件，group分组必须存在，不能为空
     *
     * @param filepath     配置文档的路径
     * @param group  要修改的变量所在段名称
     * @param key 要修改的变量名称
     * @param value    变量的新值
     * @throws IOException 抛出文档操作可能出现的io异常
     */
    public static void ini_write(String group, String key, String value, String filepath)
    {
        IniFile iniFile=new BasicIniFile();
        File file=new File(filepath);
        IniFileReader rad=new IniFileReader(iniFile, file);
        IniFileWriter wir=new IniFileWriter(iniFile, file);
        try {
            rad.read();
            IniSection iniSection=iniFile.getSection(group);
            System.out.println(iniSection.getName());
            IniItem iniItem=iniSection.getItem(key);
            if(iniItem==null)
            {
                iniItem=new IniItem(key);
                iniItem.setValue(value);
                iniSection.addItem(iniItem);
            }
            else {
                iniItem.setValue(value);
            }

            wir.write();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
      * @Description: TODO 遍历ini文件
      * @author hechuang
      * @param  filepath 配置文档的路径
      * @return    返回类型
      * @create 2019/7/16
      * @throws
      */
    public static void ini_traverse(String filepath) {
        try {
            IniFile ini = new BasicIniFile(false);//不使用大小写敏感
            IniFileReader reader = new IniFileReader(ini, new File(filepath));
            reader.read();
            for(int i=0;i<ini.getNumberOfSections();i++){
                IniSection sec = ini.getSection(i);
                System.out.println("---- " + sec.getName() + " ----");
                for(IniItem item : sec.getItems()){
                    System.out.println(item.getName() + " = " + item.getValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
