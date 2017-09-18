package com.junwu.mvplibrary.delegate;

import android.view.View;

/**
 * ===============================
 * 描    述：activity基类实现的接口，用于初始化和预处理操作
 * 作    者：pjw
 * 创建日期：2017/6/27 17:36
 * ===============================
 */
public interface IActivity {

    /**
     * 获取当前布局Id
     *
     * @return
     */
    int getLayoutId();

    /**
     * 执行注入方法
     */
    void initInject();

    /**
     * 获取当前显示的布局
     *
     * @return
     */
    View getLayoutView();

    /**
     * 子类做初始化操作
     */
    void initData();

    /**
     * 是否有事件绑定
     *
     * @return
     */
    boolean eventBus();

    /**
     * 是否使用fragment
     *
     * @return
     */
    boolean fragment();
}
