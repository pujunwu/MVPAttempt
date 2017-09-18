package com.junwu.mvplibrary.config;

import com.junwu.mvplibrary.http.IRepositoryManager;

/**
 * ===============================
 * 描    述：注册ApiModule
 * 作    者：pjw
 * 创建日期：2017/7/4 15:20
 * ===============================
 */
public interface IRegisterApiModule {

    /**
     * 注册所有api请求接口 调用方法{@link IRepositoryManager#injectCacheService(Class[])}RxCache注册缓存接口
     * 调用方法{@link IRepositoryManager#injectRetrofitService(Class[])}Retrofit注册接口
     *
     * @param repositoryManager 数据获取管理对象
     */
    void registerComponents(IRepositoryManager repositoryManager);

}
