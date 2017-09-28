package com.junwu.ktmvplibrary.mvp.view

/**
 * ===============================
 * 描    述：
 * 作    者：pjw
 * 创建日期：2017/6/29 15:55
 * ===============================
 */
interface IView {

    /**
     * 显示加载
     */
    fun showLoading()

    /**
     * 隐藏加载
     */
    fun hideLoading()

    /**
     * 显示信息
     */
    fun showMessage(message: String)
}
