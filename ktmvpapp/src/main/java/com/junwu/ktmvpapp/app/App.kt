package com.junwu.ktmvpapp.app

import android.app.Application

/**
 * ===============================
 * 描    述：
 * 作    者：pjw
 * 创建日期：2017/9/28 10:25
 * ===============================
 */
class App : Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        lateinit var instance: App
    }

}
