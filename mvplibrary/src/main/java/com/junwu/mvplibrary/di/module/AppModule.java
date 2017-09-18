package com.junwu.mvplibrary.di.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * ===============================
 * 描    述：AppModule
 * 作    者：pjw
 * 创建日期：2017/6/29 19:43
 * ===============================
 */
@Module
public class AppModule {

    private Application mApplication;

    public AppModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Singleton
    @Provides
    public Application providerApplication() {
        return mApplication;
    }

}
