package com.junwu.mvplibrary.mvp.presenter;

import android.util.Log;

import com.junwu.mvplibrary.mvp.model.IModel;
import com.junwu.mvplibrary.mvp.view.IView;
import com.junwu.mvplibrary.utils.HandlerUtil;

import org.simple.eventbus.EventBus;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * ===============================
 * 描    述：所有Persenter的基类
 * 1、rxJava订阅
 * 2、EventBus事件注册
 * 作    者：pjw
 * 创建日期：2017/6/29 16:03
 * ===============================
 */
    public class LibBasePresenter<M extends IModel, V extends IView> implements IPresenter {

        //订阅事件管理
        protected CompositeDisposable mCompositeDisposable;
        protected M mModel;
        protected V mView;
        //是否注册了eventBus事件
        private boolean isEventBus = false;
        //dialog打开的次数
        private int showDialogCount = 0;

        /**
         * 处理IView的所有业务逻辑
         *
         * @param m model 数据来源，网络、文件、数据库等数据
         */
        public LibBasePresenter(IModel m) {
            this.mModel = (M) m;
            onStart();
        }

        /**
         * 处理IView的所有业务逻辑
         *
         * @param v IView的子类接口实现类
         */
        public LibBasePresenter(IView v) {
            this(null, v);
        }

        /**
         * 处理IView的所有业务逻辑
         *
         * @param m model 提供网络、文件、数据库等数据来源
         * @param v IView的子类接口实现类
         */
        public LibBasePresenter(IModel m, IView v) {
            if (m != null)
                this.mModel = (M) m;
            if (v != null)
                this.mView = (V) v;
            onStart();
        }


        @Override
        public void onStart() {
            if (useEventBus()) {
                registerEventBus();
            }
        }

        @Override
        public void onDestroy() {
            //解除订阅
            if (mCompositeDisposable != null) {
                mCompositeDisposable.clear();
            }
            if (isEventBus) {
                EventBus.getDefault().unregister(this);
                isEventBus = false;
            }
            if (mModel != null) {
                mModel.onDestroy();
            }
            if (isShowDialog() && mView != null) {
                mView.hideLoading();
            }
            mCompositeDisposable = null;
            mModel = null;
            mView = null;
        }

        /**
         * 添加 Disposable，用于控制回调，如果调用了onDestroy方法则不会回调
         * CompositeDisposable里面的Disposable对象
         *
         * @param disposable Disposable
         */
        protected void addDisposable(Disposable disposable) {
            if (mCompositeDisposable == null) {
                mCompositeDisposable = new CompositeDisposable();
            }
            mCompositeDisposable.add(disposable);
        }

        /**
         * 注册EventBus事件
         */
        protected void registerEventBus() {
            EventBus.getDefault().register(this);
            isEventBus = true;
        }

        /**
         * 启动网络请求
         *
         * @param observable         观察者
         * @param disposableObserver 回调对象
         * @param <T>                泛型
         */
        protected <T> void subscribeWith(Observable<T> observable, DisposableObserver<T> disposableObserver) {
            subscribeWith(observable, disposableObserver, isShowDialog());
        }

        /**
         * 启动网络请求
         *
         * @param observable         观察者
         * @param disposableObserver 回调对象
         * @param isShowDialog       是否打开加载框
         * @param <T>                泛型
         */
        protected <T> void subscribeWith(Observable<T> observable, DisposableObserver<T> disposableObserver, final boolean isShowDialog) {
            Disposable disposable = observable
                    .subscribeOn(Schedulers.io())//网络请求运行线程
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(@NonNull Disposable disposable) throws Exception {
                            //请求开始回调
                            Log.d("123", "请求开始");
                            showLoading(isShowDialog);
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())//
                    .observeOn(AndroidSchedulers.mainThread())
                    .doAfterTerminate(new Action() {
                        @Override
                        public void run() throws Exception {
                            //请求完成回调
                            Log.d("123", "请求完成");
                            hideLoading(isShowDialog);
                        }
                    }).subscribeWith(disposableObserver);
            addDisposable(disposable);
        }

        /**
         * 外部调用，隐藏加载对话框
         */
        public void hideDialog() {
            hideLoading(true);
        }

        /**
         * 外部调用，显示加载对话框
         */
        public void showDialog() {
            showLoading(true);
        }

        private void showLoading(boolean isShowDialog) {
            if (!isShowDialog)
                return;
            if (showDialogCount <= 0) {
                HandlerUtil.post(new Runnable() {
                    @Override
                    public void run() {
                        showLoading();
                    }
                });
            }
            showDialogCount++;
        }

        private void hideLoading(boolean isShowDialog) {
            if (!isShowDialog)
                return;
            showDialogCount--;
            if (showDialogCount > 0)
                return;
            if (showDialogCount < 0) {
                showDialogCount = 0;
            }
            HandlerUtil.post(new Runnable() {
                @Override
                public void run() {
                    hideLoading();
                }
            });
        }

        public LibBasePresenter setShowDialogCount(int showDialogCount) {
            this.showDialogCount = showDialogCount;
            return this;
        }

        /**
         * 显示加载框
         */
        protected void showLoading() {
        }

        /**
         * 关闭加载框
         */
        protected void hideLoading() {

        }

        /**
         * 是否显示加载框
         *
         * @return boolean
         */
        protected boolean isShowDialog() {
            return false;
        }

        /**
         * 是否注册事件
         *
         * @return
         */
        protected boolean useEventBus() {
            return false;
        }

    }
