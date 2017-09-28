package com.junwu.ktmvplibrary.di.module

import com.junwu.ktmvplibrary.http.IRepositoryManager
import com.junwu.ktmvplibrary.http.RepositoryManager
import com.junwu.ktmvplibrary.tests.HomeModule
import dagger.Module
import dagger.Provides
import io.rx_cache2.internal.RxCache
import io.victoralbertos.jolyglot.GsonSpeaker
import junwu.com.mvplibrary.di.entitys.RxCacheBuilderEntity
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * ===============================
 * 描    述：
 * 作    者：pjw
 * 创建日期：2017/9/25 15:27
 * ===============================
 */
@Module(includes = arrayOf(ConfigModule::class))
class ClientHttpModule {

    //请求超时时间
    private val TIME_OUT = 30L

    /****************************************OKHttpClient **********************************/

    @Singleton
    @Provides
    fun providerOkHttpBuilder() = OkHttpClient.Builder()

    @Singleton
    @Provides
    fun providerOkHttpClient(builder: OkHttpClient.Builder, configuration: ConfigModule.OkHttpConfiguration): OkHttpClient {
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        configuration.configOkhttp(builder)
        return builder.build()
    }

    /****************************************Retrofit **********************************/

    @Singleton
    @Provides
    fun providerRetrofitBuilder() = Retrofit.Builder()

    @Singleton
    @Provides
    fun providerRetrofit(builder: Retrofit.Builder, okHttpClient: OkHttpClient, httpUrl: HttpUrl, configuration: ConfigModule.RetrofitConfiguration): Retrofit {
        builder.baseUrl(httpUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用rxjava
                .addConverterFactory(GsonConverterFactory.create())//使用Gson
        configuration.configRetrofit(builder)
        return builder.build()
    }

    /****************************************RxCache **********************************/

    @Singleton
    @Provides
    fun providerRxCache(builder: RxCache.Builder, /*@Named("RxCacheDirectory") */cacheDirectory: File, rxCacheBuilder: RxCacheBuilderEntity, cacheConfiguration: ConfigModule.RxCacheConfiguration): RxCache {
        builder.setMaxMBPersistenceCache(100)//最大缓存
        cacheConfiguration.configRxCache(builder)
        rxCacheBuilder.cacheFolder = cacheDirectory.absolutePath
        return builder.persistence(cacheDirectory, GsonSpeaker())//设置缓存目录
    }

    @Singleton
    @Provides
    fun providerRxcacheBuilder() = RxCache.Builder()

    @Singleton
    @Provides
    fun providerRxCacheBuilderEntity() = RxCacheBuilderEntity()

    /****************************************IRepositoryManager **********************************/

    @Singleton
    @Provides
    fun providerRepositoryManager(retrofit: Retrofit, rxCache: RxCache): IRepositoryManager {
        return RepositoryManager(retrofit, rxCache)
    }

    @Singleton
    @Provides
    fun providerHomeModule(retrofit: OkHttpClient): HomeModule {
        return HomeModule(retrofit)
    }

}