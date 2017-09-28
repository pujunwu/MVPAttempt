package com.junwu.ktmvplibrary.mvp.model

import com.junwu.ktmvplibrary.http.IRepositoryManager

/**
 * ===============================
 * 描    述：model层的基类
 * 1、网络数据请求处理
 * 2、其他数据处理
 * 作    者：pjw
 * 创建日期：2017/6/29 16:16
 * ===============================
 */
class LibBaseModel(manager: IRepositoryManager?) : IModel {

    /**
     * 提供网络、本地等数据[IRepositoryManager]
     */
    protected var mRepositoryManager = manager

    /**
     * 获取接口代理类
     *
     * @param c   接口
     * @param <T> 接口代理类
     * @return 接口代理类
    </T> */
    protected fun <T> getRetrofitApiService(c: Class<T>): T {
        return mRepositoryManager!!
                .obtainRetrofitService(c)
    }

    /**
     * 获取接口代理类
     *
     * @param c   接口
     * @param <T> 接口代理类
     * @return 接口代理类
    </T> */
    protected fun <T> getRxCacheApiService(c: Class<T>): T {
        return mRepositoryManager?.obtainCacheService(c) as T
    }

    override fun onDestroy() {
        mRepositoryManager = null
    }

}
