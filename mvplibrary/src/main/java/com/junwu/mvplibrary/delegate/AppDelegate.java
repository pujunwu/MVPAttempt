package com.junwu.mvplibrary.delegate;

import android.app.Application;

import com.junwu.basicslibrary.delegate.impls.ActivityLifecycle;
import com.junwu.mvplibrary.config.IConfigModule;
import com.junwu.mvplibrary.config.IRegisterApiModule;
import com.junwu.mvplibrary.di.component.AppComponent;
import com.junwu.mvplibrary.di.module.ConfigModule;
import com.junwu.mvplibrary.http.IRepositoryManager;

/**
 * ===============================
 * 描    述：app代理类，这样实现的好长是不需要继承Application，只在对应方法里面调用约定方法即可
 * 作    者：pjw
 * 创建日期：2017/6/27 17:13
 * ===============================
 */
public class AppDelegate {

    //当前对象
    public static AppDelegate sAppDelegate;
    //Application
    private Application mApplication;
    //Activity生命周期监听类
    private ActivityLifecycle mActivityLifecycle;
    //Component
    private AppComponent mAppComponent;

    /**
     * app代理类
     *
     * @param application Application
     */
    public AppDelegate(Application application) {
        this.mApplication = application;
    }

    /**
     * 在application的onCreate方法中调用
     */
    public void onCreate() {
        mActivityLifecycle = new ActivityLifecycle();
        mApplication.registerActivityLifecycleCallbacks(mActivityLifecycle);
        sAppDelegate = this;
    }

    /**
     * 初始化网络模块，包括了网络数据，本地缓存数据，以及其他数据
     * 建议在application的onCreate方法中调用
     *
     * @param configModules 配置ApiService和cacheApi接口
     */
    public void injectRegApiService(IConfigModule configModules, IRegisterApiModule... registerApiModules) {
//        mAppComponent = DaggerAppComponent.builder()
//                .appModule(new AppModule(mApplication))//提供application
//                .clientHttpModule(new ClientHttpModule())//用于提供okhttp和retrofit的单例
//                .configModule(getConfigModule(configModules))
//                .build();
        mAppComponent.inject(this);
        //调用注册apiService
        if (registerApiModules != null) {
            IRepositoryManager manager = mAppComponent.getIRepositoryManager();
            for (IRegisterApiModule module : registerApiModules) {
                module.registerComponents(manager);
            }
        }
    }

    /**
     * 销毁调用方法
     */
    public void onTerminate() {
        if (mActivityLifecycle != null) {
            mApplication.unregisterActivityLifecycleCallbacks(mActivityLifecycle);
        }
        mAppComponent.getIRepositoryManager().onTerminate();
        this.mActivityLifecycle = null;
        this.mApplication = null;
        this.mAppComponent = null;
    }

    /**
     * 获取ConfigModule，并调用配置applyOptions方法
     */
    private ConfigModule getConfigModule(IConfigModule configModule) {
        ConfigModule.Builder builder = ConfigModule.builder();
        //调用之后OKHttp、retrofit、rxCache的基本配置信息就已经知道
        configModule.applyOptions(builder);
        return builder.build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

}
