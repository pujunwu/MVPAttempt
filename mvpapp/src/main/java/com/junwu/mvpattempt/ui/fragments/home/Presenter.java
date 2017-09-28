package com.junwu.mvpattempt.ui.fragments.home;

import android.app.Activity;

import com.junwu.mvpattempt.base.presenter.BasePresenter;
import com.junwu.mvplibrary.di.scope.ViewScope;
import com.junwu.mvplibrary.mvp.model.IModel;
import com.junwu.mvplibrary.mvp.view.IView;

import javax.inject.Inject;

/**
 * ===============================
 * 描    述：
 * 作    者：pjw
 * 创建日期：2017/9/26 10:25
 * ===============================
 */
@ViewScope
public class Presenter extends BasePresenter<IModel, Contract.View> {

    @Inject
    public Presenter(Activity activity, IView v) {
        super(activity, v);
    }

    public String getDate() {
        return "当前项目为MVP+dagger2+RxJava的尝试\n当前是MVP模式下没有Model的示例\n也是MVP最简单的实现方式";
    }

}
