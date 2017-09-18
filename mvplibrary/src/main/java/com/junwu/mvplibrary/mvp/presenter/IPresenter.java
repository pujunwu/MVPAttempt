package com.junwu.mvplibrary.mvp.presenter;

/**
 * ===============================
 * 描    述：Presenter实现接口
 * 作    者：pjw
 * 创建日期：2017/6/29 15:54
 * ===============================
 */
public interface IPresenter {
    /**
     * 做准备操作
     */
    void onStart();

    /**
     * 做销毁操作
     */
    void onDestroy();
}
