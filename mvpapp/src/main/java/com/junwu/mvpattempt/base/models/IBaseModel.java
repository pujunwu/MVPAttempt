package com.junwu.mvpattempt.base.models;

import com.junwu.mvplibrary.mvp.model.IModel;

/**
 * ===============================
 * 描    述：所有Model需要实现的接口
 * 作    者：pjw
 * 创建日期：2017/9/14 17:26
 * ===============================
 */
public interface IBaseModel<RF, RC> extends IModel{

    /**
     * 获取api代理类，由子类实现
     *
     * @return 代理类
     */
    RF getRetrofitApiService();

    /**
     * 获取api代理类，由子类实现
     *
     * @return 代理类
     */
    RC getRxCacheApiService();

}
