package com.junwu.ktmvplibrary.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import butterknife.ButterKnife
import butterknife.Unbinder
import com.junwu.ktmvplibrary.R
import org.simple.eventbus.EventBus

/**
 * ===============================
 * 描    述：
 * 作    者：pjw
 * 创建日期：2017/9/28 11:28
 * ===============================
 */
abstract class LibLazyFragment : LibBaseFragment() {

    //当前是否初始化
    private var isInit = false
    //根布局
    private var lazyRootView: ViewGroup? = null
    //布局状态值
    private var isStart = false
    //初始化控件返回用于销毁对象
    private var mUnbinder: Unbinder? = null
    //获取布局用
    protected var inflater: LayoutInflater? = null

    /**
     * 懒加载
     */
    init {
        arguments = getBundle()
    }

    @Deprecated("")
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.inflater = inflater
        return initView(inflater, container)
    }

    @Deprecated("")
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && !isInit && lazyRootView != null) {
            isInit = true
            val contextView = initView(inflater, lazyRootView)
            lazyRootView!!.removeAllViews()
            lazyRootView!!.addView(contextView)
            if (isAnimation()) {
                val animation = AnimationUtils.loadAnimation(context, R.anim.alpha_visiable)
                animation.duration = 200
                contextView!!.startAnimation(animation)
            }
            bindAndEvent(contextView)
            onResumeLazy()
        } else if (isInit && lazyRootView != null) {
            if (isVisibleToUser) {
                isStart = true
                onFragmentStartLazy()
            } else {
                isStart = false
                onFragmentStopLazy()
            }
        }
    }

    @Deprecated("")
    override fun initView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        if (isLazyLoading() && !isInit) {
            if (userVisibleHint && !isInit) {//当前Fragment已经显示
                lazyRootView = super.initView(inflater, container) as ViewGroup?
                rootView = lazyRootView//更新根布局
                bindAndEvent(lazyRootView)
                return lazyRootView
            }
            //当前Fragment未显示
            var layoutInflater = inflater
            if (layoutInflater == null) {
                layoutInflater = LayoutInflater.from(context)
            }
            lazyRootView = FrameLayout(layoutInflater!!.context)
            lazyRootView!!.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            rootView = lazyRootView//更新根布局
            return lazyRootView
        } else {
            return super.initView(inflater, container)
        }
    }

    /**
     * 绑定事件和初始化控件
     *
     * @param rootView 根布局
     */
    protected fun bindAndEvent(rootView: View?) {
        mUnbinder = ButterKnife.bind(this, rootView!!)
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
        initData()
    }

    /**
     * 是否是懒加载
     *
     * @return 是否是懒加载
     */
    @Deprecated("")
    override fun isLazyLoad(): Boolean {
        return true
    }

    @Deprecated("")
    override fun onStop() {
        super.onStop()
        if (isInit && isStart && userVisibleHint) {
            isStart = false
            onFragmentStopLazy()
        }
    }

    @Deprecated("")
    override fun onResume() {
        super.onResume()
        if (isInit) {
            onResumeLazy()
        }
    }

    @Deprecated("")
    override fun onPause() {
        super.onPause()
        if (isInit) {
            onPauseLazy()
        }
    }

    @Deprecated("")
    override fun onDestroyView() {
        super.onDestroyView()
        if (mUnbinder != null && mUnbinder !== Unbinder.EMPTY) {
            mUnbinder!!.unbind()
            mUnbinder = null
        }
        if (isInit) {
            onDestroyViewLazy()
        }
        isInit = false
        lazyRootView = null
        inflater = null
    }

    override fun onDestroy() {
        if (isLazyLoading() && useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroy()
    }

    protected fun onFragmentStartLazy() {}

    protected fun onFragmentStopLazy() {}

    protected fun onResumeLazy() {}

    protected fun onPauseLazy() {}

    protected fun onDestroyViewLazy() {}

    /**
     * 显示实际布局是否有动画
     *
     * @return 是否有动画
     */
    protected fun isAnimation(): Boolean {
        return true
    }

    /**
     * 获取是否是懒加载
     *
     * @return 是否是懒加载
     */
    protected fun isLazyLoading(): Boolean {
        return true
    }

}