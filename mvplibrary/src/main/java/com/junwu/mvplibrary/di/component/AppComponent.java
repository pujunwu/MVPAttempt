package com.junwu.mvplibrary.di.component;

import android.app.Application;

import com.junwu.mvplibrary.delegate.AppDelegate;
import com.junwu.mvplibrary.di.entitys.RxCacheBuilderEntity;
import com.junwu.mvplibrary.di.module.AppModule;
import com.junwu.mvplibrary.di.module.ClientHttpModule;
import com.junwu.mvplibrary.di.module.ConfigModule;
import com.junwu.mvplibrary.http.IRepositoryManager;

import javax.inject.Singleton;

import dagger.Component;
import io.rx_cache2.internal.RxCache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * ===============================
 * 描    述：Component
 * 作    者：pjw
 * 创建日期：2017/6/30 11:54
 * ===============================
 */
@Singleton
@Component(modules = {AppModule.class, ClientHttpModule.class, ConfigModule.class})
public interface AppComponent {

    /**
     * 注入
     *
     * @param delegate AppDelegate
     */
    void inject(AppDelegate delegate);

    /**
     * 获取当前application对象
     *
     * @return Application
     */
    Application getApplication();

    /**
     * 获取OkHttpClient
     */
    OkHttpClient getOkHttp();

    /**
     * 获取Retrofit
     */
    Retrofit getRetrofit();

    /**
     * 获取RxCache
     */
    RxCache getRxCache();

    /**
     * 获取RxCacheBuild对应的实体
     */
    RxCacheBuilderEntity getRxCacheBuilderEntity();

    /**
     * 获取IRepositoryManager
     */
    IRepositoryManager getIRepositoryManager();

}
