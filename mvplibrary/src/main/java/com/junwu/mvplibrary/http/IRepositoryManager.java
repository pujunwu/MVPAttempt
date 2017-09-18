package com.junwu.mvplibrary.http;

/**
 * ===============================
 * 描    述：数据请求管理接口
 * 作    者：pjw
 * 创建日期：2017/7/3 11:26
 * ===============================
 */
public interface IRepositoryManager {

    /**
     * 注入RetrofitService,在{@link com.junwu.mvplibrary.config.IRegisterApiModule#registerComponents(IRepositoryManager)}中进行注入
     */
    void injectRetrofitService(Class<?>... services);

    /**
     * 注入CacheService,在{@link com.junwu.mvplibrary.config.IRegisterApiModule#registerComponents(IRepositoryManager)}中进行注入
     */
    void injectCacheService(Class<?>... services);

    /**
     * 根据传入的Class获取对应的Retrofit service
     */
    <T> T obtainRetrofitService(Class<T> service);

    /**
     * 根据传入的Class获取对应的RxCache service
     */
    <T> T obtainCacheService(Class<T> cache);

    /**
     * 销毁方法
     */
    void onTerminate();

}
