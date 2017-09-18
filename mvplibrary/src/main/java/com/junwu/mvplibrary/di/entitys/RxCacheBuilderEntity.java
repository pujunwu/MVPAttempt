package com.junwu.mvplibrary.di.entitys;

/**
 * ===============================
 * 描    述：rxCache参数
 * 作    者：pjw
 * 创建日期：2017/8/30 14:07
 * ===============================
 */
public class RxCacheBuilderEntity {

    private String cacheFolder;

    public String getCacheFolder() {
        return (cacheFolder != null) ? cacheFolder : "";
    }

    public void setCacheFolder(String cacheFolder) {
        this.cacheFolder = cacheFolder;
    }
}
