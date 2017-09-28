package com.junwu.ktmvplibrary.tests

import okhttp3.OkHttpClient
import javax.inject.Inject

/**
 * ===============================
 * 描    述：
 * 作    者：pjw
 * 创建日期：2017/9/28 15:39
 * ===============================
 */

class HomeModule @Inject constructor(private val api: OkHttpClient) {

    fun dd() {
        println(api)
    }

}