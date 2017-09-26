package com.junwu.mvpattempt.base.models;

import com.junwu.mvplibrary.http.IRepositoryManager;
import com.junwu.mvplibrary.mvp.model.LibBaseModel;

/**
 * ===============================
 * 描    述：app模块下Model基类
 * 作    者：pjw
 * 创建日期：2017/9/14 17:06
 * ===============================
 */
public class BaseModel<RF, RC> extends LibBaseModel implements IBaseModel {

    private Class<RF> mRetrofitClass;
    private Class<RC> mRxCacheClass;

    /**
     * 提供网络、本地等数据
     * 普通model不需要调用Retrofit和RxCache的任何一个
     */
    public BaseModel() {
        super(null);
    }

    /**
     * 提供网络、本地等数据
     *
     * @param manager {@link IRepositoryManager}
     */
    public BaseModel(IRepositoryManager manager) {
        super(manager);
    }

    /**
     * 提供网络、本地等数据
     *
     * @param manager {@link IRepositoryManager}
     */
    public BaseModel(IRepositoryManager manager, Class<RF> retrofitClass, Class<RC> rxCacheClass) {
        super(manager);
        this.mRetrofitClass = retrofitClass;
        this.mRxCacheClass = rxCacheClass;
    }

    /**
     * 获取api代理类，由子类实现
     *
     * @return 代理类
     */
    public RF getRetrofitApiService() {
        if (mRetrofitClass == null) {
            throw new NullPointerException("需要调用BaseModel(IRepositoryManager ，Class<RF>, Class<RT>)构造函数初始化，或者调用setRetrofitClass()方法设置mRetrofitClass");
        }
        return getRetrofitApiService(mRetrofitClass);
    }

    /**
     * 获取api代理类，由子类实现
     *
     * @return 代理类
     */
    public RC getRxCacheApiService() {
        if (mRxCacheClass == null) {
            throw new NullPointerException("需要调用BaseModel(IRepositoryManager ，Class<RF>, Class<RT>)构造函数初始化，或者调用setRxCacheClass()方法设置mRxCacheClass");
        }
        return getRxCacheApiService(mRxCacheClass);
    }

    /**
     * 设置RetrofitApiClass
     *
     * @param retrofitClass RetrofitApiClass
     */
    public void setRetrofitClass(Class<RF> retrofitClass) {
        mRetrofitClass = retrofitClass;
    }

    /**
     * 设置RxCacheApiClass
     *
     * @param rxCacheClass RxCacheApiClass
     */
    public void setRxCacheClass(Class<RC> rxCacheClass) {
        mRxCacheClass = rxCacheClass;
    }

}
