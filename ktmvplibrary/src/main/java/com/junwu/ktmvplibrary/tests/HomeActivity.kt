package com.junwu.ktmvplibrary.tests

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.junwu.ktmvplibrary.di.component.DaggerAppComponent
import com.junwu.ktmvplibrary.di.module.AppModule
import com.junwu.ktmvplibrary.di.module.ClientHttpModule
import com.junwu.ktmvplibrary.di.module.ConfigModule
import javax.inject.Inject

/**
 * ===============================
 * 描    述：
 * 作    者：pjw
 * 创建日期：2017/9/28 15:38
 * ===============================
 */
class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var homeModule: HomeModule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppComponent.builder()
                .appModule(AppModule(application))
                .clientHttpModule(ClientHttpModule())
                .configModule(ConfigModule.builder().build())
                .build()
                .inject(this)

//        homeModule?.dd()
    }


}