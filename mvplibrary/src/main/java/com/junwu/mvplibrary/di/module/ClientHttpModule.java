package com.junwu.mvplibrary.di.module;

import com.junwu.mvplibrary.di.entitys.RxCacheBuilderEntity;
import com.junwu.mvplibrary.http.IRepositoryManager;
import com.junwu.mvplibrary.http.RepositoryManager;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ===============================
 * 描    述：提供网络请求相关的对象
 * 作    者：pjw
 * 创建日期：2017/6/29 19:26
 * ===============================
 */
@Module
public class ClientHttpModule {

    //请求超时时间
    private static final int TIME_OUT = 30;

    /****************************************OKHttpClient***************************************/
    @Singleton
    @Provides
    OkHttpClient providerOkHttpClient(OkHttpClient.Builder builder, ConfigModule.OkHttpConfiguration configuration) {
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS);
        configuration.configOkhttp(builder);
        return builder.build();
    }

    @Singleton
    @Provides
    OkHttpClient.Builder providerOkHttpBuilder() {
        return new OkHttpClient.Builder();
    }

    /****************************************Retrofit***************************************/
    @Singleton
    @Provides
    Retrofit providerRetrofit(Retrofit.Builder builder, OkHttpClient okHttpClient, HttpUrl httpUrl, ConfigModule.RetrofitConfiguration configuration) {
        builder.baseUrl(httpUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用rxjava
                .addConverterFactory(GsonConverterFactory.create());//使用Gson
        configuration.configRetrofit(builder);
        return builder.build();
    }

    @Singleton
    @Provides
    Retrofit.Builder providerRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    RxCache providerRxCache(RxCache.Builder builder, @Named("RxCacheDirectory") File cacheDirectory, RxCacheBuilderEntity rxCacheBuilder, ConfigModule.RxCacheConfiguration cacheConfiguration) {
        builder.setMaxMBPersistenceCache(100);//最大缓存
        cacheConfiguration.configRxCache(builder);
        rxCacheBuilder.setCacheFolder(cacheDirectory.getAbsolutePath());
        return builder.persistence(cacheDirectory, new GsonSpeaker());//设置缓存目录
    }

    @Singleton
    @Provides
    RxCache.Builder providerRxcacheBuilder() {
        return new RxCache.Builder();
    }

    @Singleton
    @Provides
    RxCacheBuilderEntity providerRxCacheBuilderEntity() {
        return new RxCacheBuilderEntity();
    }

    /****************************************IRepositoryManager***************************************/

    @Singleton
    @Provides
    IRepositoryManager providerRepositoryManager(RepositoryManager manager) {
        return manager;
    }


}
