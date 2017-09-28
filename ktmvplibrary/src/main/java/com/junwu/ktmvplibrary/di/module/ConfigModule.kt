package com.junwu.ktmvplibrary.di.module

import android.app.Application
import android.content.Context
import android.text.TextUtils
import android.util.Log
import dagger.Module
import dagger.Provides
import io.rx_cache2.internal.RxCache
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

/**
 * ===============================
 * 描    述：
 * 作    者：pjw
 * 创建日期：2017/9/25 15:27
 * ===============================
 */
@Module
class ConfigModule(builder: Builder) {

    //请求地址
    private var mApiUrl: HttpUrl = builder.mApiUrl
    //Retrofit配置
    private var mRetrofitConfiguration: RetrofitConfiguration = builder.mRetrofitConfiguration
    //okHttp配置
    private var mOkHttpConfiguration: OkHttpConfiguration = builder.mOkhttpConfiguration
    //RxCache 配置
    private var mRxCacheConfiguration: RxCacheConfiguration = builder.mRxCacheConfiguration
    //rxCache缓存目录
    private var rxCacheFile: File = builder.rxCacheFile

    interface RetrofitConfiguration {
        fun configRetrofit(builder: Retrofit.Builder)
    }

    interface OkHttpConfiguration {
        fun configOkhttp(builder: OkHttpClient.Builder)
    }

    interface RxCacheConfiguration {
        fun configRxCache(builder: RxCache.Builder)
    }

    @Singleton
    @Provides
    fun provideHttpUrlConfiguration(): HttpUrl? {
        return mApiUrl
    }

    @Singleton
    @Provides
    fun provideRetrofitConfiguration(): RetrofitConfiguration? {
        return mRetrofitConfiguration
    }

    @Singleton
    @Provides
    fun provideOkhttpConfiguration(): OkHttpConfiguration? {
        return mOkHttpConfiguration
    }

    @Singleton
    @Provides
    fun provideRxCacheConfiguration(): RxCacheConfiguration? {
        return mRxCacheConfiguration
    }

    @Singleton
    @Provides
//    @Named(value = "RxCacheDirectory")
    fun provideRxCacheFile(application: Application): File {
        if (rxCacheFile == null) {
            rxCacheFile = getCacheFile(application)
        } else {
            if (makeDirs(rxCacheFile) == 1) {//创建失败，或者不可写
                rxCacheFile = getCacheFile(application)
            }
        }
        return rxCacheFile
    }

    /**
     * 返回缓存文件夹
     */
    fun getCacheFile(context: Context): File {
        var file = context.cacheDir
        val cachePath = file.absolutePath + "/rxCacheFile"
        file = File(cachePath)
        makeDirs(file)
        return file
        //        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
        //            File file = null;
        //            file = context.getExternalCacheDir();//获取系统管理的sd卡缓存文件
        //            if (file == null) {//如果获取的为空,就是用自己定义的缓存文件夹做缓存路径
        //                file = new File(getCacheFilePath(context));
        //                makeDirs(file);
        //            }
        //            return file;
        //        } else {
        //            return context.getCacheDir();
        //        }
    }

//    /**
//     * 获取自定义缓存文件地址
//     *
//     * @param context 上下文
//     * @return 路径
//     */
//    public static String getCacheFilePath(Context context) {
//        String packageName = context.getPackageName();
//        return "/mnt/sdcard/" + packageName;
//    }

    /**
     * 创建未存在的文件夹
     *
     * @param file 缓存目录文件
     * @return 缓存目录文件
     */
    fun makeDirs(file: File): Int {
        Log.d("123", "path:" + file.absolutePath)
        if (!file.exists()) {
            file.mkdirs()
        }
        return if (!file.canWrite()) {//判断是否可写
            1
        } else {
            0
        }
    }


//    @Singleton
//    @Provides
//    @Named(value = "Response")
//    Interceptor provideResponseInterceptor() {
//        return mResponseInterceptor;
//    }
//
//    @Singleton
//    @Provides
//    @Named(value = "Request")
//    Interceptor provideRequestInterceptor() {
//        return mRequestInterceptor;
//    }
//
//    @Singleton
//    @Provides
//    @Named(value = "Log")
//    Interceptor provideLogInterceptor() {
//        return mLogInterceptor;
//    }

    class Builder {
        //请求地址
        lateinit var mApiUrl: HttpUrl
        //Retrofit配置
        lateinit var mRetrofitConfiguration: RetrofitConfiguration
        //okHttp配置
        lateinit var mOkhttpConfiguration: OkHttpConfiguration
        //RxCache 配置
        lateinit var mRxCacheConfiguration: RxCacheConfiguration
        //rxCache缓存目录
        lateinit var rxCacheFile: File

        fun setApiUrl(baseurl: String): Builder {
            if (TextUtils.isEmpty(baseurl)) {
                throw IllegalArgumentException("baseurl can not be empty")
            }
            this.mApiUrl = HttpUrl.parse(baseurl)
            return this
        }

        //        public Builder setResponseInterceptor(Interceptor responseInterceptor) {
        //            if (responseInterceptor == null) {
        //                responseInterceptor = new Interceptor() {
        //                    @Override
        //                    public Response intercept(Chain chain) throws IOException {
        //                        return chain.proceed(chain.request());
        //                    }
        //                };
        //            }
        //            mResponseInterceptor = responseInterceptor;
        //            return this;
        //        }
        //
        //        public Builder setRequestInterceptor(Interceptor requestInterceptor) {
        //            if (requestInterceptor == null) {
        //                requestInterceptor = new Interceptor() {
        //                    @Override
        //                    public Response intercept(Chain chain) throws IOException {
        //                        return chain.proceed(chain.request());
        //                    }
        //                };
        //            }
        //            mRequestInterceptor = requestInterceptor;
        //            return this;
        //        }
        //
        //        public Builder setLogInterceptor(Interceptor logInterceptor) {
        //            if (logInterceptor == null) {
        //                logInterceptor = new Interceptor() {
        //                    @Override
        //                    public Response intercept(Chain chain) throws IOException {
        //                        return chain.proceed(chain.request());
        //                    }
        //                };
        //            }
        //            mLogInterceptor = logInterceptor;
        //            return this;
        //        }

        fun setRetrofitConfiguration(retrofitConfiguration: RetrofitConfiguration?): Builder {
            var retrofitConfiguration = retrofitConfiguration
            if (retrofitConfiguration == null) {
                retrofitConfiguration = object : RetrofitConfiguration {
                    override fun configRetrofit(builder: Retrofit.Builder) {

                    }
                }
            }
            mRetrofitConfiguration = retrofitConfiguration
            return this
        }

        fun setOkhttpConfiguration(okhttpConfiguration: OkHttpConfiguration?): Builder {
            var okhttpConfiguration = okhttpConfiguration
            if (okhttpConfiguration == null) {
                okhttpConfiguration = object : OkHttpConfiguration {
                    override fun configOkhttp(builder: OkHttpClient.Builder) {

                    }
                }
            }
            mOkhttpConfiguration = okhttpConfiguration
            return this
        }

        fun setRxCacheConfiguration(rxCacheConfiguration: RxCacheConfiguration?): Builder {
            var rxCacheConfiguration = rxCacheConfiguration
            if (rxCacheConfiguration == null) {
                rxCacheConfiguration = object : RxCacheConfiguration {
                    override fun configRxCache(builder: RxCache.Builder) {

                    }
                }
            }
            mRxCacheConfiguration = rxCacheConfiguration
            return this
        }

        fun setRxCacheFile(rxCacheFile: File): Builder {
            this.rxCacheFile = rxCacheFile
            return this
        }

        fun setRxCacheFile(rxCacheFile: String): Builder {
            if (!TextUtils.isEmpty(rxCacheFile)) {
                this.rxCacheFile = File(rxCacheFile)
            }
            return this
        }

        private fun isEmpty() {
            //            setResponseInterceptor(mResponseInterceptor)
            //                    .setRequestInterceptor(mRequestInterceptor)
            //                    .setLogInterceptor(mLogInterceptor)
            setRetrofitConfiguration(mRetrofitConfiguration)
                    .setOkhttpConfiguration(mOkhttpConfiguration)
                    .setRxCacheConfiguration(mRxCacheConfiguration)
        }

        fun build(): ConfigModule {
            isEmpty()
            return ConfigModule(this)
        }
    }

    companion object {
        fun builder(): Builder {
            return Builder()
        }
    }

}