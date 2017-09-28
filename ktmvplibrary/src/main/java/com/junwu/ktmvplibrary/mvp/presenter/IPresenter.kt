package com.junwu.ktmvplibrary.mvp.presenter

/**
 * ===============================
 * 描    述：Presenter实现接口
 * 作    者：pjw
 * 创建日期：2017/6/29 15:54
 * ===============================
 */
interface IPresenter {
    /**
     * 做准备操作
     */
    fun onStart()

    /**
     * 做销毁操作
     */
    fun onDestroy()
}
