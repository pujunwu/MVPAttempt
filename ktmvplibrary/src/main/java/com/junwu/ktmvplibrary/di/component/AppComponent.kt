package com.junwu.ktmvplibrary.di.component

import android.app.Application
import com.junwu.ktmvplibrary.delegate.AppDelegate
import com.junwu.ktmvplibrary.di.module.AppModule
import com.junwu.ktmvplibrary.di.module.ClientHttpModule
import com.junwu.ktmvplibrary.di.module.ConfigModule
import com.junwu.ktmvplibrary.tests.HomeActivity
import dagger.Component
import javax.inject.Singleton

/**
 * ===============================
 * 描    述：
 * 作    者：pjw
 * 创建日期：2017/9/25 15:23
 * ===============================
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, ClientHttpModule::class, ConfigModule::class))
interface AppComponent {

    /**
     * 注入
     *
     * @param delegate AppDelegate
     */
    fun inject(delegate: AppDelegate)

    fun inject(homeActivity: HomeActivity)

    /**
     * 获取当前application对象
     *
     * @return Application
     */
    fun getApplication(): Application

//    /**
//     * 获取OkHttpClient
//     */
//    fun getOkHttpClient(): OkHttpClient
//    /**
//     * 获取Retrofit
//     */
//    fun getRetrofit(): Retrofit
//
//    /**
//     * 获取RxCache
//     */
//    fun getRxCache(): RxCache
//    /**
//     * 获取RxCacheBuild对应的实体
//     */
//    fun rxCacheBuilderEntity(): RxCacheBuilderEntity
//
//    /**
//     * 获取IRepositoryManager
//     */
//    fun iRepositoryManager(): IRepositoryManager


}