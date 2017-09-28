package com.junwu.ktmvplibrary.config

import com.junwu.ktmvplibrary.di.module.ConfigModule

/**
 * ===============================
 * 描    述：需要配置OKHttp、retrofit、rxCache就实现该接口
 * 作    者：pjw
 * 创建日期：2017/9/28 10:43
 * ===============================
 */
interface IConfigModule {

    /**
     * 需要配置OKHttp、retrofit、rxCache就实现该方法
     *
     * @param builder ConfigModule.Builder
     */
    fun applyOptions(builder: ConfigModule.Builder)
}