package com.junwu.mvplibrary.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.junwu.mvplibrary.R;

import org.simple.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * ===============================
 * 描    述：懒加载Fragment
 * 作    者：pjw
 * 创建日期：2017/7/27 14:56
 * ===============================
 */
public abstract class LibLazyFragment extends LibBaseFragment {

    //当前是否初始化
    private boolean isInit = false;
    //根布局
    private ViewGroup lazyRootView;
    //布局状态值
    private boolean isStart = false;
    //初始化控件返回用于销毁对象
    private Unbinder mUnbinder;
    //获取布局用
    protected LayoutInflater inflater;

    /**
     * 懒加载
     */
    public LibLazyFragment() {
        setArguments(getBundle());
    }

    @Nullable
    @Override
    @Deprecated
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        return initView(inflater, container);
    }

    @Override
    @Deprecated
    public final void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isInit && lazyRootView != null) {
            isInit = true;
            View contextView = initView(inflater, lazyRootView);
            lazyRootView.removeAllViews();
            lazyRootView.addView(contextView);
            if (isAnimation()) {
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.alpha_visiable);
                animation.setDuration(200);
                contextView.startAnimation(animation);
            }
            bindAndEvent(contextView);
            onResumeLazy();
        } else if (isInit && lazyRootView != null) {
            if (isVisibleToUser) {
                isStart = true;
                onFragmentStartLazy();
            } else {
                isStart = false;
                onFragmentStopLazy();
            }
        }
    }

    @Deprecated
    @Override
    public final View initView(LayoutInflater inflater, ViewGroup container) {
        if (isLazyLoading() && !isInit) {
            if (getUserVisibleHint() && !isInit) {//当前Fragment已经显示
                lazyRootView = (ViewGroup) super.initView(inflater, container);
                rootView = lazyRootView;//更新根布局
                bindAndEvent(lazyRootView);
                return lazyRootView;
            }
            //当前Fragment未显示
            LayoutInflater layoutInflater = inflater;
            if (layoutInflater == null) {
                layoutInflater = LayoutInflater.from(getContext());
            }
            lazyRootView = new FrameLayout(layoutInflater.getContext());
            lazyRootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            rootView = lazyRootView;//更新根布局
            return lazyRootView;
        } else {
            return super.initView(inflater, container);
        }
    }

    /**
     * 绑定事件和初始化控件
     *
     * @param rootView 根布局
     */
    protected void bindAndEvent(View rootView) {
        mUnbinder = ButterKnife.bind(this, rootView);
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
        initData();
    }

    /**
     * 是否是懒加载
     *
     * @return 是否是懒加载
     */
    @Override
    @Deprecated
    public final boolean isLazyLoad() {
        return true;
    }

    @Override
    @Deprecated
    public final void onStop() {
        super.onStop();
        if (isInit && isStart && getUserVisibleHint()) {
            isStart = false;
            onFragmentStopLazy();
        }
    }

    @Override
    @Deprecated
    public final void onResume() {
        super.onResume();
        if (isInit) {
            onResumeLazy();
        }
    }

    @Override
    @Deprecated
    public final void onPause() {
        super.onPause();
        if (isInit) {
            onPauseLazy();
        }
    }

    @Override
    @Deprecated
    public final void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
        if (isInit) {
            onDestroyViewLazy();
        }
        isInit = false;
        lazyRootView = null;
        inflater = null;
    }

    @Override
    public void onDestroy() {
        if (isLazyLoading() && useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    protected void onFragmentStartLazy() {
    }

    protected void onFragmentStopLazy() {
    }

    protected void onResumeLazy() {
    }

    protected void onPauseLazy() {
    }

    protected void onDestroyViewLazy() {
    }

    /**
     * 显示实际布局是否有动画
     *
     * @return 是否有动画
     */
    protected boolean isAnimation() {
        return true;
    }

    /**
     * 获取是否是懒加载
     *
     * @return 是否是懒加载
     */
    protected boolean isLazyLoading() {
        return true;
    }

}
