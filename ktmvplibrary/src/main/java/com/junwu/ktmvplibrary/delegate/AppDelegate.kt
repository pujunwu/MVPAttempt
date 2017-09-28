package com.junwu.ktmvplibrary.delegate

import android.app.Application
import com.junwu.basicslibrary.delegate.impls.ActivityLifecycle
import com.junwu.ktmvplibrary.config.IConfigModule
import com.junwu.ktmvplibrary.config.IRegisterApiModule
import com.junwu.ktmvplibrary.di.component.AppComponent
import com.junwu.ktmvplibrary.di.component.DaggerAppComponent
import com.junwu.ktmvplibrary.di.module.AppModule
import com.junwu.ktmvplibrary.di.module.ClientHttpModule
import com.junwu.ktmvplibrary.di.module.ConfigModule
import okhttp3.OkHttpClient
import javax.inject.Inject

/**
 * ===============================
 * 描    述：app代理类
 * 作    者：pjw
 * 创建日期：2017/9/28 10:38
 * ===============================
 */
class AppDelegate(application: Application) {

    //当前对象
    lateinit var sAppDelegate: AppDelegate
    //Application
    private val mApplication: Application = application
    //Activity生命周期监听类
    private var mActivityLifecycle: ActivityLifecycle? = null
    //Component
    private var mAppComponent: AppComponent? = null

    @Inject
    lateinit var mArticlePresenter: Application

    /**
     * 在application的onCreate方法中调用
     */
    fun onCreate() {
        mActivityLifecycle = ActivityLifecycle()
        mApplication?.registerActivityLifecycleCallbacks(mActivityLifecycle)
        sAppDelegate = this
    }

    /**
     * 初始化网络模块，包括了网络数据，本地缓存数据，以及其他数据
     * 建议在application的onCreate方法中调用
     *
     * @param configModules 配置ApiService和cacheApi接口
     */
    fun injectRegApiService(configModules: IConfigModule, vararg registerApiModules: IRegisterApiModule) {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(AppModule(mApplication))//提供application
                .clientHttpModule(ClientHttpModule())//用于提供okhttp和retrofit的单例
                .configModule(getConfigModule(configModules))
                .build()
        mAppComponent?.inject(this)
        //调用注册apiService
//        if (registerApiModules != null) {
//            val manager = mAppComponent!!.getIRepositoryManager()
//            for (module in registerApiModules) {
//                module.registerComponents(manager)
//            }
//        }
    }

    /**
     * 销毁调用方法
     */
    fun onTerminate() {
        if (mActivityLifecycle != null) {
            mApplication?.unregisterActivityLifecycleCallbacks(mActivityLifecycle)
        }
//        mAppComponent?.getIRepositoryManager()?.onTerminate()
        this.mActivityLifecycle = null
        this.mAppComponent = null
    }

    /**
     * 获取ConfigModule，并调用配置applyOptions方法
     */
    private fun getConfigModule(configModule: IConfigModule): ConfigModule {
        val builder = ConfigModule.builder()
        //调用之后OKHttp、retrofit、rxCache的基本配置信息就已经知道
        configModule.applyOptions(builder)
        return builder.build()
    }

    fun getAppComponent(): AppComponent? {
        return mAppComponent
    }

}