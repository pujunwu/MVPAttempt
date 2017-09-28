package com.junwu.ktmvplibrary.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.junwu.basicslibrary.delegate.IActivity
import com.junwu.ktmvplibrary.mvp.view.IView

/**
 * ===============================
 * 描    述：所有activity继承的基类
 * 作    者：pjw
 * 创建日期：2017/9/28 11:24
 * ===============================
 */
abstract class LibBaseActivity : AppCompatActivity() , IActivity, IView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInject()//执行注入
        initData()//初始化
    }

    /************************[接口实现][IActivity] */

    override fun getLayoutView(): View? {
        return null
    }

    override fun eventBus(): Boolean {
        return false
    }

    override fun fragment(): Boolean {
        return false
    }

    /************************[接口实现][IView] */

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /************************其他方法************************/

}