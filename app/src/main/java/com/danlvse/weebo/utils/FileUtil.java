package com.danlvse.weebo.utils;


import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import com.danlvse.weebo.data.Weibo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zxy on 16/5/25.
 */
public class FileUtil {

    /**
     * 判断SDCard是否可用
     *
     * @return
     */
    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);

    }

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator;
    }

    /**
     * 获取SD卡的剩余容量 单位byte
     *
     * @return
     */
    public static long getSDCardAllSize() {
        if (isSDCardEnable()) {
            StatFs stat = new StatFs(getSDCardPath());
            // 获取空闲的数据块的数量
            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
            // 获取单个数据块的大小（byte）
            long freeBlocks = stat.getAvailableBlocks();
            return freeBlocks * availableBlocks;
        }
        return 0;
    }

    /**
     * 获取指定路径所在空间的剩余可用容量字节数，单位byte
     *
     * @param filePath
     * @return 容量字节 SDCard可用空间，内部存储可用空间
     */
    public static long getFreeBytes(String filePath) {
        // 如果是sd卡的下的路径，则获取sd卡可用容量
        if (filePath.startsWith(getSDCardPath())) {
            filePath = getSDCardPath();
        } else {// 如果是内部存储的路径，则获取内存存储的可用容量
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        return stat.getBlockSize() * availableBlocks;
    }

    /**
     * 获取系统存储路径
     *
     * @return
     */
    public static String getRootDirectoryPath(Context context) {
        return Environment.getRootDirectory().getAbsolutePath();
    }

    public static void put(Context context, String fileDir, String fileName, String content) {
        File filedir = new File(fileDir);
        File jsonfile = new File(filedir, fileName);
        if (!filedir.exists()) {
            filedir.mkdir();
        }
        try {
            jsonfile.createNewFile();
            if (isSDCardEnable()) {
                FileOutputStream outputstream = new FileOutputStream(jsonfile);
                byte[] buffer = content.getBytes();
                outputstream.write(buffer);
                outputstream.flush();
                outputstream.close();
                //Toast.makeText(context, "文件写入成功", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            //Toast.makeText(context, "文件写入失败", Toast.LENGTH_SHORT).show();
        }
    }
    public static void put(Context context, String fileDir, String fileName, List list) {
        File filedir = new File(fileDir);
        File file = new File(filedir, fileName);
        if (!filedir.exists()) {
            filedir.mkdir();
        }
        try {
            file.createNewFile();
            if (isSDCardEnable()) {
                ObjectOutputStream outputstream = new ObjectOutputStream(new FileOutputStream(file));

                outputstream.writeObject(list);
                outputstream.flush();
                outputstream.close();
                //Toast.makeText(context, "文件写入成功", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            //Toast.makeText(context, "文件写入失败", Toast.LENGTH_SHORT).show();
        }
    }
    public static ArrayList<Weibo> getCache(Context context, String fileDir, String fileName) {

        try {
            File file = new File(fileDir, fileName);
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            ArrayList<Weibo> weibos = (ArrayList<Weibo>) ois.readObject();
            ois.close();
            return weibos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String get(Context context, String fileDir, String fileName) {
        StringBuffer sb = null;
        try {
            File file = new File(fileDir, fileName);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String readline = "";
            sb = new StringBuffer();
            while ((readline = br.readLine()) != null) {
                System.out.println("readline:" + readline);
                sb.append(readline);
            }
            br.close();
            return sb.toString();
            //Toast.makeText(context, fileName + "文件读取成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(context, fileName + "文件读取失败", Toast.LENGTH_SHORT).show();
        }
        return null;
    }


    public static void clear(Context context, String dir, String file) {
        File f = new File(dir,file);
        System.out.println("readline:" );
        try {
            FileOutputStream fos = new FileOutputStream(f);
            fos.write("".getBytes());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
