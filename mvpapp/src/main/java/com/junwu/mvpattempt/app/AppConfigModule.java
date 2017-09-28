package com.junwu.mvpattempt.app;

import android.text.TextUtils;

import com.junwu.mvpattempt.BuildConfig;
import com.junwu.mvpattempt.comm.Constants;
import com.junwu.mvplibrary.config.IConfigModule;
import com.junwu.mvplibrary.di.module.ConfigModule;
import com.junwu.mvplibrary.http.LoggerInterceptor;

import io.rx_cache2.internal.RxCache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * ===============================
 * 描    述：设置
 * OkHttp、Retrofit和RxCache配置
 * 作    者：pjw
 * 创建日期：2017/7/4 14:55
 * ===============================
 */
public class AppConfigModule implements IConfigModule {

    @Override
    public void applyOptions(ConfigModule.Builder builder) {
        builder.setApiUrl("http:www.baidu.com/")//这个必须设置
                //配置okHttp，设置拦截器就在这里设置
                .setOkhttpConfiguration(new ConfigModule.OkHttpConfiguration() {
                    @Override
                    public void configOkhttp(OkHttpClient.Builder builder) {
                        if (BuildConfig.FormalMode) {
                            //调试模式打印请求log信息，LoggerInterceptor拦截器应该设置为最后一个拦截器，否则请求可能会不正确
                            builder.addInterceptor(new LoggerInterceptor());
                        }
                    }
                })
                //配置Retrofit，默认请求超时和写入超时时间为30秒
                .setRetrofitConfiguration(new ConfigModule.RetrofitConfiguration() {
                    @Override
                    public void configRetrofit(Retrofit.Builder builder) {

                    }
                })
                //配置RxCache
                .setRxCacheConfiguration(new ConfigModule.RxCacheConfiguration() {
                    @Override
                    public void configRxCache(RxCache.Builder builder) {
                        builder.setMaxMBPersistenceCache(50)//设置缓存为50M
                                .useExpiredDataIfLoaderNotAvailable(true);//设置true缓存过期是也可以使用缓存
                        builder.getCacheDirectory();
                    }
                }).setRxCacheFile(TextUtils.isEmpty(Constants.SDCARDROOTPATH) ? "" : Constants.RXCACHEDIRPATH);
        //RxCache缓存地址，如果不配置默认为app当前缓存地址/rxCacheFile，默认缓存空间为100M
//                .setRxCacheFile(new File(""));
    }

}
