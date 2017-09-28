package com.junwu.ktmvplibrary.mvp.presenter

import android.util.Log
import com.junwu.basicslibrary.utils.HandlerUtil
import com.junwu.ktmvplibrary.mvp.model.IModel
import com.junwu.ktmvplibrary.mvp.view.IView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.simple.eventbus.EventBus

/**
 * ===============================
 * 描    述：所有Persenter的基类
 * 1、rxJava订阅
 * 2、EventBus事件注册
 * 作    者：pjw
 * 创建日期：2017/6/29 16:03
 * ===============================
 */
class LibBasePresenter<M, V>(m: IModel? = null, v: IView? = null) : IPresenter {

    //订阅事件管理
    protected var mCompositeDisposable: CompositeDisposable? = null
    protected var mModel: M?=null
    protected var mView: V? = null
    //是否注册了eventBus事件
    private var isEventBus = false
    //dialog打开的次数
    private var showDialogCount = 0

//    /**
//     * 处理IView的所有业务逻辑
//     *
//     * @param m model 数据来源，网络、文件、数据库等数据
//     */
//    fun LibBasePresenter(m: IModel): ??? {
//        this.mModel = m
//        onStart()
//    }
//
//    /**
//     * 处理IView的所有业务逻辑
//     *
//     * @param v IView的子类接口实现类
//     */
//    fun LibBasePresenter(v: IView): ??? {
//        this(null, v)
//    }
//
//    /**
//     * 处理IView的所有业务逻辑
//     *
//     * @param m model 提供网络、文件、数据库等数据来源
//     * @param v IView的子类接口实现类
//     */
//    fun LibBasePresenter(m: IModel?, v: IView?): ??? {
//        if (m != null)
//            this.mModel = m
//        if (v != null)
//            this.mView = v
//        onStart()
//    }


    override fun onStart() {
        if (useEventBus()) {
            registerEventBus()
        }
    }

    override fun onDestroy() {
        //解除订阅
        if (mCompositeDisposable != null) {
            mCompositeDisposable?.clear()
        }
        if (isEventBus) {
            EventBus.getDefault().unregister(this)
            isEventBus = false
        }
//        if (mModel != null) {
//            mModel?.onDestroy()
//        }
//        if (isShowDialog() && mView != null) {
//            mView?.hideLoading()
//        }
//        mCompositeDisposable = null
//        mModel = null
//        mView = null
    }

    /**
     * 添加 Disposable，用于控制回调，如果调用了onDestroy方法则不会回调
     * CompositeDisposable里面的Disposable对象
     *
     * @param disposable Disposable
     */
    protected fun addDisposable(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable?.add(disposable)
    }

    /**
     * 注册EventBus事件
     */
    protected fun registerEventBus() {
        EventBus.getDefault().register(this)
        isEventBus = true
    }

    /**
     * 启动网络请求
     *
     * @param observable         观察者
     * @param disposableObserver 回调对象
     * @param <T>                泛型
    </T> */
    protected fun <T> subscribeWith(observable: Observable<T>, disposableObserver: DisposableObserver<T>) {
        subscribeWith(observable, disposableObserver, isShowDialog())
    }

    /**
     * 启动网络请求
     *
     * @param observable         观察者
     * @param disposableObserver 回调对象
     * @param isShowDialog       是否打开加载框
     * @param <T>                泛型
    </T> */
    protected fun <T> subscribeWith(observable: Observable<T>, disposableObserver: DisposableObserver<T>, isShowDialog: Boolean) {
        val disposable = observable
                .subscribeOn(Schedulers.io())//网络请求运行线程
                .doOnSubscribe {
                    //请求开始回调
                    Log.d("123", "请求开始")
                    showLoading(isShowDialog)
                }
                .subscribeOn(AndroidSchedulers.mainThread())//
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate {
                    //请求完成回调
                    Log.d("123", "请求完成")
                    hideLoading(isShowDialog)
                }.subscribeWith(disposableObserver)
        addDisposable(disposable)
    }

    /**
     * 外部调用，隐藏加载对话框
     */
    fun hideDialog() {
        hideLoading(true)
    }

    /**
     * 外部调用，显示加载对话框
     */
    fun showDialog() {
        showLoading(true)
    }

    private fun showLoading(isShowDialog: Boolean) {
        if (!isShowDialog)
            return
        if (showDialogCount <= 0) {
            HandlerUtil.post { showLoading() }
        }
        showDialogCount++
    }

    private fun hideLoading(isShowDialog: Boolean) {
        if (!isShowDialog)
            return
        showDialogCount--
        if (showDialogCount > 0)
            return
        if (showDialogCount < 0) {
            showDialogCount = 0
        }
        HandlerUtil.post { hideLoading() }
    }

    fun setShowDialogCount(showDialogCount: Int): LibBasePresenter<*, *> {
        this.showDialogCount = showDialogCount
        return this
    }

    /**
     * 显示加载框
     */
    protected fun showLoading() {}

    /**
     * 关闭加载框
     */
    protected fun hideLoading() {

    }

    /**
     * 是否显示加载框
     *
     * @return boolean
     */
    protected fun isShowDialog(): Boolean {
        return false
    }

    /**
     * 是否注册事件
     *
     * @return
     */
    protected fun useEventBus(): Boolean {
        return false
    }


}
