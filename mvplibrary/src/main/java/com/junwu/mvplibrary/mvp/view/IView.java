package com.junwu.mvplibrary.mvp.view;

/**
 * ===============================
 * 描    述：
 * 作    者：pjw
 * 创建日期：2017/6/29 15:55
 * ===============================
 */
public interface IView {

    /**
     * 显示加载
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 显示信息
     */
    void showMessage(String message);
}
