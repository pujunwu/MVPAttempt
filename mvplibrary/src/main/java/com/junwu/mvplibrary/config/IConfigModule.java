package com.junwu.mvplibrary.config;

import com.junwu.mvplibrary.di.module.ConfigModule;

/**
 * ===============================
 * 描    述：需要配置OKHttp、retrofit、rxCache就实现该接口
 * 作    者：pjw
 * 创建日期：2017/7/3 11:22
 * ===============================
 */
public interface IConfigModule {

    /**
     * 需要配置OKHttp、retrofit、rxCache就实现该方法
     *
     * @param builder ConfigModule.Builder
     */
    void applyOptions(ConfigModule.Builder builder);
}
