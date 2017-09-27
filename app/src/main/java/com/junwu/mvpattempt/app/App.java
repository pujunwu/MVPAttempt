package com.junwu.mvpattempt.app;

import android.app.Application;
import android.content.Context;

import com.junwu.mvpattempt.di.component.DaggerIViewComponent;
import com.junwu.mvpattempt.di.component.IViewComponent;
import com.junwu.mvpattempt.di.module.ModelModule;
import com.junwu.mvpattempt.di.module.UtilsModule;
import com.junwu.mvpattempt.di.module.ViewModule;
import com.junwu.mvplibrary.config.ConfigModule;
import com.junwu.mvplibrary.delegate.AppDelegate;
import com.junwu.mvplibrary.di.component.AppComponent;
import com.junwu.mvplibrary.http.IRepositoryManager;

/**
 * ===============================
 * 描    述：Application
 * 作    者：pjw
 * 创建日期：2017/9/15 9:25
 * ===============================
 */
public class App extends Application {

    private AppDelegate mAppDelegate;
    //当前app实例对象
    public static App sApp;
    //全局上下文对象
    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        sContext = this;
        mAppDelegate = new AppDelegate(sApp);
        //这个方法执行之后会监听每个activity和fragment的生命周期
        //建议在Application的onCreate方法里面调用
        mAppDelegate.onCreate();
        initInject();//初始化全局注入
    }

    /**
     * 初始化全局注入
     */
    public void initInject() {
        /**初始化注入,这个方法调用了之后就会调用{@link com.junwu.mvplibrary.config.IConfigModule#applyOptions(ConfigModule.Builder)}方法配置参数，
         * 接着就会调用{@link com.junwu.mvplibrary.config.IRegisterApiModule#registerComponents(IRepositoryManager)}方法，设置api接口
         * 可以在测试接口确定了之后在调用该方法，比如：测试阶段需要选择测试服务器地址，选择之后再调用这个方法
         **/
        mAppDelegate.injectRegApiService(new AppConfigModule(), new RegisterApiModule());
    }

    @Override
    public void onTerminate() {
        if (mAppDelegate != null) {
            mAppDelegate.onTerminate();
        }
        super.onTerminate();
    }

    /**
     * 获取AppComponent
     *
     * @return AppComponent
     */
    protected AppComponent getAppComponent() {
        //这个方法调用必须在mAppDelegate.onCreate()之后
        return AppDelegate.sAppDelegate.getAppComponent();
    }

    /**
     * 获取IViewComponent
     *
     * @return IViewComponent
     */
    public IViewComponent getIViewComponent(ViewModule viewModule) {
        return DaggerIViewComponent.builder()
                .appComponent(getAppComponent())
                .viewModule(viewModule)
                .modelModule(new ModelModule())
                .utilsModule(new UtilsModule())
                .build();
    }

}
