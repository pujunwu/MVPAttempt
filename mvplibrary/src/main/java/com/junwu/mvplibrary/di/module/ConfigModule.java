package com.junwu.mvplibrary.di.module;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * ===============================
 * 描    述：Retrofit、Okhttp和RxCache的配置
 * 作    者：pjw
 * 创建日期：2017/7/3 10:37
 * ===============================
 */
@Module
public class ConfigModule {

    //请求地址
    private HttpUrl mApiUrl;
    //Retrofit配置
    private RetrofitConfiguration mRetrofitConfiguration;
    //okHttp配置
    private OkHttpConfiguration mOkHttpConfiguration;
    //RxCache 配置
    private RxCacheConfiguration mRxCacheConfiguration;
    //rxCache缓存目录
    private File rxCacheFile;

    private ConfigModule(Builder builder) {
        mApiUrl = builder.mApiUrl;
        mRetrofitConfiguration = builder.mRetrofitConfiguration;
        mOkHttpConfiguration = builder.mOkhttpConfiguration;
        mRxCacheConfiguration = builder.mRxCacheConfiguration;
        rxCacheFile = builder.rxCacheFile;
    }

    public interface RetrofitConfiguration {
        void configRetrofit(Retrofit.Builder builder);
    }

    public interface OkHttpConfiguration {
        void configOkhttp(OkHttpClient.Builder builder);
    }

    public interface RxCacheConfiguration {
        void configRxCache(RxCache.Builder builder);
    }

    @Singleton
    @Provides
    HttpUrl provideHttpUrlConfiguration() {
        return mApiUrl;
    }

    @Singleton
    @Provides
    RetrofitConfiguration provideRetrofitConfiguration() {
        return mRetrofitConfiguration;
    }

    @Singleton
    @Provides
    OkHttpConfiguration provideOkhttpConfiguration() {
        return mOkHttpConfiguration;
    }

    @Singleton
    @Provides
    RxCacheConfiguration provideRxCacheConfiguration() {
        return mRxCacheConfiguration;
    }

    @Singleton
    @Provides
    @Named(value = "RxCacheDirectory")
    File provideRxCacheFile(Application application) {
        if (rxCacheFile == null) {
            rxCacheFile = getCacheFile(application);
        } else {
            if (makeDirs(rxCacheFile) == 1) {//创建失败，或者不可写
                rxCacheFile = getCacheFile(application);
            }
        }
        return rxCacheFile;
    }

    /**
     * 返回缓存文件夹
     */
    public static File getCacheFile(Context context) {
        File file = context.getCacheDir();
        String cachePath = file.getAbsolutePath() + "/rxCacheFile";
        file = new File(cachePath);
        makeDirs(file);
        return file;
    }

    /**
     * 创建未存在的文件夹
     *
     * @param file 缓存目录文件
     * @return 缓存目录文件
     */
    public static int makeDirs(File file) {
        Log.d("123", "path:" + file.getAbsolutePath());
        if (!file.exists()) {
            file.mkdirs();
        }
        if (!file.canWrite()) {//判断是否可写
            return 1;
        } else {
            return 0;
        }
    }

    public static class Builder {
        //请求地址
        private HttpUrl mApiUrl;
        //Retrofit配置
        private RetrofitConfiguration mRetrofitConfiguration;
        //okHttp配置
        private OkHttpConfiguration mOkhttpConfiguration;
        //RxCache 配置
        private RxCacheConfiguration mRxCacheConfiguration;
        //rxCache缓存目录
        private File rxCacheFile;

        public Builder setApiUrl(String baseurl) {
            if (TextUtils.isEmpty(baseurl)) {
                throw new IllegalArgumentException("baseurl can not be empty");
            }
            this.mApiUrl = HttpUrl.parse(baseurl);
            return this;
        }

        public Builder setRetrofitConfiguration(RetrofitConfiguration retrofitConfiguration) {
            if (retrofitConfiguration == null) {
                retrofitConfiguration = new RetrofitConfiguration() {
                    @Override
                    public void configRetrofit(Retrofit.Builder builder) {

                    }
                };
            }
            mRetrofitConfiguration = retrofitConfiguration;
            return this;
        }

        public Builder setOkhttpConfiguration(OkHttpConfiguration okhttpConfiguration) {
            if (okhttpConfiguration == null) {
                okhttpConfiguration = new OkHttpConfiguration() {
                    @Override
                    public void configOkhttp(OkHttpClient.Builder builder) {

                    }
                };
            }
            mOkhttpConfiguration = okhttpConfiguration;
            return this;
        }

        public Builder setRxCacheConfiguration(RxCacheConfiguration rxCacheConfiguration) {
            if (rxCacheConfiguration == null) {
                rxCacheConfiguration = new RxCacheConfiguration() {
                    @Override
                    public void configRxCache(RxCache.Builder builder) {

                    }
                };
            }
            mRxCacheConfiguration = rxCacheConfiguration;
            return this;
        }

        public Builder setRxCacheFile(File rxCacheFile) {
            this.rxCacheFile = rxCacheFile;
            return this;
        }

        public Builder setRxCacheFile(String rxCacheFile) {
            if (!TextUtils.isEmpty(rxCacheFile)) {
                this.rxCacheFile = new File(rxCacheFile);
            }
            return this;
        }

        private void isEmpty() {
            setRetrofitConfiguration(mRetrofitConfiguration)
                    .setOkhttpConfiguration(mOkhttpConfiguration)
                    .setRxCacheConfiguration(mRxCacheConfiguration);
        }

        public ConfigModule build() {
            isEmpty();
            return new ConfigModule(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

}
