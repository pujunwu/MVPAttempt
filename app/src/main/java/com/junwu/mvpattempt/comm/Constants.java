package com.junwu.mvpattempt.comm;

import com.junwu.mvplibrary.utils.PhoneUtils;

/**
 * ===============================
 * 描    述：
 * 作    者：pjw
 * 创建日期：2017/9/15 9:29
 * ===============================
 */
public class Constants {

    //sd卡根目录
    public static final String SDCARDROOTPATH = PhoneUtils.getSdCardRootPath() + "/mvpAttermpt/";
    //rx缓存地址
    public static final String RXCACHEDIRPATH = SDCARDROOTPATH + "rxCache/";
    // 图片缓存地址
    public static final String IMAGECACHEDIRPATH = SDCARDROOTPATH + "imageCache/";

    //图片清晰度分界值 KB
    public static final float HIGHDEFINITIONMINSIZE = 1.5f * 1024 * 1024;
    //图片缓存大小
    public static final int IMAGECACHESIZE = 50 * 1024 * 1024;

}
