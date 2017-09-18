package com.junwu.mvplibrary.mvp.model;

import com.junwu.mvplibrary.http.IRepositoryManager;

import javax.inject.Inject;

/**
 * ===============================
 * 描    述：model层的基类
 * 1、网络数据请求处理
 * 2、其他数据处理
 * 作    者：pjw
 * 创建日期：2017/6/29 16:16
 * ===============================
 */
public class LibBaseModel implements IModel {

    protected IRepositoryManager mRepositoryManager;

    /**
     * 提供网络、本地等数据
     *
     * @param manager {@link IRepositoryManager}
     */
    public LibBaseModel(IRepositoryManager manager) {
        this.mRepositoryManager = manager;
    }

    /**
     * 获取接口代理类
     *
     * @param c   接口
     * @param <T> 接口代理类
     * @return 接口代理类
     */
    protected <T> T getRetrofitApiService(Class<T> c) {
        return mRepositoryManager
                .obtainRetrofitService(c);
    }

    /**
     * 获取接口代理类
     *
     * @param c   接口
     * @param <T> 接口代理类
     * @return 接口代理类
     */
    protected <T> T getRxCacheApiService(Class<T> c) {
        return mRepositoryManager
                .obtainCacheService(c);
    }

    @Override
    public void onDestroy() {
        mRepositoryManager = null;
    }
}
