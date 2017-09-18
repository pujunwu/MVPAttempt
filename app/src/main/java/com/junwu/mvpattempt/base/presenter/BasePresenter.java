package com.junwu.mvpattempt.base.presenter;

import android.app.Activity;

import com.junwu.mvplibrary.mvp.model.IModel;
import com.junwu.mvplibrary.mvp.presenter.LibBasePresenter;
import com.junwu.mvplibrary.mvp.view.IView;

/**
 * ===============================
 * 描    述：所有Presenter继承的基类
 * 可以是空实现，建议在框架中也有此类，如果以后业务改变需要添加一个统一的功能，这个就有价值了
 * 作    者：pjw
 * 创建日期：2017/9/14 16:43
 * ===============================
 */
public class BasePresenter<M extends IModel, V extends IView> extends LibBasePresenter<M, V> {

    //上下文
    protected Activity mActivity;

    public BasePresenter(IModel m) {
        this(null, m);
    }

    public BasePresenter(Activity activity, IModel m) {
        this(activity, m, null);
    }

    public BasePresenter(IView v) {
        this(null, null, v);
    }

    public BasePresenter(Activity activity, IView v) {
        this(activity, null, v);
    }

    public BasePresenter(IModel m, IView v) {
        this(null, m, v);
    }

    public BasePresenter(Activity activity, IModel m, IView v) {
        super(m, v);
        this.mActivity = activity;
    }

}
