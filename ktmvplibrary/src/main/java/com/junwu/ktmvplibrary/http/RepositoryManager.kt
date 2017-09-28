package com.junwu.ktmvplibrary.http

import io.rx_cache2.internal.RxCache
import retrofit2.Retrofit
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ===============================
 * 描    述：
 * 作    者：pjw
 * 创建日期：2017/9/28 10:57
 * ===============================
 */
@Singleton
class RepositoryManager @Inject constructor(retrofit: Retrofit, rxCache: RxCache) : IRepositoryManager {

    private val mRetrofit: Retrofit = retrofit
    private val mRxCache: RxCache = rxCache
    private var mRetrofitServiceCache: MutableMap<String, Any> = LinkedHashMap()
    private var mCacheServiceCache: MutableMap<String, Any> = LinkedHashMap()

    /**
     * 注入RetrofitService,在[com.junwu.ktmvplibrary.config.IRegisterApiModule.registerComponents]中进行注入
     *
     * @param services Retrofit Api接口
     */
    override fun injectRetrofitService(vararg services: Class<*>) {
        if (services == null)
            return
        for (service in services) {
            if (service == null)
                continue
            if (mRetrofitServiceCache?.containsKey(service.name)) continue
            mRetrofitServiceCache?.put(service.name, mRetrofit.create(service))
        }
    }

    /**
     * 注入CacheService,在[com.junwu.ktmvplibrary.config.IRegisterApiModule.registerComponents]中进行注入
     *
     * @param services RXCache Api接口
     */
    override fun injectCacheService(vararg services: Class<*>) {
        if (services == null)
            return
        for (service in services) {
            if (service == null)
                continue
            if (mCacheServiceCache!!.containsKey(service.name)) continue
            mCacheServiceCache!!.put(service.name, mRxCache.using(service))
        }
    }

    /**
     * 根据传入的Class获取对应的Retrift service
     *
     * @param service 获取 Retrofit接口对象
     * @param <T>     泛型
     * @return 返回泛型
    </T> */
    override fun <T> obtainRetrofitService(service: Class<T>): T {
        if (!mRetrofitServiceCache.containsKey(service.name)) {
            throw RuntimeException(String.format(Locale.CANADA, "Unable to find %s,first call injectRetrofitService(%s) in IConfigModule", service.name, service.simpleName))
        }
        return mRetrofitServiceCache[service.name] as T
    }

    /**
     * 根据传入的Class获取对应的RxCache service
     *
     * @param cache 获取 RxCache接口对象
     * @param <T>   泛型
     * @return 返回泛型
    </T> */
    override fun <T> obtainCacheService(cache: Class<T>): T {
        if (!mCacheServiceCache?.containsKey(cache.name)) {
            throw RuntimeException(String.format(Locale.CANADA, "Unable to find %s,first call injectCacheService(%s) in IConfigModule", cache.name, cache.simpleName))
        }
        return mCacheServiceCache[cache.name] as T
    }

    override fun onTerminate() {
        mRetrofitServiceCache?.clear()
        mCacheServiceCache?.clear()
    }

}