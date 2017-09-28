package com.junwu.mvpattempt.app;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;
import com.junwu.basicslibrary.utils.PhoneUtils;
import com.junwu.mvpattempt.comm.Constants;

import java.io.File;

/**
 * ===============================
 * 描    述：glide配置，类中的方法应该是在glide第一次加载图片是调用，来初始化配置
 * 作    者：pjw
 * 创建日期：2017/8/28 13:43
 * ===============================
 */
public class GlideConfiguration implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        PackageManager pm = context.getPackageManager();
        //判断是否有SD卡读写权限
        boolean permission = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("android.permission.WRITE_EXTERNAL_STORAGE", "packageName"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !permission) {//6.0以上没有存储权限时的配置
            //设置缓存地址及大小
            builder.setDiskCache(new InternalCacheDiskCacheFactory(context, Constants.IMAGECACHESIZE));
        } else {
            File cacheLocation = new File(Constants.IMAGECACHEDIRPATH);
            if (!cacheLocation.exists()) {
                cacheLocation.mkdirs();
            }
            //设置缓存地址及大小
            builder.setDiskCache(new DiskLruCacheFactory(cacheLocation.getPath(), Constants.IMAGECACHESIZE));
        }
        //根据运行内存情况，自定义对图像的编码格式
        builder.setDecodeFormat((PhoneUtils.getTotalMemory() >= Constants.HIGHDEFINITIONMINSIZE) ? DecodeFormat.PREFER_ARGB_8888 : DecodeFormat.PREFER_RGB_565);
        //根据屏幕分辨率，动态计算图片默认大小
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
        //重新计算内存占用值
        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);
        //内存缓存的最大值
        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        //图片占用内存初始值
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }

}
