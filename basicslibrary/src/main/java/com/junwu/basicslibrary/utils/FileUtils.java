package com.junwu.basicslibrary.utils;

import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

/**
 * ===============================
 * 描    述：
 * 作    者：pjw
 * 创建日期：2017/8/30 11:34
 * ===============================
 */
public class FileUtils {

    //删除文件夹和文件夹里面的文件
    public static void deleteDir(final String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return;
        }
        deleteDir(new File(dirPath));
    }

    /**
     * 删除文件夹里面的所有文件
     *
     * @param dir
     */
    public static void deleteDir(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDir(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    /**
     * 创建文件夹
     */
    public static boolean createFolder(String folderPath) {
        return createFolder(new File(folderPath));
    }

    /**
     * 创建文件夹
     */
    public static boolean createFolder(File dirFirstFolder) {
        if (!dirFirstFolder.exists()) { //如果该文件夹不存在，则进行创建
            return dirFirstFolder.mkdirs();//创建文件夹
        }
        return true;
    }

    /**
     * 创建文件
     */
    public static boolean createFile(String folderPath) {
        return createFile(new File(folderPath));
    }

    /**
     * 创建文件
     */
    public static boolean createFile(File file) {
        if (!file.exists()) {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }


}
