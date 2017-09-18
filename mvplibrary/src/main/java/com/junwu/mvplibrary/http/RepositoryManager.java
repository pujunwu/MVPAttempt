package com.junwu.mvplibrary.http;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.rx_cache2.internal.RxCache;
import retrofit2.Retrofit;

/**
 * 用来管理网络请求层,以及数据缓存层,以后可能添加数据库请求层
 * 需要在{@link com.junwu.mvplibrary.config.IConfigModule}的实现类中先inject需要的服务
 */
@Singleton
public class RepositoryManager implements IRepositoryManager {

    private Retrofit mRetrofit;
    private RxCache mRxCache;
    private Map<String, Object> mRetrofitServiceCache = new LinkedHashMap<>();
    private Map<String, Object> mCacheServiceCache = new LinkedHashMap<>();

    @Inject
    public RepositoryManager(Retrofit retrofit, RxCache rxCache) {
        this.mRetrofit = retrofit;
        this.mRxCache = rxCache;
    }

    /**
     * 注入RetrofitService,在{@link com.junwu.mvplibrary.config.IRegisterApiModule#registerComponents(IRepositoryManager)}中进行注入
     *
     * @param services Retrofit Api接口
     */
    @Override
    public void injectRetrofitService(Class<?>... services) {
        if (services == null)
            return;
        for (Class<?> service : services) {
            if (service == null)
                continue;
            if (mRetrofitServiceCache.containsKey(service.getName())) continue;
            mRetrofitServiceCache.put(service.getName(), mRetrofit.create(service));
        }
    }

    /**
     * 注入CacheService,在{@link com.junwu.mvplibrary.config.IRegisterApiModule#registerComponents(IRepositoryManager)}中进行注入
     *
     * @param services RXCache Api接口
     */
    @Override
    public void injectCacheService(Class<?>... services) {
        if (services == null)
            return;
        for (Class<?> service : services) {
            if (service == null)
                continue;
            if (mCacheServiceCache.containsKey(service.getName())) continue;
            mCacheServiceCache.put(service.getName(), mRxCache.using(service));
        }
    }

    /**
     * 根据传入的Class获取对应的Retrift service
     *
     * @param service 获取 Retrofit接口对象
     * @param <T>     泛型
     * @return 返回泛型
     */
    @Override
    public <T> T obtainRetrofitService(Class<T> service) {
        if (!mRetrofitServiceCache.containsKey(service.getName())) {
            throw new RuntimeException(String.format(Locale.CANADA, "Unable to find %s,first call injectRetrofitService(%s) in IConfigModule", service.getName(), service.getSimpleName()));
        }
        return (T) mRetrofitServiceCache.get(service.getName());
    }

    /**
     * 根据传入的Class获取对应的RxCache service
     *
     * @param cache 获取 RxCache接口对象
     * @param <T>   泛型
     * @return 返回泛型
     */
    @Override
    public <T> T obtainCacheService(Class<T> cache) {
        if (!mCacheServiceCache.containsKey(cache.getName())) {
            throw new RuntimeException(String.format(Locale.CANADA, "Unable to find %s,first call injectCacheService(%s) in IConfigModule", cache.getName(), cache.getSimpleName()));
        }
        return (T) mCacheServiceCache.get(cache.getName());
    }

    @Override
    public void onTerminate() {
        mRetrofitServiceCache.clear();
        mCacheServiceCache.clear();
        mRetrofitServiceCache = null;
        mCacheServiceCache = null;
    }


}
