package com.junwu.ktmvplibrary.config

import com.junwu.ktmvplibrary.http.IRepositoryManager

/**
 * ===============================
 * 描    述：注册ApiModule
 * 作    者：pjw
 * 创建日期：2017/9/28 10:43
 * ===============================
 */
interface IRegisterApiModule {

    /**
     * 注册所有api请求接口 调用方法[IRepositoryManager.injectCacheService]RxCache注册缓存接口
     * 调用方法[IRepositoryManager.injectRetrofitService]Retrofit注册接口
     *
     * @param repositoryManager 数据获取管理对象
     */
    fun registerComponents(repositoryManager: IRepositoryManager)

}