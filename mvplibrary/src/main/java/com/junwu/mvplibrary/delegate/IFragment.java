package com.junwu.mvplibrary.delegate;

import android.view.View;

/**
 * ===============================
 * 描    述：fragment
 * 作    者：pjw
 * 创建日期：2017/6/29 10:45
 * ===============================
 */
public interface IFragment {

    /**
     * 获取当前布局Id
     */
    int getLayoutId();

    /**
     * 获取当前显示的布局
     */
    View getLayoutView();

    /**
     * 是否自动注册EventBus事件
     *
     * @return 是否注册EventBus事件
     */
    boolean useEventBus();

    /**
     * 执行注入方法
     */
    void initInject();

    /**
     * 获取是否是懒加载
     *
     * @return 是否是懒加载
     */
    boolean isLazyLoad();

    /**
     * 初始化方法
     */
    void initData();


}
